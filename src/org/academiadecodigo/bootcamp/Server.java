package org.academiadecodigo.bootcamp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by nakatax85 on 16-07-2017.
 */
public class Server {

    private static int PORT_NUMBER = 6969;
    private String welcomeMessage;
    ServerSocket serverSocket;
    Socket clientSocket;

    public void start() throws IOException {

        welcomeMessage = "Welcome to Rock,Paper, Scissors multiplayer game \n " +
                "This game is brought to you by Team DesperateWithACause";
        serverSocket = new ServerSocket(PORT_NUMBER);
        System.out.println("\n CONNECTION ESTABLISHED");


        String resClient1 = "";
        String resClient2 = "";
        String inputClient1, inputClient2;

        while (!serverSocket.isClosed()) {
            Socket client1 = serverSocket.accept();
            Socket client2 = serverSocket.accept();

            ClientHandler clientHandler1 = new ClientHandler(client1);
            ClientHandler clientHandler2 = new ClientHandler(client2);

            //Player One
            if (client1.isConnected()) {
                clientHandler1.send(welcomeMessage);
                clientHandler1.send("\n");
                clientHandler1.send("You have joined the game session as PLAYER ONE... Waiting for player TWO");

                System.out.println("\n Player One (" + (client1.getLocalAddress().toString()).substring(1) + ": "
                        + client1.getLocalPort() + ") has joined ... waiting for player two");
            }

            DataOutputStream outClient1 = new DataOutputStream(client1.getOutputStream());
            BufferedReader inClient1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));


            //Player Two
            if (client2.isConnected()) {
                clientHandler2.send(welcomeMessage);
                clientHandler2.send("\n");
                clientHandler2.send("You have joined the game session as PLAYER TWO ... Let's START THE GAME");
                System.out.println("\n Player Two (" + (client2.getLocalAddress().toString()).substring(1) + ": "
                        + client2.getLocalPort() + ") has joined ... Let's START THE GAME");
            }
            DataOutputStream outClient2 = new DataOutputStream(client2.getOutputStream());
            BufferedReader inClient2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));

            //Get Client inputs
            inputClient1 = inClient1.readLine();
            inputClient2 = inClient2.readLine();

            if (inputClient1.equals(inputClient2)) {
                resClient1 = drawMessage();
                resClient2 = drawMessage();
                clientHandler1.send(resClient1);
                clientHandler2.send(resClient2);

            } else if (inputClient1.equals("R") && inputClient2.equals("S") ||
                    inputClient1.equals("S") && inputClient2.equals("P") ||
                    inputClient1.equals("P") && inputClient2.equals("R")) {
                resClient1 = "Player ONE: " + inputClient1 + "\n" +
                        "Player TWO : " + inputClient2 + "\n" + winMessage();
                resClient2 = "Player ONE: " + inputClient1 + "\n" +
                        "Player TWO: " + inputClient2 + "\n" +
                        loseMessage();

                clientHandler1.send(resClient1);
                clientHandler2.send(resClient2);

                System.out.println("PLAYER ONE WINS!!!!");
            } else {
                clientHandler1.send(resClient1);
                clientHandler2.send(resClient2);

                resClient1 = "Player ONE: " + inputClient1 + "\n" +
                        "Player TWO: " + inputClient2 + "\n" +
                        loseMessage();
                resClient2 = "Player ONE: " + inputClient1 + "\n" +
                        "Player TWO: " + inputClient2 + "\n" +
                        winMessage();

                System.out.println("Player Two WINS!!!!!");
            }

            outClient1.writeBytes(resClient1.toUpperCase());
            outClient2.writeBytes(resClient2.toUpperCase());;

            if (inputClient1.equals("quit")){
                client1.close();
            }
            if (inClient2.equals("quit")){
                client2.close();
            }

            System.out.println("Waiting for new players ... \n");

        }


    }

    public String winMessage() {
        String message = "You WIN!!!!";
        return message;
    }

    public String loseMessage() {
        String message = "You lose....SHAME, SHAME, SHAME...";
        return message;
    }

    public String drawMessage() {
        String message = "Draw!!!!";
        return message;
    }

    public class ClientHandler implements Runnable {
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
            }
        }


        public void send(String message) {
            out.write(message);
            out.write("\n");
            out.flush();
        }

        @Override
        public void run() {
            Socket clientSocket = new Socket();


        }
    }


}
