import Automaton.FiniteAutomaton;
import Automaton.FiniteAutomatonBuilder;
import Automaton.FiniteState;
import HtmlReader.HtmlReader;
import java.io.IOException;

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


        FiniteState q0 = new FiniteState("InitialState");

        FiniteAutomaton automat = new FiniteAutomaton(q0);

        //We should receive words from the .txt file
        automat.add("hello", "hello world", "world");
        automat.add("hellu");
        FiniteAutomaton.Result result3 = automat.evaluate("hello");
        System.out.println("Result3 valid " + result3.isValid());

    }
}
