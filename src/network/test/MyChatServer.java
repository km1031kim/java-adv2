package network.test;

import UTIL.MyLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static UTIL.MyLogger.*;

public class MyChatServer {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        SessionManager sessionManager = new SessionManager();
        log("서버 소켓 시작, 리스닝 포트 : " + PORT);

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                log("소켓 연결 완료 : " + socket);
                Session session = new Session(sessionManager, socket);
                Thread thread = new Thread(session);
                thread.start();
            }
        } catch (IOException e) {
            log("서버 소켓 예외 발생");
            e.printStackTrace();
        }
    }
}
