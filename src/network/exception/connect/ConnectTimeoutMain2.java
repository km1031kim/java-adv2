package network.exception.connect;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ConnectTimeoutMain2 {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        // 윈도우는 약 21초동안 대기한다. -> ConnectionException
        try {
//            Socket socket = new Socket("192.168.1.250", 45678);
            Socket socket = new Socket();

            // 타임아웃정보를 주면 -> SocketTimeoutException
            socket.connect(new InetSocketAddress("192.168.1.250", 45678), 1000);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("end = " + (end - start) + "ms");

    }
}
