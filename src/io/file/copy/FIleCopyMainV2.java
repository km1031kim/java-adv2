package io.file.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FIleCopyMainV2 {

    private static final int FILE_SIZE = 200 * 1024 * 1024;

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        FileInputStream fis = new FileInputStream("temp/copy.dat");
        FileOutputStream fos = new FileOutputStream("temp/copy_new.dat");


        // inputStream -> outputStream
        fis.transferTo(fos);

        fos.close();
        fis.close();

        long endTime = System.currentTimeMillis();

        System.out.println("TIme taken : " + (endTime - startTime) + "ms");

    }
}
