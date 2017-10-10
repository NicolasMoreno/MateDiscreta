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

        FiniteAutomaton.Result result = automat.evaluate("011000");
        FiniteAutomaton.Result result2 = automat.evaluate("0110010");


        /*FiniteState q0 = new FiniteState("q0");
        FiniteState q1 = new FiniteState("q1");
        FiniteState q2 = new FiniteState("q2");
        FiniteState q3 = new FiniteState("q3");
        FiniteState q4 = new FiniteState("q4");
        q4.setFinal();

        q0.addTransition(q1,'a');
        q0.addTransition(q2,'b');

        FiniteAutomaton fa = new FiniteAutomaton(q0);*/
    }
}
