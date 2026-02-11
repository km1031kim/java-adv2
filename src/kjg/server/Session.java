package kjg.server;

import UTIL.MyLogger;
import network.tcp.SocketCloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static UTIL.MyLogger.*;

public class Session implements Runnable {

    private final Socket socket;
    private final SessionManager sessionManager;
    private final CommandManager commandManager;
    private final DataInputStream input;
    private final DataOutputStream output;
    private String username;
    private boolean closed;


    public Session(Socket socket, SessionManager sessionManager, CommandManager commandManager) throws IOException {
        this.socket = socket;
        this.output = new DataOutputStream(socket.getOutputStream());
        this.input = new DataInputStream(socket.getInputStream());
        this.sessionManager = sessionManager;
        this.commandManager = commandManager;
        sessionManager.add(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String received = input.readUTF();
                log("client -> server : " + received);
                commandManager.execute(received, this);
            }

        } catch (IOException e) {
            log(e);
        } finally {
            // 닫아야 해
            sessionManager.remove(this);
            sessionManager.sendAll(username + "님이 퇴장했습니다.");
            close();
        }

    }

    public void send(String message) throws IOException {
        log("client -> server");
        output.writeUTF(message);
    }

    public synchronized void close() {
        if (closed) {
            return;
        }
        SocketCloseUtil.closeAll(socket, input, output);
        closed = true;
        log(username + " 세션 종료");
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
