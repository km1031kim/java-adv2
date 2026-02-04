package io.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class NewFilesPath {

    public static void main(String[] args) throws IOException {
        Path path = Path.of("temp/..");
        System.out.println("path : " + path);

        Path absolutePath = path.toAbsolutePath();
        Path realPath = path.toRealPath();


        Stream<Path> pathStream = Files.list(path);
        List<Path> list = pathStream.toList();
        pathStream.close();
        for (Path path1 : list) {
            System.out.println(Files.isRegularFile(path1) ? "F" : "D");
        }
    }
}
