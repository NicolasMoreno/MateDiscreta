import Automaton.FiniteAutomaton;
import Automaton.FiniteState;
import Automaton.FiniteTransition;
import HtmlReader.HtmlReader;
import extras.GraphViz;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la ruta o el nombre del archivo que desea levantar: ");
        String path = scanner.nextLine();
        System.out.println("Ingrese la ruta o el nombre de la carpeta con los HTML: ");
        String path2 = scanner.nextLine();
        FiniteState q0 = new FiniteState("InitialState");
        FiniteAutomaton automat = new FiniteAutomaton(q0);
        File[] files = new File(path2).listFiles();
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                automat.addNonDeterministically(line);
            }
            automat.transformToDeterministic();
            int indexFile = 0;
            for(File auxFile: files){
                HtmlReader htmlReader = new HtmlReader(auxFile.getPath());
                String line2;
                while ((line2 = htmlReader.readLine()) != null) {
                    automat.evaluate(line2,indexFile);
                }
                indexFile++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        generateIndexTxt(automat,files);
        generateDOT(automat);
        dotToPNG();
    }

    private static void generateIndexTxt(FiniteAutomaton automat, File[] files) {
        final Map<String, ArrayList<Integer>> concurrencyMap = automat.getConcurrencyMap();
        try{
            FileWriter writer = new FileWriter(new File("example/index.txt"));
            concurrencyMap.forEach((s, integers) -> {
                try {
                    writer.append(s+'\n');
                    int index = 0;
                    for( Integer integer: integers){
                        if(index == 0 && !(integer.equals(0))){
                            writer.append(files[index].getName()+'\n');
                            writer.append(integer.toString()+'\n');
                        }
                        else if(!(integer - integers.get(index-1) == 0)){
                            writer.append(files[index].getName()+'\n');
                            Integer result = integer - integers.get(index-1);
                            writer.append(result.toString()+'\n');
                        }
                        index++;
                    }
                    writer.append('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            writer.flush();
            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private static void generateDOT(FiniteAutomaton automaton) {
        List<FiniteState> states = automaton.getAllStates();

        File file = new File("example/example.dot");
        try {
            FileWriter writer = new FileWriter(file);
            writer.append("digraph { \n\t rankdir = \"LR\"; \n");

            for (FiniteState finiteState: states) {
                if(finiteState.isFinal()){
                    writer.append("\t node [shape=doublecircle] Node").append(finiteState.getName()).append(" [label ="+'"').append(finiteState.getName()).append('"'+"];\n");
                }else{
                    writer.append("\t node [shape=circle] Node").append(finiteState.getName()).append(" [label ="+'"').append(finiteState.getName()).append('"'+"];\n");
                }
            }

            for (FiniteState finiteState : states){
                String nodeName = finiteState.getName();
                for (FiniteTransition transition: finiteState.getTransitions()){
                    writer.append("\t Node").append(nodeName).append(" -> Node").append(transition.getState().getName()).append("[label=").append(String.valueOf('"')).append(String.valueOf(transition.getChar())).append(String.valueOf('"')).append("];\n");
                }
            }
            writer.append("}");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the DOT source from a file,
     * convert to image and store the image in the file system.
     */
    private static void dotToPNG()
    {
        String input = "example/example.dot";

        GraphViz gv = new GraphViz();
        gv.readSource(input);
        System.out.println(gv.getDotSource());

        String type = "png";
        String representationType= "dot";

        File out = new File("example/example." + type);
        gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, representationType), out);
    }
}
