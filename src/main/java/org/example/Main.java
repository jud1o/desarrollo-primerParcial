package org.example;

import java.util.Scanner;
import static com.desarrollo.parcial.MutanteController.isMutant;

public class Main {

    public static void main(String[] args) {
        System.out.println("Por favor, ingrese una secuencia de ADN para saber si es mutante");
        Scanner scaner = new Scanner(System.in);
        String adnIngresado = scaner.nextLine();
        scaner.close();
        String[] adn = adnIngresado.split(","); //la coma separa las filas
        System.out.println ( isMutant(adn) ); //Muestra en pantalla si es mutante o no
    }

}