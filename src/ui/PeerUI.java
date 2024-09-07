package ui;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.UDPConnection;

public class PeerUI extends Application {

    private TextArea messageArea;
    private Label ipSrcLabel, ipDestLabel;
    private Label portSrcLabel, portDestLabel;
    private UDPConnection connection;
    private Thread listeningThread;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UDP Chat - Peer");

        // UI components
        TextField messageField = new TextField();
        messageField.setPromptText("Enter message to send");

        TextField ipField = new TextField();
        ipField.setPromptText("Enter IP Address");

        TextField portField = new TextField();
        portField.setPromptText("Enter Port");

        Button sendButton = new Button("Send Message");
        Button receiveButton = new Button("Start Receiving");
        Button stopButton = new Button("Stop Listening");

        messageArea = new TextArea();
        messageArea.setEditable(false);
        messageArea.setPrefHeight(200);

        ipSrcLabel = new Label("Source IP: Not Set");
        ipDestLabel = new Label("Destination IP: Not Set");
        portSrcLabel = new Label("Source Port: Not Set");
        portDestLabel = new Label("Destination Port: Not Set");

        // UDP Connection
        connection = UDPConnection.getInstance();
        connection.setPeerUI(this);

        // Action for send button
        sendButton.setOnAction(e -> {
            String message = messageField.getText();
            String ip = ipField.getText();
            int port = Integer.parseInt(portField.getText());
            connection.sendDatagram(message, ip, port);
            ipDestLabel.setText("Destination IP: " + ip);
            portDestLabel.setText("Destination Port: " + port);
            messageField.clear();
        });

        // Action for receive button
        receiveButton.setOnAction(e -> {
            int port = Integer.parseInt(portField.getText());
            connection.setPort(port);
            listeningThread = new Thread(connection);
            listeningThread.start();

            // Update source port label
            portSrcLabel.setText("Source Port: " + port);
            ipSrcLabel.setText("Source IP: " + getLocalIPAddress());

            messageArea.appendText("Started listening on port: " + port + "\n");
        });

        // Action for stop button
        stopButton.setOnAction(e -> {
            if (connection != null) {
                connection.stopListening();
            }
            if (listeningThread != null && listeningThread.isAlive()) {
                listeningThread.interrupt();
            }
            messageArea.appendText("Stopped listening.\n");
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(new Label("Message:"), messageField, new Label("IP Address:"), ipField,
                new Label("Port:"), portField, sendButton, receiveButton, stopButton,
                ipSrcLabel, portSrcLabel, ipDestLabel, portDestLabel,
                messageArea);

        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to update the message area
    public void updateMessageArea(String message) {
        messageArea.appendText(message + "\n");
    }

    // Helper method to get the local IP address
    private String getLocalIPAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
}
