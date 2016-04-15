package org.test.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by by Ilya Shatskikh (shatskikh.ilya@gmail.com)
 */
public class Connection extends Thread {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Connection(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String str = in.readLine();
                if (str != null && "bye".equals(str)) {
                    close();
                }
            } catch (java.net.SocketException e){
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String command) {
        out.println(command);
    }

    public void close() {
        try {
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
