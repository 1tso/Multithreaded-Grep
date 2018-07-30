package bg.uni.sofia.fmi.grep;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by itso on 1/15/18.
 */
public class Main {

    public static void main(String[] args) {

        String path = args[0];
        String key = args[1];
        int numOfThreads = Integer.parseInt(args[2]);

        Path pathToStart = Paths.get(path);
        if (numOfThreads == 1) {
            SingleThreadGrep solution = new SingleThreadGrep(pathToStart, key);
            solution.startFileWalking();
        } else {
            int cons = numOfThreads / 2;
            int prods = numOfThreads - cons;
            MyFileVisitor solution = new MyFileVisitor(pathToStart, key, prods, cons);
            solution.startWalk();
        }
    }

}
