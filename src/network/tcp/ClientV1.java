package network.tcp;

import UTIL.MyLogger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static UTIL.MyLogger.*;

public class ClientV1 {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");

        // 네트워크 연결 - 소켓.
        // 소켓을 읽고 쓴다.
        // 소켓을 통해 외부 네트워크와 연결
        Socket socket = new Socket("localhost", PORT);

//        socket.getOutputStream();
//        socket.getInetAddress();

        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        log("소켓 연결 : " + socket);

        // 서버에게 문자 보내기
        String toSend = "Hello";
        output.writeUTF(toSend);
        log("client -> server : " + toSend);

        // 서버로부터 문자 받기
        String received = input.readUTF();

        // 자원 정리
        log("연결 종료 : " + socket);
        output.close();
        input.close();
        socket.close();

    }
}
