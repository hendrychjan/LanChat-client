package com.hendrychjan;

import com.hendrychjan.Main;
import com.hendrychjan.DataCarrierSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientConfigController {

    @FXML
    private TextField txf_nick;

    @FXML
    private TextField txf_ip;

    @FXML
    private Label lbl_error;

    public void onConfigSubmit(ActionEvent actionEvent) throws IOException {
        // Config variables
        String nick = txf_nick.getText();
        String ip = txf_ip.getText();

        // IP format check
        Pattern ipRegexPat = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$", Pattern.CASE_INSENSITIVE);
        Matcher ipRegexMat = ipRegexPat.matcher(ip);

        if (ipRegexMat.find()) {
            // IP format is valid

            // Validate nick
            if (nick.isEmpty() || nick.toUpperCase().equals("SERVER") || nick.length() > 8) {
                lbl_error.setText("Nick nesmí být \"SERVER\" a musí být v rozsahu 1-8 znaků.");
            } else {
                // Connect to socket server
                Main.startServer(ip);

                if (Main.serverStatus) {
                    // Connected to server successfully

                    // Set nick + log with that nick on the server
                    DataCarrierSingleton.getInstance().setNick(nick.trim().toUpperCase());
                    Main.out.println(DataCarrierSingleton.getInstance().getNick());
                    Main.out.flush();

                    // Load the client window
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("general.css").toExternalForm());
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
                    stage.setScene(scene);
                    ClientController controller = loader.getController();
                    stage.setOnHidden(e -> controller.shutdown());
                    stage.setTitle("LAN Chat - " + DataCarrierSingleton.getInstance().getNick());
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    stage.show();
                } else {

                    // Connection failed
                    lbl_error.setText("Nepodařilo se připojit k serveru.");
                }
            }
        } else {
            // IP format is invalid
            lbl_error.setText("Zadaná IP adresa je nesprávná.");
        }
    }
}
