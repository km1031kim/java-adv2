package network.tcp.v5;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static UTIL.MyLogger.log;

public class ServerV5 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");

        // 서버는 ServerSocket이 필요하다.
        // 12345 포트에서 대기
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트 : " + PORT);

        // 클라이언트 접속 대기 -> 소켓 생성
        // accept 호출 -> OS backlog queue 에서 TCP 연결 정보 기반으로 소켓 생성
        while (true) {
            Socket socket = serverSocket.accept(); // 블로킹
            log("소켓 연결 : " + socket);
            SessionV5 session = new SessionV5(socket);
            Thread thread = new Thread(session);
            thread.start();
        }
    }
}
