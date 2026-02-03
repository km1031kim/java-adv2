package io.text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static io.text.TextConst.FILE_NAME;

public class ReaderWriterMainV1 {

    public static void main(String[] args) throws IOException {
        String writeString = "ABC";

        // 문자 -> byte UTF-8
        byte[] writeBytes = writeString.getBytes(StandardCharsets.UTF_8);
        System.out.println("write String : " + writeString);
        System.out.println("write bytes : " + Arrays.toString(writeBytes));
        System.out.println(writeBytes.length);

        // 파일 쓰기
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        fos.write(writeBytes);
        fos.close();

        // 파일 읽기
        FileInputStream fis = new FileInputStream(FILE_NAME);
        byte[] readBytes = fis.readAllBytes();

        // byte -> string UTF-8
        String readString = new String(readBytes, StandardCharsets.UTF_8);
        System.out.println("Read bytes : " + Arrays.toString(readBytes));
        System.out.println("readString = " + readString);

    }
}
