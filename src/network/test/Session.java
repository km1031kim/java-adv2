package network.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static UTIL.MyLogger.log;

public class Session implements Runnable{

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager;
    private String name;
    private boolean closed = false;

    public Session(SessionManager sessionManager, Socket socket) throws IOException {
        this.sessionManager = sessionManager;
        this.socket = socket;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        sessionManager.add(this);
    }

    @Override
    public void run() {
        // 클라이언트 -> Session, Session -> MyChatServer
        // 클라이언트가 보낸 메세지를 받고 서버로 전달.
        try {
            while (true) {
                // 클라이언트로부터 문자 받기
                String received = input.readUTF();
                log("client -> server : " + received);
                sessionManager.sendAll(received);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        } finally {
            sessionManager.remove(this);
            close();
        }
    }

    public void send(String message) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void close() {

    }
}
