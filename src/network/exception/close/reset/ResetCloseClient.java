package network.exception.close.reset;

import UTIL.MyLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import static UTIL.MyLogger.*;

public class ResetCloseClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 12345);
        log("소켓 연결 : " + socket);

        // client <- server: FIN
        Thread.sleep(1000); // 서버가 close 호출할 때 가지 잠깐 대기

        // client -> server: PUSH[1]
        // FIN 날리던 말던 메세지 보내기
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        output.write(1);

        // client <- server: RST
        Thread.sleep(1000); // RST 메세지 전송 대기

        try {
            // Connection reset
            int read = input.read();
            System.out.println("read = " + read);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            // Broken pipe.. 하둡에서 많이 보던거다.
            // RST를 전달한 서버에게 데이터 전송 시 Broken pipe Exception
            output.write(1);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
