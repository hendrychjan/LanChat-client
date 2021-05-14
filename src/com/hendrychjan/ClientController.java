package com.hendrychjan;

import com.hendrychjan.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private TextArea txa_chat;

    @FXML
    private TextField txf_message;

    public void handleSend(ActionEvent actionEvent) {
        try {
            if (Main.serverStatus) {
                // Generate message body
                String message = txf_message.getText().trim();

                // Send message to server socket
                Main.out.println(message);
                Main.out.flush();

                // Append that message to the chat TextArea
                txa_chat.appendText("\n[->]: " + message);
            }
        } catch (Exception e) {

            // Message sending failed
            txa_chat.appendText("\n SERVER: Při odesílání zprávy nastala chyba...");

        } finally {
            // Clear the message TextField
            txf_message.clear();
        }
    }

    public static void shutdown() {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Background thread for messages incoming from socket server
        new Thread(() -> {
            while (Main.appIsRunning && Main.serverStatus) {
                try {
                    // Get new message
                    String message = Main.in.nextLine();

                    // Append that new message to chat TextField
                    Platform.runLater(() -> {
                        txa_chat.appendText("\n" + message);
                    });
                } catch (Exception e) {
                    Main.serverStatus = false;
                    Platform.runLater(() -> {
                        txa_chat.appendText("\n[SERVER]: spojení se serverem ukončeno");
                    });
                }
            }
        }).start();
    }
}
