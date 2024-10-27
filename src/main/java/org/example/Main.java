package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

import com.desarrollo.parcial.MutanteService;
import com.desarrollo.parcial.repository.MutanteRepository;

@SpringBootApplication
public class Main {

    @Autowired
    private static MutanteRepository mutanteRepository; // Usar inyección de dependencias

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args); // Inicializa el contexto de Spring

        System.out.println("Por favor, ingrese una secuencia de ADN para saber si es mutante");
        Scanner scanner = new Scanner(System.in);
        String adnIngresado = scanner.nextLine();
        scanner.close();
        String[] adn = adnIngresado.split(","); // la coma separa las filas

        // Crear una instancia de MutanteService pasando el MutanteRepository
        MutanteService mutanteService = new MutanteService(mutanteRepository);

        // Llamar al método isMutant a través de la instancia
        boolean esMutante = mutanteService.isMutant(adn);
        System.out.println(esMutante); // Muestra en pantalla si es mutante o no
    }
}

