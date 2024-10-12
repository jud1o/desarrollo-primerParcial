package com.desarrollo.parcial;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mutante")
public class MutanteController {
    static boolean controlMatriz(char[][] arreglo2d, String[] adn ){ //Es un arreglo bidimensional
        for (int i = 0; i < adn.length; i++) {
            char [] adnIngresado = adn[i].toCharArray(); //Convierte la fila a un arreglo de caracteres
            if (adnIngresado.length!=adn.length || adn.length<4){
                //Comprueba que el valor de la longitud de la fila sea igual a la cantidad de filas (columnas), es decir, que sea cuadrada
                //También comprueba que la longitud sea de, al menos, cuatro
                return false;
            }
            if (!letras(adnIngresado)){
                return false;
            }
            arreglo2d[i] = adnIngresado;
        }
        return true;
    }

    static boolean letras(char [] adnIngresado){
        for (int i = 0; i < adnIngresado.length; i++) {
            if (adnIngresado[i]!='A' && adnIngresado[i]!='T' && adnIngresado[i]!='C' && adnIngresado[i]!='G'){
                //Comprueba que las letras que se ingresen sean las esperadas
                return false;
            }
        }
        return true;
    }

    static int horizontal(char[][] arreglo2d, int medidaMatriz){
        int secuencias=0;
        for (int i = 0; i < medidaMatriz; i++) { //recorre filas
            for (int j = 0; j < medidaMatriz; j++) { //recorre columnas
                if (medidaMatriz-j>=4) { //revisa que hayan al menos cuatro posiciones
                    if (arreglo2d[i][j] == arreglo2d[i][j+ 1] &&
                            arreglo2d[i][j] == arreglo2d[i][j + 2] &&
                            arreglo2d[i][j] == arreglo2d[i][j + 3]) {
                        secuencias++;
                        j = j + 3; //Se saltea las siguientes tres letras para no encontrar secuencias superpuestas
                        if (secuencias>1){ //con que encuentre una secuenta ya es mutante
                            return (secuencias); //por lo que retorna
                        }
                    }
                }
            }
        }
        return secuencias; //Si no encuentra devuelve 0
    }

    static int vertical(char[][] arreglo2d, int medidaMatriz){ //Idem método "horizontal"
        int secuencias=0;
        for (int i = 0; i < medidaMatriz; i++) {
            for (int j = 0; j < medidaMatriz; j++) {
                if (medidaMatriz-i>=4) {
                    if (arreglo2d[i][j] == arreglo2d[i+1][j] &&
                            arreglo2d[i][j] == arreglo2d[i+2][j] &&
                            arreglo2d[i][j] == arreglo2d[i+3][j]
                    ) {
                        secuencias++;
                        j = j + 3;
                        if (secuencias>1){
                            return (secuencias);
                        }
                    }
                }
            }
        }
        return secuencias;
    }

    static int diagonalIzqDer(char[][] arreglo2d, int medidaMatriz, int i, int j, String limiteFilaColumna){
        int secuencias =0;
        int restantes=0;
        while (i<=medidaMatriz-1 && j<=medidaMatriz-1){
            if (limiteFilaColumna == "J"){ //se usa J
                restantes=medidaMatriz-j;      //Según si es J o I va a analizar cuantos elementos
            }else{ //se usa I               //quedan disponibles al costado/por debajo
                restantes=medidaMatriz-i;
            }
            if (restantes>=4) { //Si quedan menos de 4 no intenta comparar las letras
                if (arreglo2d[i][j] == arreglo2d[i+1][j+1] &&
                        arreglo2d[i][j] == arreglo2d[i+2][j+2] &&
                        arreglo2d[i][j] == arreglo2d[i+3][j+3]
                ) {
                    secuencias++;
                }
            }
            i++;
            j++;
        }
        return secuencias;
    }

    static int secuenciasDiagonalIzDe (char[][] arreglo2d, int medidaMatriz){
        int secuencias=0;
        for (int indiceI = medidaMatriz-1; indiceI >= 0; indiceI--) { //Empieza en la última fila y termina en la primera
            if (indiceI==0){ //Acá entra por último, cuando llegó a la primera fila

                for (int indiceJ = 0; indiceJ < medidaMatriz; indiceJ++) { //Recorre desde la primera columna hasta la última
                    int contador = diagonalIzqDer(arreglo2d, medidaMatriz, indiceI, indiceJ, "J"); //se mantiene en la primera fila y recorre las columnas (por eso el índice J)
                    secuencias = secuencias + contador; //al contador se le asignan la cantidad de secuencias que encuentre el método
                }

            }else{ //Acá entra siempre, excepto en la primera fila
                int contador = diagonalIzqDer(arreglo2d, medidaMatriz, indiceI, 0, "I"); //se mantiene en la primera columna (j=0) y recorre las filas (por eso el índice I)
                secuencias = secuencias + contador;
            }
            if (secuencias > 1){
                return secuencias;
            }
        }
        return secuencias;
    }

    //Repito métodos oblicuos pero de derecha a izquierda

    static int diagonalDerIzq(char[][] arreglo2d, int medidaMatriz, int i, int j, String limiteFilaColumna){
        int secuencias =0;
        int restantes=0;
        while (i<=medidaMatriz-1 && j>=0){ //Al ser de derecha a izquiera J recorre desde la última columna hasta la primera
            if (limiteFilaColumna == "J"){
                restantes=j;
            }else{
                restantes=medidaMatriz-i-1;
            }
            if (restantes>=4) {
                if (arreglo2d[i][j] == arreglo2d[i+1][j-1] &&
                        arreglo2d[i][j] == arreglo2d[i+2][j-2] &&
                        arreglo2d[i][j] == arreglo2d[i+3][j-3]
                ) {
                    secuencias++;
                }
            }
            i++;
            j--;
        }
        return secuencias;
    }

    static int secuenciasDiagonalDeIz(char[][] arreglo2d, int medidaMatriz){
        int i=0;
        int j=0;
        int secuencias=0;
        for (int indiceJ = 0; indiceJ <= medidaMatriz-1; indiceJ++) { //recorre desde la primer columna hasta la última
            if (indiceJ==medidaMatriz-1){ //acá entra a lo último
                for (int indiceI = 0; indiceI <= medidaMatriz-1 ; indiceI++) { // busca desde [n,n]
                    int contador = diagonalDerIzq(arreglo2d, medidaMatriz, indiceI, indiceJ, "I");
                    secuencias = secuencias + contador;
                }
            }else{ //Acá entra siempre hasta que llega a la última columna
                int contador = diagonalDerIzq(arreglo2d, medidaMatriz, i, indiceJ, "J");
                secuencias = secuencias + contador;
            }

            if (secuencias > 1){
                return secuencias;
            }

        }
        return secuencias;
    }

    public static boolean isMutant(String[] adn){
        int medidaMatriz = adn.length; //el valor del tamaño de la matríz es la longitud del string que ingresa
        char[][] arreglo2d = new char[medidaMatriz][medidaMatriz]; //genera el arreglo de 2 dimensiones con el tamaño de la matriz
        if (!controlMatriz(arreglo2d,adn)){ //Si el control devuelve false el ! lo pasa a true para ejecutar el if y devolver false
            return false;
        }

        /*Con que en alguno de los siguiente métodos encuentre una secuencia es suficiente
        y devuelve true*/

        if (horizontal(arreglo2d, medidaMatriz) > 1) {
            return true;
        }

        if (vertical(arreglo2d, medidaMatriz) > 1) {
            return true;
        }

        if (secuenciasDiagonalIzDe(arreglo2d, medidaMatriz) > 1) {
            return true;
        }

        if (secuenciasDiagonalDeIz(arreglo2d, medidaMatriz) > 1) {
            return true;
        }

        //sino devuelve false --> no hay ninguna secuencia (no hay nigún mutante)
        return false;
    }

    @PostMapping("/")
    public ResponseEntity<String> verificarMutante(@RequestBody ADNRequest request) {
        boolean esMutante = isMutant(request.getAdn());
        if (esMutante) {
            return ResponseEntity.ok("Es un mutante");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es un mutante");
        }
    }

}
