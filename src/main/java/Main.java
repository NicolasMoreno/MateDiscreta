import Automaton.FiniteAutomaton;
import Automaton.FiniteAutomatonBuilder;
import Automaton.FiniteState;
import Automaton.FiniteTransition;
import HtmlReader.HtmlReader;
import extras.GraphViz;
import guru.nidi.graphviz.attribute.Attributed;
import extras.GraphViz;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.*;

import guru.nidi.graphviz.model.*;

public class Main {
    public static void main(String[] args) {

        /*HtmlReader htmlReader;
        try {
            htmlReader = new HtmlReader("/Users/nicolasmoreno/Nico/Facultad/Mate Dis/TpMateDiscreta/src/resources/files/htmlFile.html");
            System.out.println(htmlReader.readLine());
            System.out.println(htmlReader.readLine());
        } catch (IOException e2){
            e2.printStackTrace();
        }*/

        try{
            Graph g = graph("example1").directed().with(node("a").link(node("b")).link(node("c").link(node("c"))));
            Graphviz.fromGraph(g).width(200).render(Format.PNG).toFile(new File("example/ex1.png"));
        }catch (IOException e){
            System.out.println(e);
        }

        try {

            Node
                    init = node("a"),
                    execute = node("b"),
                    compare = node("c"),
                    mkString = node("d").with(Label.of("make a\nstring")),
                    printf = node("e");

            Graph g = graph("example2").directed().with(
                    node("q0").link(
                            to(node("q1").link(execute)).with("weight", 8),
                            to(init),
                            node("q2"),
                            to(printf).with(Label.of("transition")),
                            execute.link(
                                    graph().with(mkString, printf),
                                    to(compare),
                                    init.link(mkString)
                            )
                    )
            );
            Graphviz.fromGraph(g).width(200).render(Format.PNG).toFile(new File("example/ex1.png"));
        } catch (IOException e) {
            System.out.println(e);
        }


        /*
         * ---------------------- OURS AUTOMATON -------------------------------
         */
        FiniteState q0 = new FiniteState("InitialState");

        FiniteAutomaton automat = new FiniteAutomaton(q0);

        //We should receive words from the .txt file
        automat.add("hello", "hello world", "world");
        automat.add("hellu");
        FiniteAutomaton.Result result3 = automat.evaluate("hello");
        System.out.println("Result3 valid " + result3.isValid());

//        graphicInitialState("MANZAEXAMPLE", automat.getInitialState());

    }

    //TODO
    private static void graphic(FiniteState state) {
        List<FiniteState> states = state.getAllStates();

        File file = new File("example/example.dot");
        try {
            FileWriter writer = new FileWriter(file);
            writer.append("digraph { \n rankdir = \"LR\"; \n");

            for (FiniteState finiteState: states) {
                if(finiteState.isFinal()){
                    writer.append("node [shape=doublecircle] Node" + finiteState.getName() + " [label =" + finiteState.getName() + "];");
                }else{
                    writer.append("node [shape=circle] Node").append(finiteState.getName()).append(" [label =").append(finiteState.getName()).append("];");
                }
            }

            for (FiniteState finiteState : states){
                String nodeName = finiteState.getName();
                for (FiniteTransition transition: finiteState.getTransitions()){
                    writer.append("Node"+nodeName+" -> Node"+transition.getState().getName()+ "[label=["+'"'+
                            transition.getChar()+'"'+"];");
                }
            }
            writer.append("\n}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void graphDot(){
        String command = "dot -Tpng " + "-o " + "outfile.png example.gv";
        try {
            Runtime.getRuntime().exec(command);
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
        String input = "/example/example.dot";

        GraphViz gv = new GraphViz();
        gv.readSource(input);
        System.out.println(gv.getDotSource());

        String type = "png";
        String representationType= "dot";

        File out = new File("/example/example." + type);
        gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, representationType), out);
    }
}
