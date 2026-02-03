package io.buffered;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.FILE_NAME;

public class ReadFileV3 {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(FILE_NAME);
        BufferedInputStream bis = new BufferedInputStream(fis);

        long startTIme = System.currentTimeMillis();

        int fileSize = 0;
        int data;

        // 버퍼만큼 읽어오고, 버퍼에서 반환.
        // 버퍼에 데이터가 다 소모되면 다시 FileInputStrema 에서 버퍼만큼 가져옴.
        while ((data = bis.read()) != -1) {
            fileSize++;
        }
        bis.close();

        long endTime = System.currentTimeMillis();


        System.out.println("File name : " + FILE_NAME);
        System.out.println("File size : " + fileSize / 1024 / 1024 + " MB");
        System.out.println("Time taken : " + (endTime - startTIme) + " ms");

    }
}
