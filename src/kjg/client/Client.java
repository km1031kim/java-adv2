package kjg.client;

import network.tcp.SocketCloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static UTIL.MyLogger.log;
import static network.tcp.SocketCloseUtil.*;

public class Client {


    private final String host;
    private final int port;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private ReadHandler readHandler;
    private WriteHandler writeHandler;
    private boolean closed;


    public Client(String host, int port) throws IOException {
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
        Thread writeThread = new Thread(writeHandler, "writeHandler");

        readThread.start();
        writeThread.start();
    }

    public synchronized void close() {
        if (closed) {
            return;
        }
        readHandler.close();
        writeHandler.close();
        closeAll(socket, input, output);
    }
}
