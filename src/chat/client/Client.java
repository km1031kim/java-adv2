package chat.client;

import UTIL.MyLogger;
import network.tcp.SocketCloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static UTIL.MyLogger.*;
import static network.tcp.SocketCloseUtil.*;

public class Client {

    private final String host;
    private final int port;
    private Socket socket;
    private DataInputStream input;

    private DataOutputStream output;

    private ReadHandler readHandler;
    private WriteHandler writeHandler;
    private boolean closed = false;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        log("클라이언트 시작");
        socket = new Socket(host, port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        readHandler = new ReadHandler(input, this);
        writeHandler = new WriteHandler(output, this);
        Thread readThread = new Thread(readHandler, "readHandler");
        Thread writeHandler = new Thread(this.writeHandler, "writeHandler");
        readThread.start();
        writeHandler.start();
    }

    public synchronized void close() {
        if (closed) {
            return;
        }

        writeHandler.close();
        readHandler.close();
        closeAll(socket, input, output);
        log("연결 종료 : " + socket);
    }
}
