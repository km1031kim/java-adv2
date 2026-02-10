package network.test;

import UTIL.MyLogger;

import java.util.ArrayList;
import java.util.List;

import static UTIL.MyLogger.*;

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


    public void changeUsername(Session session, String oldUsername, String newUsername) {
        for (Session s : sessions) {
            if (s == session && s.getUsername().equals(oldUsername)) {
                s.setUsername(newUsername);
            }
        }
    }


    public void closeAll() {
        log("세션매니저 close 호출");
        for (Session session : sessions) {
            session.close();
        }
        sessions.clear();
    }

    public List<String> getAllUsername() {
        return sessions.stream().map(Session::getUsername).toList();
    }
}
