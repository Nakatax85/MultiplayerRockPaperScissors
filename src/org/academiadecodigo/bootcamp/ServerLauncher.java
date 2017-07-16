package org.academiadecodigo.bootcamp;

import java.io.IOException;

/**
 * Created by nakatax85 on 16-07-2017.
 */
public class ServerLauncher {

    public static void main(String[] args) {
        Server server = new Server();

        try {
            server.start();
        } catch (IOException e) {
            System.err.println("ERROR: "+e.getMessage());
        }
    }
}
