package util;

import javafx.application.Platform;
import ui.PeerUI;

import java.io.IOException;
import java.net.*;

public class UDPConnection extends Thread {

    private DatagramSocket socket;
    private static UDPConnection instance;
    private PeerUI peerUI;

    private UDPConnection() {
    }

    public static UDPConnection getInstance() {
        if (instance == null) {
            instance = new UDPConnection();
        }
        return instance;
    }

    public void setPeerUI(PeerUI peerUI) {
        this.peerUI = peerUI;
    }

    public void setPort(int port) {
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
            while (true) {
                System.out.println("Waiting for message...");
                this.socket.receive(packet);

                // Decodificar mensaje
                String message = new String(packet.getData(), 0, packet.getLength()).trim();
                System.out.println("Received: " + message);

                // Actualizar UI con el mensaje recibido
                if (peerUI != null) {
                    Platform.runLater(() -> peerUI.updateMessageArea("Received: " + message));
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDatagram(String message, String ipDest, int portDest) {
        try {
            InetAddress ipAddress = InetAddress.getByName(ipDest);
            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), ipAddress, portDest);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
