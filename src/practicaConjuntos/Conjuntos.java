package practicaConjuntos;

import javax.swing.JOptionPane;
import java.util.Arrays;

public class Conjuntos {

    public static void main(String[] args) {
        // 1) Crear el conjunto universal con JOptionPane
        String[] universal = crearConjuntoUniversal();

        // 2) Elegir tipo de conjunto
        int tipo = pedirEntero(
                "Seleccione tipo de conjunto:\n" +
                        "1. Conjunto en Vector\n" +
                        "2. Conjunto en Lista\n\n" +
                        "Opción:",
                1, 2
        );

        if (tipo == 1) {
            menuVector(universal);
        } else if (tipo == 2) {
            menuLista(universal);
        } else {
            JOptionPane.showMessageDialog(null, "Opción inválida.");
        }
    }

    // ---------------------- CREAR UNIVERSAL ----------------------
    private static String[] crearConjuntoUniversal() {
        int n = pedirEntero(
                "¿Cuántos elementos tendrá el conjunto universal?\n" +
                        "(máximo 10)",
                1, 10
        );

        String[] universal = new String[n];

        for (int i = 0; i < n; i++) {
            String elem;
            do {
                elem = JOptionPane.showInputDialog(
                        null,
                        "Elemento " + (i + 1) + " del conjunto universal:"
                );

                if (elem == null) { // Cancel
                    JOptionPane.showMessageDialog(null,
                            "No puedes cancelar en este punto. Intenta de nuevo.");
                    elem = "";
                    continue;
                }

                elem = elem.trim();

                if (elem.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "El elemento no puede estar vacío.");
                    continue;
                }

                if (contiene(universal, elem, i)) {
                    JOptionPane.showMessageDialog(null,
                            "Ese elemento ya está en el universal. Digita otro diferente.");
                    elem = "";
                }

            } while (elem.isEmpty());

            universal[i] = elem;
        }

        JOptionPane.showMessageDialog(null,
                "Conjunto universal creado:\n" + Arrays.toString(universal));

        return universal;
    }

    // Verifica si el arreglo contiene un elemento (hasta la posición 'limite')
    private static boolean contiene(String[] arreglo, String elem, int limite) {
        for (int i = 0; i < limite; i++) {
            if (arreglo[i] != null && arreglo[i].equalsIgnoreCase(elem)) {
                return true;
            }
        }
        return false;
    }

    // ---------------------- MENÚ PARA CONJUNTOS EN VECTOR ----------------------
    public static void menuVector(String[] universal) {
        JOptionPane.showMessageDialog(null,
                "Creación del Conjunto A:");
        ConjuntoEnVector A = crearConjuntoEnVector("A", universal);

        JOptionPane.showMessageDialog(null,
                "Creación del Conjunto B:");
        ConjuntoEnVector B = crearConjuntoEnVector("B", universal);

        int opcion;
        do {
            opcion = pedirEntero(
                    "--- MENÚ DE OPERACIONES (VECTOR) ---\n" +
                            "1. Unión\n" +
                            "2. Intersección\n" +
                            "3. Diferencia (A - B)\n" +
                            "4. Diferencia Simétrica\n" +
                            "5. Subconjunto (A ⊆ B)\n" +
                            "6. Igualdad (A = B)\n" +
                            "7. Complemento de A\n" +
                            "8. Salir\n\n" +
                            "Opción:",
                    1, 8
            );

            ConjuntoEnVector resultado;

            switch (opcion) {
                case 1 -> {
                    resultado = A.union(B);
                    JOptionPane.showMessageDialog(null,
                            "A ∪ B = " + resultado.comoCadena());
                }
                case 2 -> {
                    resultado = A.interseccion(B);
                    JOptionPane.showMessageDialog(null,
                            "A ∩ B = " + resultado.comoCadena());
                }
                case 3 -> {
                    resultado = A.diferencia(B);
                    JOptionPane.showMessageDialog(null,
                            "A − B = " + resultado.comoCadena());
                }
                case 4 -> {
                    resultado = A.diferenciaSimetrica(B);
                    JOptionPane.showMessageDialog(null,
                            "Diferencia simétrica(A, B) = " + resultado.comoCadena());
                }
                case 5 -> {
                    boolean esSub = A.subconjunto(B);
                    JOptionPane.showMessageDialog(null,
                            esSub ? "A es subconjunto de B" : "A NO es subconjunto de B");
                }
                case 6 -> {
                    boolean iguales = A.igualdad(B);
                    JOptionPane.showMessageDialog(null,
                            iguales ? "A es igual a B" : "A NO es igual a B");
                }
                case 7 -> {
                    resultado = A.complemento(universal);
                    JOptionPane.showMessageDialog(null,
                            "Complemento de A (respecto al universal) = " + resultado.comoCadena());
                }
                case 8 -> JOptionPane.showMessageDialog(null, "Fin del programa.");
            }

        } while (opcion != 8);
    }

    // ---------------------- CREAR CONJUNTO EN VECTOR ----------------------
    public static ConjuntoEnVector crearConjuntoEnVector(String nombre, String[] universal) {
        int n = pedirEntero(
                "¿Cuántos elementos tendrá el conjunto " + nombre + "?\n" +
                        "(0 hasta " + universal.length + ")",
                0, universal.length
        );

        ConjuntoEnVector conjunto = new ConjuntoEnVector(n);

        for (int i = 0; i < n; ) {
            String elem = JOptionPane.showInputDialog(
                    null,
                    "Elemento " + (i + 1) + " del conjunto " + nombre + ":\n" +
                            "Universal: " + Arrays.toString(universal)
            );

            if (elem == null) { // cancel
                JOptionPane.showMessageDialog(null,
                        "No puedes cancelar aquí. Si no quieres más elementos, pon un número menor al inicio.");
                continue;
            }

            elem = elem.trim();

            if (elem.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "El elemento no puede estar vacío.");
                continue;
            }

            if (!estaEnUniversal(elem, universal)) {
                JOptionPane.showMessageDialog(null,
                        "Ese elemento NO pertenece al conjunto universal.\nIntenta de nuevo.");
                continue;
            }

            // Aquí podrías evitar repetidos dentro del mismo conjunto si quieres
            // (si tu ConjuntoEnVector tiene un método contiene, lo usas aquí).

            conjunto.setDato(elem, i);
            i++; // solo incrementa si fue válido
        }

        JOptionPane.showMessageDialog(null,
                "Conjunto " + nombre + " creado: " + conjunto.comoCadena());

        return conjunto;
    }

    // ---------------------- MENÚ PARA CONJUNTOS EN LISTA ----------------------
    public static void menuLista(String[] universal) {
        JOptionPane.showMessageDialog(null,
                "Creación del Conjunto A (LISTA):");
        ConjuntoEnLista A = crearConjuntoEnLista("A", universal);

        JOptionPane.showMessageDialog(null,
                "Creación del Conjunto B (LISTA):");
        ConjuntoEnLista B = crearConjuntoEnLista("B", universal);

        int opcion;
        do {
            opcion = pedirEntero(
                    "--- MENÚ DE OPERACIONES (LISTA) ---\n" +
                            "1. Unión\n" +
                            "2. Intersección\n" +
                            "3. Diferencia (A - B)\n" +
                            "4. Diferencia Simétrica\n" +
                            "5. Subconjunto (A ⊆ B)\n" +
                            "6. Igualdad (A = B)\n" +
                            "7. Complemento de A\n" +
                            "8. Salir\n\n" +
                            "Opción:",
                    1, 8
            );

            ConjuntoEnLista resultado;

            switch (opcion) {
                case 1 -> {
                    resultado = A.union(B);
                    JOptionPane.showMessageDialog(null,
                            "A ∪ B = " + resultado.comoCadena());
                }
                case 2 -> {
                    resultado = A.interseccion(B);
                    JOptionPane.showMessageDialog(null,
                            "A ∩ B = " + resultado.comoCadena());
                }
                case 3 -> {
                    resultado = A.diferencia(B);
                    JOptionPane.showMessageDialog(null,
                            "A − B = " + resultado.comoCadena());
                }
                case 4 -> {
                    resultado = A.diferenciaSimetrica(B);
                    JOptionPane.showMessageDialog(null,
                            "Diferencia simétrica(A, B) = " + resultado.comoCadena());
                }
                case 5 -> {
                    boolean esSub = A.subconjunto(B);
                    JOptionPane.showMessageDialog(null,
                            esSub ? "A es subconjunto de B" : "A NO es subconjunto de B");
                }
                case 6 -> {
                    boolean iguales = A.igualdad(B);
                    JOptionPane.showMessageDialog(null,
                            iguales ? "A es igual a B" : "A NO es igual a B");
                }
                case 7 -> {
                    resultado = A.complemento(universal);
                    JOptionPane.showMessageDialog(null,
                            "Complemento de A (respecto al universal) = " + resultado.comoCadena());
                }
                case 8 -> JOptionPane.showMessageDialog(null, "Fin del programa (LISTA).");
            }

        } while (opcion != 8);
    }

    // ---------------------- CREAR CONJUNTO EN LISTA ----------------------
    public static ConjuntoEnLista crearConjuntoEnLista(String nombre, String[] universal) {
        int n = pedirEntero(
                "¿Cuántos elementos tendrá el conjunto " + nombre + "?\n" +
                        "(0 hasta " + universal.length + ")",
                0, universal.length
        );

        ConjuntoEnLista conjunto = new ConjuntoEnLista();

        for (int i = 0; i < n; ) {
            String elem = JOptionPane.showInputDialog(
                    null,
                    "Elemento " + (i + 1) + " del conjunto " + nombre + ":\n" +
                            "Universal: " + Arrays.toString(universal)
            );

            if (elem == null) { // Cancelar
                JOptionPane.showMessageDialog(null,
                        "No puedes cancelar aquí. Si no quieres más elementos,\n" +
                                "pon un número menor al inicio cuando te pregunte la cantidad.");
                continue;
            }

            elem = elem.trim();

            if (elem.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "El elemento no puede estar vacío.");
                continue;
            }

            if (!estaEnUniversal(elem, universal)) {
                JOptionPane.showMessageDialog(null,
                        "Ese elemento NO pertenece al conjunto universal.\nIntenta de nuevo.");
                continue;
            }

            // Evitar repetidos dentro del mismo conjunto (opcional pero recomendable)
            if (conjunto.pertenece(elem)) {
                JOptionPane.showMessageDialog(null,
                        "Ese elemento ya está en el conjunto " + nombre + ".\nIntenta con otro.");
                continue;
            }

            conjunto.insertar(elem);
            i++; // solo si fue válido
        }

        JOptionPane.showMessageDialog(null,
                "Conjunto " + nombre + " creado: " + conjunto.comoCadena());

        return conjunto;
    }

    // ---------------------- VALIDAR UNIVERSAL ----------------------
    private static boolean estaEnUniversal(String elem, String[] universal) {
        for (String u : universal) {
            if (u.equalsIgnoreCase(elem)) return true;
        }
        return false;
    }

    // ---------------------- PEDIR ENTERO CON VALIDACIÓN ----------------------
    private static int pedirEntero(String mensaje, int min, int max) {
        int valor = 0;
        boolean valido = false;

        while (!valido) {
            String input = JOptionPane.showInputDialog(null, mensaje);
            if (input == null) {
                JOptionPane.showMessageDialog(null,
                        "No puedes cancelar aquí, ingresa un número.");
                continue;
            }

            try {
                valor = Integer.parseInt(input.trim());
                if (valor < min || valor > max) {
                    JOptionPane.showMessageDialog(null,
                            "Debe estar entre " + min + " y " + max + ".");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Entrada inválida. Escribe un número entero.");
            }
        }

        return valor;
    }
}

