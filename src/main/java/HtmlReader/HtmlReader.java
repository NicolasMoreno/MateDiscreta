package HtmlReader;

import java.io.*;
import java.util.Scanner;

public class HtmlReader {
    private File htmlFile;
    private BufferedReader reader;

    public HtmlReader(String filePath) throws FileNotFoundException{
        this.htmlFile = new File(filePath);
        this.reader = new BufferedReader(new FileReader(this.htmlFile));
    }

    public String readLine() throws IOException {
        final String line = this.reader.readLine();
        if(line == null) return null;
        else return line.replaceAll("<[^>]*>", "").trim();
    }

    public File getFile(){
        return this.htmlFile;
    }
}
