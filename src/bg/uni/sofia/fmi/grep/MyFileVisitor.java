package bg.uni.sofia.fmi.grep;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Created by itso on 1/20/18.
 */
public class MyFileVisitor extends SimpleFileVisitor<Path> {
    private Path path;
    private String key;
    //private int prodCount;
    private int consCount;
    private ExecutorService producers;
    private BlockingQueue queue;
    private ExecutorService consumers;
    private static final int CAPACITY = 100;

    MyFileVisitor(Path path, String key, int prodCount, int consCount) {
        this.path = path;
        this.key = key;
       // this.prodCount = prodCount;
        this.consCount = consCount;

        this.producers = Executors.newFixedThreadPool(prodCount);
        this.consumers = Executors.newFixedThreadPool(this.consCount);
        this.queue = new ArrayBlockingQueue<MyLine>(CAPACITY);
    }

    public void startWalk() {

        for (int i = 0; i < consCount; i++) {
            consumers.submit(new Consumer(queue, key));
        }

        try {
            Files.walkFileTree(path, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        producers.shutdown();

        while (!queue.isEmpty()) {
            if (consumers.isTerminated()) {
                break;
            }
        }

        producers.shutdown();

    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
        if (attrs.isRegularFile() && path.toString().endsWith(".txt")) {
            producers.submit(new Producer(queue, path));
        }
        return CONTINUE;
    }

}

