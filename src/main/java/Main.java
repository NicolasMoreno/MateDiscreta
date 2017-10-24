import Automaton.FiniteAutomaton;
import Automaton.FiniteState;
import Automaton.FiniteTransition;
import extras.GraphViz;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la ruta o el nombre del archivo que desea levantar: ");
        String path = scanner.nextLine();
        FiniteState q0 = new FiniteState("InitialState");
        FiniteAutomaton automat = new FiniteAutomaton(q0);
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                automat.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FiniteAutomaton.Result result = automat.evaluate("hola");
        System.out.println("Result valid " + result.isValid());

        generateDOT(automat);
        dotToPNG();
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
