package kjg.client;

import java.io.DataInputStream;
import java.io.IOException;

import static UTIL.MyLogger.log;

public class ReadHandler implements Runnable {

    private final DataInputStream input;
    private final Client client;
    private boolean closed;

    public ReadHandler(DataInputStream input, Client client) {
        this.input = input;
        this.client = client;
    }


    @Override
    public void run() {
        try {
            while (true) {
                String received = input.readUTF();
                System.out.println(received);
            }
        } catch (IOException e) {
            log(e);
        } finally {
            client.close();
        }
    }

    public synchronized void close() {
        if (closed) {
            return;
        }

        try {
            input.close();
        } catch (IOException e) {
            log("ReadHandler 종료 중 Exception 발생 : " + e);
        }
        closed = true;
        log("ReadHandler 종료");
    }
}
