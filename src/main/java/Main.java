import Automaton.FiniteAutomaton;
import Automaton.FiniteAutomatonBuilder;
import Automaton.FiniteState;
import Automaton.FiniteTransition;
import HtmlReader.HtmlReader;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.IOException;

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
    private static void graphicInitialState(String filesName, FiniteState state) {
        Graph graph = graph(filesName).directed().with(
                graphic(node(state.getName()), state)
        );

        try {
            Graphviz.fromGraph(graph).width(200).render(Format.PNG).toFile(new File("example/" + filesName + ".png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //TODO
    private static Node graphic(Node node, FiniteState state) {

        if (!state.isFinal()) {

            for (FiniteTransition transition : state.getTransitions()) {
                FiniteState transState = transition.getState();

//                Node nodeAux = node(transState.getName()).with(
//                        Label.of(String.valueOf(transition.getChar()))
//                );
//
//                node.link(nodeAux).link(graphic(nodeAux, transState));

            }

        } else {
            node.link(node(state.getName()));
        }
        return node;
    }
}
