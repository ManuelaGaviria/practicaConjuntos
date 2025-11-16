package practicaConjuntos;

public class Nodo {
    private String dato;
    private Nodo sig;

    public Nodo(String dato) {
        this.dato = dato;
        this.sig = null;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public Nodo getSig() {
        return sig;
    }

    public void setSig(Nodo sig) {
        this.sig = sig;
    }

    @Override
    public String toString() {
        return dato;
    }
}
