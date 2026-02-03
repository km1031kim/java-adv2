package io.text;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.text.TextConst.FILE_NAME;

public class ReaderWriterMainV3 {

    public static void main(String[] args) throws IOException {
        String writeString = "abc";
        System.out.println("Write string : " + writeString);

        FileWriter fw = new FileWriter(FILE_NAME, StandardCharsets.UTF_8);
        fw.write(writeString);
        fw.close();

        // 파일에서 읽기
        StringBuffer content = new StringBuffer();
        FileReader fr = new FileReader(FILE_NAME, StandardCharsets.UTF_8);
        int ch; // char는 부호가 없다.
        while ((ch = fr.read()) != -1) {
            content.append((char) ch);
        }
        fr.close();

        System.out.println(content);
    }
}
