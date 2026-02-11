package kjg.server;

import java.io.IOException;
import java.util.regex.Pattern;

public class CommandManagerV1 implements CommandManager {

    private static final String DELIMITER = "|";
    private static final String CMD_IDENTIFIER = "/";
    private static final String JOIN_COMMAND = "/join" + DELIMITER;
    private static final String CHANGE_COMMAND = "/change" + DELIMITER;
//    private static final String SEND_COMMAND = "/message" + DELIMITER;
    private static final String EXIT_COMMAND = "/exit";

    private final SessionManager sessionManager;

    public CommandManagerV1(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String totalMessage, Session session) throws IOException {
        if (totalMessage.startsWith(EXIT_COMMAND)) {
            throw new IOException(session.getUsername() + " exited");
        }

        if (totalMessage.startsWith(CHANGE_COMMAND)) {
            System.out.println("CHANGE");
            String username = totalMessage.split(Pattern.quote(DELIMITER))[1];
            session.setUsername(username);
            return;
        }

        if (totalMessage.startsWith(JOIN_COMMAND)) {
            String username = totalMessage.split(Pattern.quote(DELIMITER))[1];
            session.setUsername(username);
            sessionManager.sendAll(username + " 님이 입장하셨습니다.");
            return;
        }

        sessionManager.sendAll(totalMessage);
    }
}
