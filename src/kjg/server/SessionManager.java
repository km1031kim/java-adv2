package kjg.server;

import UTIL.MyLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static UTIL.MyLogger.*;

public class SessionManager {
    private final List<Session> sessions = new ArrayList<>();

    public synchronized void sendAll(String message)  {
        try {
            for (Session session : sessions) {
                session.send(message);
            }
        } catch (IOException e) {
            log(e);
        }
    }

    public void add(Session session) {
        sessions.add(session);
    }

    public void remove(Session session) {
        sessions.remove(session);
    }

    public void closeAll() {
        for (Session session : sessions) {
            session.close();
        }
    }
}
