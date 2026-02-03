package io.text;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static io.text.TextConst.FILE_NAME;

public class ReaderWriterMainV4 {

    private static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) throws IOException {
        String writeString = "abc\n가나다";
        System.out.println("== Write String==");
        System.out.println(writeString);

        FileWriter fw = new FileWriter(FILE_NAME, StandardCharsets.UTF_8);
        BufferedWriter bw = new BufferedWriter(fw, BUFFER_SIZE);
        bw.write(writeString);
        bw.close();

        // 파일에서 읽기
        StringBuffer content = new StringBuffer();
        FileReader fr = new FileReader(FILE_NAME, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(fr, BUFFER_SIZE);

        String line;

        // 엔터 단위로 끊어서 읽기
        while ((line = br.readLine()) != null) {
            content.append(line).append("\n");
        }
        br.close();
        System.out.println("== Read String ==");
        System.out.println(content);
    }
}
