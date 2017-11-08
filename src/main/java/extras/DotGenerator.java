package extras;

import Automaton.FiniteState;
import Automaton.FiniteTransition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DotGenerator {

    private static int[] occurrences = new int[256];

    public static void generateDOT(FiniteState state, String filename){
        File file = new File(filename+".dot");
        occurrences = new int[256];
        try {
            FileWriter writer = new FileWriter(file);
            writer.append("digraph { \n\t rankdir = \"LR\"; \n");
            writer.append("\t node [shape=circle] ").append(state.getName().concat("0;\n")); //.append(" [label ="+'"').append(state.getName()).append('"'+"];\n");
            state.getTransitions().forEach( transition -> {
                generate(state ,transition, writer);
            });
            writer.append("}");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generate(FiniteState previousState, FiniteTransition transition, FileWriter writer)  {
        final FiniteState state = transition.getState();
        final String stateName = state.getName().equals(" ") ? "_" : state.getName();
        final int occurrence = occurrences[stateName.charAt(0)];
        final String previousName = previousState.getName().contains("InitialState") ? "InitialState0" : (previousState.getName().equals(" ")? "_" : previousState.getName());
        try {
            if(state.isFinal()) {
                writer.append("\t node [shape= doublecircle] ")
                        .append(stateName.concat(String.valueOf(occurrence)))
                        .append(" [label =" + '"').append(stateName.equals("_")? " " : stateName).append('"' + "];\n"); //
            }
            else{
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
            state.getTransitions().forEach(transition2 -> {
                int previousNodeIndex = occurrences[previousName.charAt(0)]; // TODO el problema esta en los indices.
                generate(state, transition2, writer);

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
