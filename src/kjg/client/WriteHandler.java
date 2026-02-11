package kjg.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

import static UTIL.MyLogger.*;

public class WriteHandler implements Runnable {

    private static final String DELIMITER = "|";
    private static final String CMD_IDENTIFIER = "/";
    private static final String JOIN_COMMAND = "/join" + DELIMITER;
    private static final String SEND_COMMAND = "/message" + DELIMITER;
    private static final String EXIT_COMMAND = "/exit";

    private final DataOutputStream output;
    private final Scanner scanner = new Scanner(System.in);
    private final Client client;


    private boolean closed;

    public WriteHandler(DataOutputStream output, Client client) {
        this.client = client;
        this.output = output;
    }

    @Override
    public void run() {
        // 1. 이름 입력받기
        try {
            String username = inputUsername();
            output.writeUTF(JOIN_COMMAND + username);
            log("client -> server : " + username);

            // 1. 메세지 입력.
            while (true) {
                String toSend = scanner.nextLine();
                if (toSend.isBlank()) {
                    continue;
                }

                if (toSend.equals(EXIT_COMMAND)) {
                    output.writeUTF(toSend);
                    break;
                }

                if (toSend.startsWith(CMD_IDENTIFIER)) {
                    output.writeUTF(toSend);
                } else {
                    output.writeUTF(SEND_COMMAND + toSend);
                }
            }
        } catch (IOException e) {
            log(e);
        } finally {
            client.close();
        }

    }

    private String inputUsername() {
        System.out.println("이름을 입력하세요.");
        String username;
        do {
            username = scanner.nextLine();
        } while (username.isBlank());
        return username;
    }

    public synchronized void close() {
        if (closed) {
            return;
        }

        try {
            System.in.close();
            closed = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
