package io.file;

import java.io.File;
import java.io.IOException;

public class OldFIleMain {

    public static void main(String[] args) throws IOException {
        File file = new File("temp/example.txt");
        File directory = new File("temp/exampleDir");

        System.out.println("File exists : " + file.exists());

        boolean created = file.createNewFile();
        System.out.println("File created : " + created);

        boolean dirCreated = directory.mkdir();
        System.out.println("Directory created : " + dirCreated);

        boolean deleted = file.delete();
        System.out.println("File deleted : " + deleted);

        System.out.println("Is file : " + file.isFile());
        System.out.println("Is directory : " + directory);
        System.out.println("File Name : " + file.getName());
        System.out.println("File Size : " + file.length());

        File newFile = new File("temp/newExample.txt");
        boolean renamed = file.renameTo(newFile);
        System.out.println("File renamed : " + renamed);

        long lastModified = newFile.lastModified();
        System.out.println("Last Modified : " + lastModified);

    }
}
