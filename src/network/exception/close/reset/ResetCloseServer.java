package network.exception.close.reset;

import UTIL.MyLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ResetCloseServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        Socket socket = serverSocket.accept();
        MyLogger.log("소켓 연결 : " + socket);

        socket.close();
        serverSocket.close();
        MyLogger.log("소켓 종료 : " + socket);

    }
}
