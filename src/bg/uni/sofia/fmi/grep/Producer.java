package bg.uni.sofia.fmi.grep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

/**
 * Created by itso on 1/20/18.
 */
public class Producer implements Runnable {

    private Path path;
    private BlockingQueue queue;

    Producer(BlockingQueue queue, Path path) {
        this.path = path;
        this.queue = queue;
    }


    @Override
    public void run() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(path.getFileName().toString()))) {
                String line;
                int counter = 0;

                while ((line = br.readLine()) != null) {
                    counter++;
                    try {
                        queue.put(new MyLine(path, counter, line));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
