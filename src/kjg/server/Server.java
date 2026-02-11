package kjg.server;

import UTIL.MyLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static UTIL.MyLogger.*;

public class Server {

    private final int port;
    private final SessionManager sessionManager;
    private final CommandManager commandManager;
    private ServerSocket serverSocket;


    public Server(int port, SessionManager sessionManager, CommandManager commandManager) {
        this.port = port;
        this.sessionManager = sessionManager;
        this.commandManager = commandManager;
    }

    public void start() throws IOException {
        log("서버 시작 : " + commandManager.getClass());
        serverSocket = new ServerSocket(port);
        log("서버 소켓 시작 - 리스닝 포트 : " + port);
        addShutdownHook();
        running();

    }

    private void addShutdownHook() {
        ShutdownHook shutdownHook = new ShutdownHook(serverSocket, sessionManager);
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook));
    }

    private void running() {

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                Session session = new Session(socket, sessionManager, commandManager);
                Thread thread = new Thread(session);
                thread.start();
            }
        } catch (IOException e) {
            log(e);
        }
    }

    static class ShutdownHook implements Runnable {

        private final ServerSocket serverSocket;
        private final SessionManager sessionManager;

        public ShutdownHook(ServerSocket serverSocket, SessionManager sessionManager) {
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
