package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.UDPConnection;

public class PeerUI extends Application {

    private TextArea messageArea;
    private UDPConnection connection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UDP Chat - Peer");

        // UI components
        TextField messageField = new TextField();
        messageField.setPromptText("Enter message to send");

        TextField ipField = new TextField("127.0.0.1");
        ipField.setPromptText("Enter IP Address");

        TextField portField = new TextField();
        portField.setPromptText("Enter Port");

        Button sendButton = new Button("Send Message");
        Button receiveButton = new Button("Start Receiving");

        messageArea = new TextArea();
        messageArea.setEditable(false);
        messageArea.setPrefHeight(200);

        // UDP Connection
        connection = UDPConnection.getInstance();
        connection.setPeerUI(this);

        // Action for send button
        sendButton.setOnAction(e -> {
            String message = messageField.getText();
            String ip = ipField.getText();
            int port = Integer.parseInt(portField.getText());
            connection.sendDatagram(message, ip, port);
            messageField.clear();
        });

        // Action for receive button
        receiveButton.setOnAction(e -> {
            int port = Integer.parseInt(portField.getText());
            connection.setPort(port);
            connection.start();
            messageArea.appendText("Started listening on port: " + port + "\n");
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(new Label("Message:"), messageField, new Label("IP Address:"), ipField,
                new Label("Port:"), portField, sendButton, receiveButton, messageArea);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to update the message area
    public void updateMessageArea(String message) {
        messageArea.appendText(message + "\n");
    }
}
