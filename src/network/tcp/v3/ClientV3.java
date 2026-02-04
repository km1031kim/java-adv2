package network.tcp.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static UTIL.MyLogger.log;

public class ClientV3 {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");

        // 네트워크 연결 - 소켓.
        // 소켓을 읽고 쓴다.
        // 소켓을 통해 외부 네트워크와 연결
        // TCP-IP 통신
        Socket socket = new Socket("localhost", PORT);

//        socket.getOutputStream();
//        socket.getInetAddress();

        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        log("소켓 연결 : " + socket);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("전송 문자 : ");
            String toSend = scanner.nextLine();

            output.writeUTF(toSend);
            log("client -> server : " + toSend);

            if (toSend.equals("exit")) {
                break;
            }

            // 서버로부터 문자 받기
            String received = input.readUTF();
            log("client <- server : " + received);
        }

        // 자원 정리
        log("연결 종료 : " + socket);
        input.close();
        output.close();
        socket.close();

    }
}
