package practicaConjuntos;

public class ConjuntoEnVector {

    // n = cantidad lógica de elementos usados (0..n-1)
    private int n;
    private String[] vec;

    public ConjuntoEnVector(int n) {
        this.n = n;
        this.vec = new String[n];
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String[] getVec() {
        return vec;
    }

    public void setVec(String[] vec) {
        this.vec = vec;
    }

    public String getDato(int i) {
        return vec[i];
    }

    public void setDato(String d, int i) {
        this.vec[i] = d;
    }

    // Ya no hace nada, lo puedes borrar si no lo usan en tu taller
    public void conjuntoVector(int n) {
    }

    // ---------- Utilidad para mostrar ----------
    public void mostrar() {
        System.out.println(comoCadena());
    }

    // Representación bonita del conjunto
    public String comoCadena() {
        StringBuilder sb = new StringBuilder("{ ");
        for (int i = 0; i < n; i++) {
            if (vec[i] != null) {
                sb.append(vec[i]);
                if (i < n - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append(" }");
        return sb.toString();
    }

    // Si quieres, también puedes sobreescribir toString:
    @Override
    public String toString() {
        return comoCadena();
    }

    // ---------- Métodos básicos ----------

    public boolean pertenece(String elemento) {
        if (elemento == null) return false;
        for (int i = 0; i < n; i++) {
            String dato = vec[i];
            if (dato != null && dato.equalsIgnoreCase(elemento)) {
                return true;
            }
        }
        return false;
    }

    // Cuenta cuántos elementos NO nulos hay (por si en algún momento dejas huecos)
    private int cardinalidad() {
        int c = 0;
        for (int i = 0; i < n; i++) {
            if (vec[i] != null) c++;
        }
        return c;
    }

    // this ⊆ B ?
    public boolean subconjunto(ConjuntoEnVector B) {
        for (int i = 0; i < n; i++) {
            String dato = vec[i];
            if (dato != null && !B.pertenece(dato)) {
                return false;
            }
        }
        return true;
    }

    // A ∪ B
    public ConjuntoEnVector union(ConjuntoEnVector B) {
        // Capacidad máxima: todos los de this + todos los de B
        ConjuntoEnVector C = new ConjuntoEnVector(this.n + B.n);
        int k = 0;

        // Copiamos todos los de A (this), evitando duplicar dentro del mismo C
        for (int i = 0; i < this.n; i++) {
            String dato = this.vec[i];
            if (dato != null && !C.pertenece(dato)) {
                C.setDato(dato, k++);
            }
        }

        // Agregamos los de B que no estén ya en C
        for (int j = 0; j < B.n; j++) {
            String datoB = B.vec[j];
            if (datoB != null && !C.pertenece(datoB)) {
                C.setDato(datoB, k++);
            }
        }

        C.setN(k);  // tamaño lógico real
        return C;
    }

    // A ∩ B
    public ConjuntoEnVector interseccion(ConjuntoEnVector B) {
        ConjuntoEnVector C = new ConjuntoEnVector(this.n);
        int k = 0;

        for (int i = 0; i < this.n; i++) {
            String dato = this.vec[i];
            if (dato != null && B.pertenece(dato) && !C.pertenece(dato)) {
                C.setDato(dato, k++);
            }
        }

        C.setN(k);
        return C;
    }

    // A = B ?
    public boolean igualdad(ConjuntoEnVector B) {
        // Comparamos por cardinalidad (cantidad real de elementos)
        if (this.cardinalidad() != B.cardinalidad()) {
            return false;
        }

        // Todos los elementos de this deben pertenecer a B
        for (int i = 0; i < this.n; i++) {
            String dato = this.vec[i];
            if (dato != null && !B.pertenece(dato)) {
                return false;
            }
        }
        return true;
    }

    // A^c (respecto al universal)
    public ConjuntoEnVector complemento(String[] universal) {
        ConjuntoEnVector C = new ConjuntoEnVector(universal.length);
        int k = 0;

        for (int i = 0; i < universal.length; i++) {
            String elemento = universal[i];
            if (elemento != null && !this.pertenece(elemento)) {
                C.setDato(elemento, k++);
            }
        }

        C.setN(k);
        return C;
    }

    // A − B
    public ConjuntoEnVector diferencia(ConjuntoEnVector B) {
        ConjuntoEnVector C = new ConjuntoEnVector(this.n);
        int k = 0;

        for (int i = 0; i < this.n; i++) {
            String dato = this.vec[i];
            if (dato != null && !B.pertenece(dato) && !C.pertenece(dato)) {
                C.setDato(dato, k++);
            }
        }

        C.setN(k);
        return C;
    }

    // A Δ B = (A − B) ∪ (B − A)
    public ConjuntoEnVector diferenciaSimetrica(ConjuntoEnVector B) {
        ConjuntoEnVector parte1 = this.diferencia(B);
        ConjuntoEnVector parte2 = B.diferencia(this);
        return parte1.union(parte2);
    }
}

