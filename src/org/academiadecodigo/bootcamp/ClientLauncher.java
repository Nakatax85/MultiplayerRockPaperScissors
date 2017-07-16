package org.academiadecodigo.bootcamp;

import java.io.IOException;

/**
 * Created by nakatax85 on 16-07-2017.
 */
public class ClientLauncher {

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.startConnection();
        } catch (IOException e) {
            System.err.println("ERROR: "+e.getMessage());
        }
    }
}
