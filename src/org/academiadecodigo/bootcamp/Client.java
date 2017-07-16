package org.academiadecodigo.bootcamp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by nakatax85 on 16-07-2017.
 */
public class Client {
    private String host = "localhost";
    private int port = 6969;


    public void startConnection() throws IOException {
        String input = "";
        String response;

        System.out.println("Welcome to Rock, Paper, Scissors Multiplayer Game");
        System.out.println("This game is brought to you by Team DesperateWithACause");

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket(host,port);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        if (input.equals("-man")){
            manMessage();
        }
        if (!input.equals("R")&& !input.equals("S")&&!input.equals("P")) {

            System.out.println("Start the game by selecting (R)ock, (P)aper, (S)cissors");
            System.out.println("or type\"-man\" in order to see the rules: ");
        }
        input = inFromUser.readLine();
        outToServer.writeBytes(input + "\n");
        System.out.println("\n Your play has been transmitted. Please wait for result");

        response = inFromServer.readLine();

        System.out.println("Response from server: "+ response);
        clientSocket.close();

    }


    public void manMessage(){
        System.out.println("\n RULES: (R)ock beats (S)cissors\n - (S)cissors beats (P)aper\n - (P)aper beats (R)ock\n");
    }
}
