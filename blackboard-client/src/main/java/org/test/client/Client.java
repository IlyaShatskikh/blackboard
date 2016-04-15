package org.test.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by by Ilya Shatskikh (shatskikh.ilya@gmail.com)
 */
public class Client extends Thread {
    private final String ip;
    private final int port;
    private final Util util;

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public Client(String ip, int port, Util util) {
        this.ip = ip;
        this.port = port;
        this.util = util;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (socket.isConnected()) {
                try {
                    String command = in.readLine();
                    if (command != null) {
                        if (!command.isEmpty()) {
                            util.drawCommands(parseCommand(command));
                            continue;
                        } else {
                            util.drawCommands();
                        }
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            //e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (socket != null) {
            try {
                out.println("bye");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Command parseCommand(String command) throws Exception {
        String[] fields = command.split(";");
        if (fields.length != 5) {
            throw new Exception("Parse message error: Incorrect command format");
        }
        Command com = new Command();
        com.setDeviceId(fields[0]);

        if ("start".equals(fields[1])) {
            com.setAction(Command.Action.START);
        } else if ("move".equals(fields[1])) {
            com.setAction(Command.Action.MOVE);
        } else {
            throw new Exception("Parse message error: Unknown action - " + fields[1]);
        }

        com.setX(Double.parseDouble(fields[2]));
        com.setY(Double.parseDouble(fields[3]));
        com.setColor(Integer.parseInt(fields[4]));
        return com;
    }
}
