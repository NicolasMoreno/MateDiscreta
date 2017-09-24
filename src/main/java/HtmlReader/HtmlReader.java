package HtmlReader;

import java.io.*;

public class HtmlReader {
    private File htmlFile;
    private BufferedReader reader;

    public HtmlReader(String filePath) throws FileNotFoundException{
        this.htmlFile = new File(filePath);
        this.reader = new BufferedReader(new FileReader(this.htmlFile));
    }

    public String readLine() throws IOException {
        return this.reader.readLine();
    }

    public File getFile(){
        return this.htmlFile;
    }
}
