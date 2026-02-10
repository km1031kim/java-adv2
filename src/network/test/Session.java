package network.test;

import UTIL.MyLogger;
import network.tcp.SocketCloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static UTIL.MyLogger.*;
import static UTIL.MyLogger.log;
import static network.test.ChatFormatter.*;

public class Session implements Runnable {


    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager;
    private String username;

    private boolean closed = false;

    public Session(SessionManager sessionManager, Socket socket) throws IOException {
        this.socket = socket;
        this.sessionManager = sessionManager;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        sessionManager.add(this);
    }

    @Override
    public void run() {
        // 클라이언트 -> Session, Session -> MyChatServer
        // 클라이언트가 보낸 메세지를 받고 서버로 전달.
        try {
            username = input.readUTF();
            sessionManager.sendAll(username + " 님이 입장하셨습니다.");

            while (true) {
                // 클라이언트로부터 문자 받기
                String clientMessage = input.readUTF();

                // 메세지, 이름 변경, 멤버 출력, 종료에 대한 분기 필요
                log("clientMessage : " + clientMessage);
                if (clientMessage.startsWith(SEND_MESSAGE_FORMAT)) {
                    String sendMessage = getMessage(clientMessage);
                    sessionManager.sendAll(username + " : " + sendMessage);
                    continue;
                }

                if (clientMessage.startsWith(CHANGE_USERNAME_FORMAT)) {
                    String newUsername = getMessage(clientMessage);
                    sessionManager.changeUsername(this, username, newUsername);
                    continue;
                }

                if (clientMessage.equals(PRINT_MEMBERS)) {
                    List<String> allUsername = sessionManager.getAllUsername();
                    send(allUsername.toString());
                    continue;
                }

                if (clientMessage.equals(EXIT_MESSAGE)) {
                    break;
                }
            }
        } catch (IOException e) {
            log(e.getMessage());
        } finally {
            log("finally 호출");
            sessionManager.remove(this);
            close();
        }
    }

    public void send(String message) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            log(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername) {
        username = newUsername;
    }

    public synchronized void close() {
        log("close 호출");
        if (closed) {
            return;
        }

        SocketCloseUtil.closeAll(socket, input, output);
        closed = true;
        log("연결 종료 : " + socket);
    }
}
