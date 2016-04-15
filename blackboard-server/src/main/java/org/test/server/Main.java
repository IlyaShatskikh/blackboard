package org.test.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by by Ilya Shatskikh (shatskikh.ilya@gmail.com)
 */
public class Main {
    private static final int PORT = 29288;
    private static final String COMMAND_FILE = "command.txt";

    public static void main(String[] args) {
        Server server = new Server(PORT);
        try {
            server.start();
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("Choose command: 1)send commands to clients(S) 2)exit(E)");
                String str = sc.next();
                if ("S".equalsIgnoreCase(str)) {
                    server.pushCommands(readFile(COMMAND_FILE));
                } else if ("E".equalsIgnoreCase(str)) {
                    break;
                }
            }
        } catch (java.util.NoSuchElementException e) {
            //
        } finally {
            server.close();
        }
    }

    private static String readFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
                sb.append(System.lineSeparator());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
