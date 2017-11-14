package extras;

import Automaton.FiniteState;
import Automaton.FiniteTransition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DotGenerator {

    private int[] occurrences;
    private boolean DFAMode;

    public DotGenerator(boolean DFAMode){
        this.DFAMode = DFAMode;
    }

    public void changeMode(){
        this.DFAMode = !this.DFAMode;
    }

    public void generateDOT(FiniteState state, String filename){
        File file = new File(filename+".dot");
        this.occurrences = new int[256];
        try {
            FileWriter writer = new FileWriter(file);
            writer.append("digraph { \n\t rankdir = \"LR\"; \n");
            writer.append("\t node [shape=circle] ").append(state.getName().concat("0;\n")); //.append(" [label ="+'"').append(state.getName()).append('"'+"];\n");
            if(this.DFAMode){
                state.getTransitions().forEach( transition -> {
                    generateDFA(state ,transition, writer,0);
                });
            }else {
                state.getTransitions().forEach( transition -> {
                    generateNFA(state ,transition, writer);
                });
            }
            writer.append("}");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateDFA(FiniteState previousState, FiniteTransition transition, FileWriter writer, int previousNodeIndex)  {
        final FiniteState state = transition.getState();
        final String stateName = state.getName().equals(" ") ? "_" : state.getName();
        final int occurrence = this.occurrences[stateName.charAt(0)];
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
                    .append(previousName.equals("InitialState0")? previousName : previousName.concat(String.valueOf(previousNodeIndex)))
                    .append(" -> ")
                    .append(stateName.concat(String.valueOf(occurrence)))
                    .append("[label=").append(String.valueOf('"')).append(stateName.equals("_")? " " : stateName).append(String.valueOf('"')).append("];\n");
            this.occurrences[stateName.charAt(0)] += 1;
            int previousIndex = 0;
            if(!previousName.equals("InitialState0")) {
                previousIndex = this.occurrences[stateName.charAt(0)]-1; // TODO el problema esta en los indices.
            }
            for(FiniteTransition transition2: state.getTransitions()){
                generateDFA(state,transition2,writer,previousIndex);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateNFA(FiniteState previousState, FiniteTransition transition, FileWriter writer){
        final FiniteState state = transition.getState();
        final String stateName = state.getName().equals(" ") ? "_" : state.getName();
        final int occurrence = this.occurrences[stateName.charAt(0)];
        final String previousName = previousState.getName().contains("InitialState") ? "InitialState0" : (previousState.getName().equals(" ")? "_" : previousState.getName());
        try{
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
            this.occurrences[stateName.charAt(0)] += 1;
            state.getTransitions().forEach(transition2 -> {
                generateNFA(state,transition2,writer);
            });
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
