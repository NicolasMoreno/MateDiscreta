package HtmlReader;

import java.io.*;

public class HtmlReader {
    private BufferedReader reader;

    public HtmlReader(String filePath) throws FileNotFoundException{
        File htmlFile = new File(filePath);
        this.reader = new BufferedReader(new FileReader(htmlFile));
    }

    public String readLine() throws IOException {
        final String line = this.reader.readLine();
        if(line == null) return null;
        else return line.replaceAll("<[^>]*>", "").trim();
    }
}
