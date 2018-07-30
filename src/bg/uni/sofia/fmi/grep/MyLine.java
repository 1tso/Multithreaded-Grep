package bg.uni.sofia.fmi.grep;

import java.nio.file.Path;

/**
 * Created by itso on 1/21/18.
 */
public class MyLine {
    private int lineNum;
    private String line;
    private Path location;

    MyLine(Path location, int lineNum, String line) {
        this.lineNum = lineNum;
        this.line = line;
        this.location = location;
    }


    public String getLine() {
        return this.line;
    }


    @Override
    public String toString() {
        return "path: " + this.location.toString() + " line_number: " + this.lineNum + " line_text: " + this.line;
    }
}
