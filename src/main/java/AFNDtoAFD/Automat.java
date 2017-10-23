package AFNDtoAFD;

import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Sebas Belaustegui on 23/10/2017.
 */
public class Automat {
    private String Nombre;
    private int numEstados;
    private int EstadoInicial;
    private int EstadoActual;
    private TreeSet<String> Alfabeto;
    private TreeSet<Integer> estadoFinal;
    private TreeSet<Integer>[][] TablaTransiciones;

    public Automat() {
        super();
        Alfabeto = new TreeSet<>();
        estadoFinal = new TreeSet<>();
    }

    public Automat(String nombre, int nEstados, TreeSet<String> alfabeto, int q0, TreeSet<Integer> qend, TreeSet<Integer>[][] tablaTransiciones) {
        super();
        Nombre = nombre;
        this.numEstados = nEstados;
        Alfabeto = alfabeto;
        this.EstadoInicial = q0;
        estadoFinal = qend;
        TablaTransiciones = tablaTransiciones;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getnumEstados() {
        return numEstados;
    }

    public void setnumEstados(int nEstados) {
        this.numEstados = nEstados;
    }

    public TreeSet<String> getAlfabeto() {
        return Alfabeto;
    }

    public void setAlfabeto(TreeSet<String> alfabeto) {
        Alfabeto = alfabeto;
    }

    public int getEstadoInicial() {
        return EstadoInicial;
    }

    public void setEstadoInicial(int q0) {
        this.EstadoInicial = q0;
    }

    public TreeSet<Integer> getestadoFinal() {
        return estadoFinal;
    }

    public void setestadoFinal(TreeSet<Integer> qend) {
        estadoFinal = qend;
    }

    public TreeSet<Integer>[][] getTablaTransiciones() {
        return TablaTransiciones;
    }

    public void setTablaTransiciones(TreeSet<Integer>[][] tablaTransiciones) {
        TablaTransiciones = tablaTransiciones;
    }

    public void addEstadoFinal(int q) {
        estadoFinal.add(q);
    }

    public int getEstadoActual() {
        return EstadoActual;
    }

    @SuppressWarnings("unchecked")
    public void addLetraAlfabeto(String letra) {
        Alfabeto.add(letra);
        TablaTransiciones = new TreeSet[numEstados][Alfabeto.size()];
        iniciarTablaTransiciones();
    }

    private void iniciarTablaTransiciones() {
        for (int x = 0; x < numEstados; x++) {
            for (int y = 0; y < Alfabeto.size(); y++) {
                TablaTransiciones[x][y] = new TreeSet<>();
            }
        }
    }

    public void addTransicion(int q0, String e, int q1) {
        Vector<String> a = new Vector<>();
        a.addAll(Alfabeto);
        TablaTransiciones[q0][a.indexOf(e)].add(q1);
    }

    public boolean analizarPalabra(String palabra) {
        EstadoActual = EstadoInicial;
        String[] letras = palabra.split("");

        for (String l : letras) {
            if (!l.equals("")) {
                EstadoActual = funcion(EstadoActual, l);
                if (EstadoActual == -1) {
                    return false;
                }
            }
        }
        return estadoFinal.contains(EstadoActual);
    }

    private int funcion(int estadoActual, String e) {
        Vector<String> a = new Vector<>();
        a.addAll(Alfabeto);
        if (TablaTransiciones[estadoActual][a.indexOf(e)].isEmpty()) {
            return -1;
        } else {
            return TablaTransiciones[estadoActual][a.indexOf(e)].first();
        }
    }
}
