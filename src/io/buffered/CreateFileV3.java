package io.buffered;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.*;

public class CreateFileV3 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(FILE_NAME);

        // 버퍼가 가득 차면 outputStream으로 전달
        BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);

        long startMs = System.currentTimeMillis();

        for (int i = 0; i < FILE_SIZE; i++) {
            bos.write(1);
        }

        // 버퍼에 데이터가 남아있는 상태에서 close 호출 -> flush 후 close
        bos.close();
        long endMs = System.currentTimeMillis();

        System.out.println("File created : " + FILE_NAME);
        System.out.println("File size : " + FILE_SIZE / 1024 / 1024 + " MB");
        System.out.println("Time taken : " + (endMs - startMs) + " ms");

    }
}
