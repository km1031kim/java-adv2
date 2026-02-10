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
        Socket socket = null;
        log("서버 소켓 시작, 리스닝 포트 : " + PORT);


        //셧다운 훅
        Shutdown shutdown = new Shutdown(serverSocket, sessionManager);
        Runtime.getRuntime().addShutdownHook(new Thread(shutdown, "shutdown"));

        while (true) {
            socket = serverSocket.accept();
            log("소켓 연결 완료 : " + socket);
            Session session = new Session(sessionManager, socket);
            Thread thread = new Thread(session);
            thread.start();
        }


    }

    static class Shutdown implements Runnable {

        private final ServerSocket serverSocket;
        private final SessionManager sessionManager;

        public Shutdown(ServerSocket serverSocket, SessionManager sessionManager) {
            this.serverSocket = serverSocket;
            this.sessionManager = sessionManager;
        }

        @Override
        public void run() {
            log("shutdownHook 실행");
            try {
                sessionManager.closeAll();
                serverSocket.close();
                Thread.sleep(1000); // 자원 정리 대기
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("e = " + e);
            }
        }
    }
}

