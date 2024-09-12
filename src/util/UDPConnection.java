package util;

import javafx.application.Platform;
import ui.PeerUI;

import java.io.IOException;
import java.net.*;

public class UDPConnection extends Thread {

    private DatagramSocket socket;
    private PeerUI peerUI;
    private volatile boolean running = true;

    private static UDPConnection instance;

    private UDPConnection() {
    }

    public static UDPConnection getInstance() {
        if (instance == null) {
            instance = new UDPConnection();
        }
        return instance;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setPeerUI(PeerUI peerUI) {
        this.peerUI = peerUI;
    }

    public void setPort(int port) {
        try {
            this.socket = new DatagramSocket(port);
            System.out.println("Socket opened on port: " + port); // Debugging line
        } catch (SocketException e) {
            System.out.println("Error opening socket: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (running) {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                System.out.println("Waiting for message...");
                this.socket.receive(packet);

                String message = new String(packet.getData()).trim();
                System.out.println("Received: " + message);

                if (peerUI != null) {
                    Platform.runLater(() -> peerUI.updateMessageArea("Received: " + message));
                }
            }
        } catch (SocketException e) {
            if (running) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            if (running) {
                e.printStackTrace();
            }
        }
    }

    public void sendDatagram(String message, String ipDest, int portDest) {
        try {
            InetAddress ipAddress = InetAddress.getByName(ipDest);
            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), ipAddress, portDest);
            socket.send(packet);
            System.out.println("Sent message: '" + message + "' to " + ipDest + ":" + portDest); // Debugging line
        } catch (IOException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }

    public void stopListening() {
        running = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    
}
