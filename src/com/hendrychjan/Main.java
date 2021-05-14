package com.hendrychjan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main extends Application {

    public static Scanner in;
    public static PrintWriter out;
    public static boolean appIsRunning = true;
    public static boolean serverStatus;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("clientConfig.fxml"));
        primaryStage.setTitle("LAN Chat");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        appIsRunning = false;
    }

    public static void startServer(String ip) {
        Socket socket = null;
        try {
            // Connect to socket server
            socket = new Socket(ip, 56789);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());

            // Connection successful
            serverStatus = true;

        } catch (IOException e) {

            // Connection failed
            serverStatus = false;

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
