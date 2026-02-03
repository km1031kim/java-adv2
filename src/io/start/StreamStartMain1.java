package io.start;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamStartMain1 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp/hello.dat");
        fos.write(65); // A
        fos.write(66); // B
        fos.write(67); // C
        fos.close();

        FileInputStream fis = new FileInputStream("temp/hello.dat");

        // int를 반환하는 이유..
        // EOF -1 표현을 위해
        // 자바에서 바이트는 -128 ~ 127만 표현 가능.
        // EOF를 위해 -1를 사용하기 위해 int 반환
        int data;
        while ((data = fis.read()) != -1) {
            System.out.println(data);
        }
        fis.close();

    }
}
