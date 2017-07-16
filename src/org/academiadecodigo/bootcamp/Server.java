package org.academiadecodigo.bootcamp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by nakatax85 on 16-07-2017.
 */
public class Server {

    private static int PORT_NUMBER=6969;
    private String welcomeMessage;
    ServerSocket serverSocket;



    public void start() throws IOException {

        while(true) {
            welcomeMessage = "Welcome to Rock,Paper, Scissors multiplayer game \n " +
                    "This game is brough to you by Team DesperateWithACause";
            System.out.println(welcomeMessage);
            serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("\n CONNECTION ESTABLISHED");

            String resClient1 = "";
            String resClient2 = "";
            String inputClient1, inputClient2;

            while (!serverSocket.isClosed()) {
                Socket client1 = serverSocket.accept();
                //Player One
                if (client1.isConnected()) {
                    System.out.println("\n Player One (" + (client1.getLocalAddress().toString()).substring(1) + ": "
                            + client1.getLocalPort() + ") has joined ... waiting for player two");
                }
                DataOutputStream outClient1 = new DataOutputStream(client1.getOutputStream());
                BufferedReader inClient1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
                //Player Two
                Socket client2 = serverSocket.accept();
                if (client2.isConnected()) {
                    System.out.println("\n Player Two (" + (client2.getLocalAddress().toString()).substring(1) + ": "
                            + client2.getLocalPort() + ") has joined ... Let's START THE GAME");
                }
                DataOutputStream outClient2 = new DataOutputStream(client2.getOutputStream());
                BufferedReader inClient2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));

                //Get Client inputs
                inputClient1 = inClient1.readLine();
                inputClient2 = inClient2.readLine();

                if (inClient1.equals(inputClient2)) {
                    resClient1 = drawMessage();
                    resClient2 = drawMessage();
                    System.out.println("It's a Draw!");
                } else if (inputClient1.equals("R") && inputClient2.equals("S") ||
                        inputClient1.equals("S") && inputClient2.equals("P") ||
                        inputClient1.equals("P") && inputClient2.equals("R")) {
                    resClient1 = winMessage();
                    resClient2 = loseMessage();
                    System.out.println("Player One WINS!!!!!");
                } else {
                    resClient1 = loseMessage();
                    resClient2 = winMessage();
                    System.out.println("Player Two WINS!!!!!");
                }

                outClient1.writeBytes(resClient1.toUpperCase());
                outClient2.writeBytes(resClient2.toUpperCase());
                System.out.println("Would you like to play again? (y or n)");

                if (inputClient1.equals("n")){
                    client1.close();
                }
                else if (inputClient2.equals("n")){
                    client2.close();
                }
                if (inputClient1.equals("y")||inputClient2.equals("y")){
                    start();
                }
                

                System.out.println("Waiting for new players ... \n");

            }
        }

    }

    public String winMessage() {
        String message = "You WIN!!!!";
        return message;
    }

    public String loseMessage(){
        String message = "You lose....SHAME, SHAME, SHAME...";
        return message;
    }

    public String drawMessage(){
        String message = "Draw!!!!";
        return message;
    }








}
