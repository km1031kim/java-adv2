package network.tcp.v1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static UTIL.MyLogger.*;

public class ServerV1 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");

        // 서버는 ServerSocket이 필요하다.
        // 12345 포트에서 대기
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트 : " + PORT);

        // 클라이언트 접속 대기 -> 소켓 생성
        // accept 호출 -> OS backlog queue 에서 TCP 연결 정보 기반으로 소켓 생성
        Socket socket = serverSocket.accept();
        log("소켓 연결 : " + socket);

        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        // 클라이언트로부터 문자 받기
        String received = input.readUTF();
        log("client -> server : " + received);

        // 클라이언트에게 문자 보내기
        String toSend = received + " World!";

        output.writeUTF(toSend);
        log("client <- server : " + toSend);

        output.close();
        input.close();
        socket.close();

    }
}
