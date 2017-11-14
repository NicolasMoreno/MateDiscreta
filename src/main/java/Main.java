import Automaton.FiniteAutomaton;
import Automaton.FiniteState;
import Automaton.FiniteTransition;
import HtmlReader.HtmlReader;
import extras.GraphViz;

import java.io.*;
import java.util.ArrayList;
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
            generateDOT(automat.getInitialState(), "example/nfa");
            dotToPNG("example/nfa");
            automat.transformToDeterministic();
            int indexFile = 0;
            assert files != null;
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
        generateDOT(automat.getInitialState(), "example/dfa");
        dotToPNG("example/dfa");
    }

    private static void generateIndexTxt(FiniteAutomaton automat, File[] files) {
        final Map<String, ArrayList<Integer>> concurrencyMap = automat.getConcurrencyMap();
        try{
            FileWriter writer = new FileWriter(new File("example/index.txt"));
            concurrencyMap.forEach((s, integers) -> {
                try {
                    writer.append(s).append(String.valueOf('\n'));
                    int index = 0;
                    for( Integer integer: integers){
                        if(index == 0 && !(integer.equals(0))){
                            writer.append(files[index].getName()).append(String.valueOf('\n'));
                            writer.append(integer.toString()).append(String.valueOf('\n'));
                        }
                        else if(index != 0 && !(integer - integers.get(index-1) == 0)){
                            writer.append(files[index].getName()).append(String.valueOf('\n'));
                            Integer result = integer - integers.get(index-1);
                            writer.append(result.toString()).append(String.valueOf('\n'));
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


    private static void generateDOT(FiniteState state, String filename){
        File file = new File(filename+".dot");
        int[] occurrences = new int[256];
        try {
            FileWriter writer = new FileWriter(file);
            writer.append("digraph { \n\t rankdir = \"LR\"; \n");
            writer.append("\t node [shape=circle] ").append(state.getName().concat("0;\n"));
            state.getTransitions().forEach( transition -> generate(state ,transition, writer, occurrences));
            writer.append("}");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generate(FiniteState previousState, FiniteTransition transition, FileWriter writer, int[] occurrences)  {
        final FiniteState state = transition.getState();
        final String stateName = state.getName().equals(" ") ? "_" : state.getName();
        final int occurrence = occurrences[stateName.charAt(0)];
        final String previousName = previousState.getName().contains("InitialState") ? "InitialState0" : (previousState.getName().equals(" ")? "_" : previousState.getName());
        try {
            if(state.isFinal()) {
                writer.append("\t node [shape= doublecircle] ")
                        .append(stateName.concat(String.valueOf(occurrence)))
                        .append(" [label =" + '"').append(stateName.equals("_")? " " : stateName).append('"' + "];\n"); //
            }else{
                writer.append("\t node [shape=circle] ")
                        .append(stateName.concat(String.valueOf(occurrence)))
                        .append(" [label =" + '"').append(stateName.equals("_")? " " : stateName).append('"' + "];\n"); //
            }
            writer.append("\t ")
                    .append(previousName.equals("InitialState0")? previousName : previousName.concat(String.valueOf(occurrences[previousName.charAt(0)]-1)))
                    .append(" -> ")
                    .append(stateName.concat(String.valueOf(occurrence)))
                    .append("[label=").append(String.valueOf('"')).append(stateName.equals("_")? " " : stateName).append(String.valueOf('"')).append("];\n");
            occurrences[stateName.charAt(0)] += 1;
            if(!state.isFinal()) state.getTransitions().forEach(transition2 -> generate(state, transition2, writer, occurrences));
            else state.getTransitions().forEach(trans ->{
                try {
                    writer.append("\t ")
                            .append("[label=").append(String.valueOf('"')).append(trans.getState().getName().equals("_")? " " : trans.getState().getName()).append(String.valueOf('"')).append("];\n");
                    trans.getState().getTransitions().forEach(transition3 -> generate(trans.getState(), transition3, writer, occurrences));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the DOT source from a file,
     * convert to image and store the image in the file system.
     */
    private static void dotToPNG(String filename) {
        String input = filename+".dot";

        GraphViz gv = new GraphViz();
        gv.readSource(input);
        System.out.println(gv.getDotSource());

        String type = "png";
        String representationType= "dot";

        File out = new File(filename+"." + type);
        try {
            gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, representationType), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
