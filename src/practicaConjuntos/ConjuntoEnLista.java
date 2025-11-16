package practicaConjuntos;

public class ConjuntoEnLista {

    private Nodo cab;

    public ConjuntoEnLista() {
        this.cab = null;
    }

    public Nodo getCab() {
        return cab;
    }

    public void setCab(Nodo cab) {
        this.cab = cab;
    }

    // Inserta al inicio si no pertenece
    public void insertar(String dato) {
        if (dato == null) return;
        if (!pertenece(dato)) {
            Nodo nuevo = new Nodo(dato);
            nuevo.setSig(cab);
            cab = nuevo;
        }
    }

    public boolean pertenece(String dato) {
        if (dato == null) return false;
        Nodo p = cab;
        while (p != null) {
            String actual = p.getDato();
            if (actual != null && actual.equalsIgnoreCase(dato)) {
                return true;
            }
            p = p.getSig();
        }
        return false;
    }

    // Cuenta elementos de la lista (cardinalidad del conjunto)
    private int cardinalidad() {
        int c = 0;
        Nodo p = cab;
        while (p != null) {
            if (p.getDato() != null) c++;
            p = p.getSig();
        }
        return c;
    }

    // this ⊆ B ?
    public boolean subconjunto(ConjuntoEnLista B) {
        Nodo p = cab;
        while (p != null) {
            String dato = p.getDato();
            if (dato != null && !B.pertenece(dato)) {
                return false;
            }
            p = p.getSig();
        }
        return true;
    }

    // A ∪ B
    public ConjuntoEnLista union(ConjuntoEnLista B) {
        ConjuntoEnLista C = new ConjuntoEnLista();

        // Primero todos los de this
        Nodo p = this.cab;
        while (p != null) {
            String dato = p.getDato();
            if (dato != null) {
                C.insertar(dato);
            }
            p = p.getSig();
        }

        // Luego los de B que no estén ya
        Nodo q = B.getCab();
        while (q != null) {
            String datoB = q.getDato();
            if (datoB != null) {
                C.insertar(datoB);  // insertar ya evita repetidos
            }
            q = q.getSig();
        }

        return C;
    }

    // A ∩ B
    public ConjuntoEnLista interseccion(ConjuntoEnLista B) {
        ConjuntoEnLista C = new ConjuntoEnLista();
        Nodo p = cab;
        while (p != null) {
            String dato = p.getDato();
            if (dato != null && B.pertenece(dato)) {
                C.insertar(dato);
            }
            p = p.getSig();
        }
        return C;
    }

    // A − B
    public ConjuntoEnLista diferencia(ConjuntoEnLista B) {
        ConjuntoEnLista C = new ConjuntoEnLista();
        Nodo p = cab;
        while (p != null) {
            String dato = p.getDato();
            if (dato != null && !B.pertenece(dato)) {
                C.insertar(dato);
            }
            p = p.getSig();
        }
        return C;
    }

    // A Δ B = (A − B) ∪ (B − A)
    public ConjuntoEnLista diferenciaSimetrica(ConjuntoEnLista B) {
        ConjuntoEnLista AmenosB = this.diferencia(B);
        ConjuntoEnLista BmenosA = B.diferencia(this);
        return AmenosB.union(BmenosA);
    }

    // A = B ?
    public boolean igualdad(ConjuntoEnLista B) {
        // Si tienen distinta cardinalidad, ya sabemos que no son iguales
        if (this.cardinalidad() != B.cardinalidad()) {
            return false;
        }

        // this ⊆ B y B ⊆ this
        return this.subconjunto(B) && B.subconjunto(this);
    }

    // A^c respecto al universal
    public ConjuntoEnLista complemento(String[] universal) {
        ConjuntoEnLista C = new ConjuntoEnLista();
        if (universal == null) return C;

        for (String elem : universal) {
            if (elem != null && !this.pertenece(elem)) {
                C.insertar(elem);
            }
        }
        return C;
    }

    // ---------- Mostrar / representación ----------

    public void mostrar() {
        System.out.println(comoCadena());
    }

    public String comoCadena() {
        StringBuilder sb = new StringBuilder("{ ");
        Nodo p = cab;

        boolean primero = true;
        while (p != null) {
            String dato = p.getDato();
            if (dato != null) {
                if (!primero) sb.append(", ");
                sb.append(dato);
                primero = false;
            }
            p = p.getSig();
        }

        sb.append(" }");
        return sb.toString();
    }

    @Override
    public String toString() {
        return comoCadena();
    }
}

