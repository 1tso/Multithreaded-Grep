package bg.uni.sofia.fmi.grep;

import java.util.concurrent.BlockingQueue;

/**
 * Created by itso on 1/21/18.
 */
public class Consumer implements Runnable {

    private String key;
    private BlockingQueue<MyLine> queue;

    Consumer(BlockingQueue<MyLine> queue, String key) {
        this.key = key;
        this.queue = queue;
    }

    @Override
    public void run() {

        MyLine line;

        while (true) {
            try {
                line = queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (line.getLine().contains(key)) {
                System.out.println(line);
            }
        }

    }
}
