package io.buffered;

import java.io.FileOutputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.FILE_NAME;
import static io.buffered.BufferedConst.FILE_SIZE;

public class CreateFileV4 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        long startMs = System.currentTimeMillis();

        byte[] buffer = new byte[FILE_SIZE];
        for (int i = 0; i < FILE_SIZE; i++) {
            buffer[i] = 1;
        }

        // 버퍼 크기만큼 한번에 쓴다고 하더라도, 시스템에서 데이터를 읽고 쓰는 단위가 4KB, 8KB 이기 때문에..
        fos.write(buffer);
        fos.close();
        long endMs = System.currentTimeMillis();

        System.out.println("File created : " + FILE_NAME);
        System.out.println("File size : " + FILE_SIZE / 1024 / 1024 + " MB");
        System.out.println("Time taken : " + (endMs - startMs) + " ms");

    }
}
