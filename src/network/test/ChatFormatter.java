package network.test;

import java.util.regex.Pattern;

public abstract class ChatFormatter {
    public static final String DELIMITER = "|";
    public static final String START_MESSAGE_FORMAT = "/join" + DELIMITER;
    public static final String SEND_MESSAGE_FORMAT = "/message" + DELIMITER;
    public static final String CHANGE_USERNAME_FORMAT = "/change" + DELIMITER;
    public static final String PRINT_MEMBERS = "/members";
    public static final String HELP_MESSAGE = "/help";
    public static final String EXIT_MESSAGE = "/exit";

    public static String getMessage(String clientMessage) {
        return clientMessage.split(Pattern.quote(DELIMITER))[1];
    }

}
