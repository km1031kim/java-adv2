package network.test;

import UTIL.MyLogger;
import network.tcp.v6.SessionManagerV6;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static UTIL.MyLogger.*;

public class MyChatClient {
    private static final String START_MESSAGE_FORMAT = "/join";
    private static final String SEND_MESSAGE_FORMAT = "/message";

    private static final String EXIT_MESSAGE_FORMAT = "/exit";
    private static final String DELIMITER = "|";

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");


        try (Socket socket = new Socket("localhost", 12345);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            // 입장 하면, 그때부터
            System.out.print("채팅방 입장 방법: " + START_MESSAGE_FORMAT + DELIMITER + "유저명 ");
            String enterMessage = scanner.nextLine();

            while (!enterMessage.startsWith(START_MESSAGE_FORMAT + DELIMITER)) {
                System.out.print("채팅방 입장 방법: " + START_MESSAGE_FORMAT + DELIMITER + "유저명 ");
                enterMessage = scanner.nextLine();
            }

            // 1. 입장
            String username = enterMessage.split(DELIMITER)[1];

            Thread receiver = new Thread(new Receiver(input));
            receiver.start();

            System.out.println("ㅇㅇㅇ");

           // output.writeUTF(username + " 님이 입장하셨습니다.");

        }
    }

    static class Receiver implements Runnable {

        private final DataInputStream input;

        public Receiver(DataInputStream input) {
            this.input = input;
        }

        @Override
        public void run() {
            // 터미널 출력
            System.out.println("receiver thread start");

            while (true) {
                String message = null;
                try {
                    message = input.readUTF();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("message : " + message);
            }
        }
    }
}
