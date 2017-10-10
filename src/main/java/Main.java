import Automaton.FiniteAutomaton;
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


        FiniteState q0 = new FiniteState("q0");
        FiniteState q1 = new FiniteState("q1");

        q1.setFinal();

        q0.addTransition(q0, '0');
        q0.addTransition(q1,  '1');
        q1.addTransition(q1, '1');

        FiniteAutomaton automat = new FiniteAutomaton(q0);

        FiniteAutomaton.Result result2 = automat.evaluate("0110010");
        System.out.println("Result2 isValid " + result2.isValid());

    }
}
