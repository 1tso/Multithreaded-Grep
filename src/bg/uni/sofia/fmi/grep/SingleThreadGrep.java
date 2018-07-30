package bg.uni.sofia.fmi.grep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Created by itso on 1/21/18.
 */
public class SingleThreadGrep extends SimpleFileVisitor<Path> {

    private Path path;
    private String key;

    SingleThreadGrep(Path path, String key) {
        this.path = path;
        this.key = key;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
        if (attrs.isRegularFile() && path.toString().endsWith(".txt")) {
            readFile(path);
        }
        return CONTINUE;
    }

    public void startFileWalking() {
        try {
            Files.walkFileTree(path, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(Path path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path.getFileName().toString()))) {

            String line;
            int lineNum = 0;

            while ((line = br.readLine()) != null) {
                lineNum++;
                match(new MyLine(path, lineNum, line));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void match(MyLine line) {
        if (line.getLine().equals(key)) {
            System.out.println(line);
        }
    }

}
