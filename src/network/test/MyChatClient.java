package network.test;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

import static UTIL.MyLogger.*;
import static network.test.ChatFormatter.*;

public class MyChatClient {


    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");

        Socket socket = new Socket("localhost", 12345);
        try (socket;
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            // 입장 하면, 그때부터
            System.out.print("채팅방 입장 방법: " + START_MESSAGE_FORMAT + "유저명 ");
            String enterMessage = scanner.nextLine();

            while (!enterMessage.startsWith(START_MESSAGE_FORMAT)) {
                System.out.print("채팅방 입장 방법: " + START_MESSAGE_FORMAT + "유저명 ");
                enterMessage = scanner.nextLine();
            }

            // 1. 입장
            String username = enterMessage.split(Pattern.quote(DELIMITER))[1];
            helpMessage();

            // 2. 리시버 시작
            Thread receiver = new Thread(new Receiver(input), username + "_receiver");
            receiver.start();

            // 3. 입장 및 유저명 전송
            output.writeUTF(username);

            while (true) {
                String toSend = scanner.nextLine();

                if (toSend.startsWith(SEND_MESSAGE_FORMAT) ||
                    toSend.startsWith(CHANGE_USERNAME_FORMAT) ||
                    toSend.equals(PRINT_MEMBERS)) {
                    output.writeUTF(toSend);
                    continue;
                }

                if (toSend.equals(HELP_MESSAGE)) {
                    helpMessage();
                    continue;
                }

                if (toSend.equals(EXIT_MESSAGE)) {
                    System.out.println("클라이언트를 종료합니다.");
                    output.writeUTF(toSend);
                    break;
                }
                System.out.println("사용방법 출력 : " + HELP_MESSAGE);
            }
        } catch (ConnectException e) {
            log("서버 기동중 아님 : " + e);
        } catch (IOException e) {
            log("클라이언트 종료 : " + e);
            throw new IOException(e);
        }
        log("socket.isClosed() : " + socket.isClosed());
    }

    private static void helpMessage() {
        System.out.println("메세지 보내기 : " + SEND_MESSAGE_FORMAT + "보낼 메세지");
        System.out.println("채팅방 멤버 리스트 출력하기 : " + PRINT_MEMBERS);
        System.out.println("이름 변경하기 : " + CHANGE_USERNAME_FORMAT + "변경할 이름");
        System.out.println("메세지 보내기 : " + SEND_MESSAGE_FORMAT + "보낼 메세지");
        System.out.println("도움말 : " + HELP_MESSAGE);
        System.out.println("나가기 : " + EXIT_MESSAGE);
    }

    static class Receiver implements Runnable {

        private final DataInputStream input;
        public Receiver(DataInputStream input) {
            this.input = input;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String messageFromSerer = input.readUTF();
                    System.out.println(messageFromSerer);
                }
            } catch (IOException e) {
                log("Exception 발생. 메세지 리시버 스레드 종료. : " + e);
            }
        }
    }
}
