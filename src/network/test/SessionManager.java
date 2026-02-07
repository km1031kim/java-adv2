package network.test;

import network.tcp.v6.SessionV6;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    private List<Session> sessions = new ArrayList<>();

    public void add(Session session) {
        sessions.add(session);
    }

    public void remove(Session session) {
        sessions.remove(session);
    }

    public void sendAll(String message) {
        for (Session session : sessions) {
            session.send(message);
        }
    }

    public void closeAll() {
        for (Session session : sessions) {
            session.close();
        }
    }
}
