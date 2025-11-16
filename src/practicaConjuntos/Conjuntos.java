package practicaConjuntos;

import java.util.Scanner;

public class Conjuntos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] universal = {"perro", "gato", "loro", "conejo", "pez", "hamster"};

        System.out.println("Seleccione tipo de conjunto:");
        System.out.println("1. Conjunto en Vector");
        System.out.println("2. Conjunto en Lista");
        System.out.print("Opcion: ");
        int tipo = sc.nextInt();
        sc.nextLine();
/*
        if (tipo == 1) {
            menuVector(universal, sc);
        } else if (tipo == 2) {
            menuLista(universal, sc);
        } else {
            System.out.println("Opcion invalida.");
        }*/
    }
}
