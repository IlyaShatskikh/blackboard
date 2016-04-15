package org.test.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by by Ilya Shatskikh (shatskikh.ilya@gmail.com)
 */
public class Server extends Thread {
    private final int port;
    private ServerSocket server;
    private List<Connection> connections = new ArrayList<>();

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            while (true) {
                Socket socket = server.accept();
                Connection con = new Connection(socket);
                connections.add(con);
                con.start();
            }
        } catch (SocketException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pushCommands(String str) {
        for (Connection con : connections) {
            con.send(str);
        }
    }

    public void close() {
        try {
            closeAllConnections();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeAllConnections() {
        for (Connection con : connections) {
            con.close();
        }
    }
}

