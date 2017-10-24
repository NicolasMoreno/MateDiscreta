import Automaton.FiniteAutomaton;
import Automaton.FiniteState;

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

        /*try{
            Graph g = graph("example1").directed().with(node("a").link(node("b")).link(node("c")));
            Graphviz.fromGraph(g).width(200).render(Format.PNG).toFile(new File("example/ex1.png"));
        }catch (IOException e){
            System.out.println(e);
        }*/

        FiniteState q0 = new FiniteState("InitialState");

        FiniteAutomaton automat = new FiniteAutomaton(q0);

        //We should receive words from the .txt file or from the console input by the user
   /*     automat.addDeterministically("hello", "hello world", "world");
        automat.addDeterministically("hellu");
        final FiniteAutomaton.Result world = automat.evaluate("world");
        System.out.println("world result valid" + world.isValid());*/
//        automat.emptyAutomaton();
        automat.addNonDeterministically("hola","mundo", "hola mundo");
        automat.transformToDeterministic();
        automat.evaluate("hola mundo hola mundo hola hola mundo hola hola mundo hola hola mundo mundo");

    }
}
