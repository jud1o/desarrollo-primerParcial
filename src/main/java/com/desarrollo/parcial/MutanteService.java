package com.desarrollo.parcial;

import com.desarrollo.parcial.repository.MutanteRepository;
import com.desarrollo.parcial.model.Mutante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MutanteService {

    private final MutanteRepository mutanteRepository;

    @Autowired
    public MutanteService(MutanteRepository mutanteRepository) {
        this.mutanteRepository = mutanteRepository;
    }

    static boolean controlMatriz(char[][] arreglo2d, String[] adn) { // Es un arreglo bidimensional
        for (int i = 0; i < adn.length; i++) {
            char[] adnIngresado = adn[i].toCharArray(); // Convierte la fila a un arreglo de caracteres
            if (adnIngresado.length != adn.length || adn.length < 4) {
                return false; // Comprueba que el valor de la longitud de la fila sea igual a la cantidad de filas
            }
            if (!letras(adnIngresado)) {
                return false;
            }
            arreglo2d[i] = adnIngresado;
        }
        return true;
    }

    static boolean letras(char[] adnIngresado) {
        for (int i = 0; i < adnIngresado.length; i++) {
            if (adnIngresado[i] != 'A' && adnIngresado[i] != 'T' && adnIngresado[i] != 'C' && adnIngresado[i] != 'G') {
                return false; // Comprueba que las letras que se ingresen sean las esperadas
            }
        }
        return true;
    }

    static int horizontal(char[][] arreglo2d, int medidaMatriz) {
        int secuencias = 0;
        for (int i = 0; i < medidaMatriz; i++) {
            for (int j = 0; j < medidaMatriz; j++) {
                if (medidaMatriz - j >= 4) {
                    if (arreglo2d[i][j] == arreglo2d[i][j + 1] &&
                            arreglo2d[i][j] == arreglo2d[i][j + 2] &&
                            arreglo2d[i][j] == arreglo2d[i][j + 3]) {
                        secuencias++;
                        j = j + 3; // Se saltea las siguientes tres letras
                        if (secuencias > 1) { // Con que encuentre una secuencia ya es mutante
                            return secuencias;
                        }
                    }
                }
            }
        }
        return secuencias;
    }

    static int vertical(char[][] arreglo2d, int medidaMatriz) {
        int secuencias = 0;
        for (int i = 0; i < medidaMatriz; i++) {
            for (int j = 0; j < medidaMatriz; j++) {
                if (medidaMatriz - i >= 4) {
                    if (arreglo2d[i][j] == arreglo2d[i + 1][j] &&
                            arreglo2d[i][j] == arreglo2d[i + 2][j] &&
                            arreglo2d[i][j] == arreglo2d[i + 3][j]) {
                        secuencias++;
                        j = j + 3;
                        if (secuencias > 1) {
                            return secuencias;
                        }
                    }
                }
            }
        }
        return secuencias;
    }

    static int diagonalIzqDer(char[][] arreglo2d, int medidaMatriz, int i, int j, String limiteFilaColumna) {
        int secuencias = 0;
        int restantes = 0;
        while (i <= medidaMatriz - 1 && j <= medidaMatriz - 1) {
            if (limiteFilaColumna.equals("J")) {
                restantes = medidaMatriz - j;
            } else {
                restantes = medidaMatriz - i;
            }
            if (restantes >= 4) {
                if (arreglo2d[i][j] == arreglo2d[i + 1][j + 1] &&
                        arreglo2d[i][j] == arreglo2d[i + 2][j + 2] &&
                        arreglo2d[i][j] == arreglo2d[i + 3][j + 3]) {
                    secuencias++;
                }
            }
            i++;
            j++;
        }
        return secuencias;
    }

    static int secuenciasDiagonalIzDe(char[][] arreglo2d, int medidaMatriz) {
        int secuencias = 0;
        for (int indiceI = medidaMatriz - 1; indiceI >= 0; indiceI--) {
            if (indiceI == 0) {
                for (int indiceJ = 0; indiceJ < medidaMatriz; indiceJ++) {
                    int contador = diagonalIzqDer(arreglo2d, medidaMatriz, indiceI, indiceJ, "J");
                    secuencias += contador;
                }
            } else {
                int contador = diagonalIzqDer(arreglo2d, medidaMatriz, indiceI, 0, "I");
                secuencias += contador;
            }
            if (secuencias > 1) {
                return secuencias;
            }
        }
        return secuencias;
    }

    static int diagonalDerIzq(char[][] arreglo2d, int medidaMatriz, int i, int j, String limiteFilaColumna) {
        int secuencias = 0;
        int restantes = 0;
        while (i <= medidaMatriz - 1 && j >= 0) {
            if (limiteFilaColumna.equals("J")) {
                restantes = j;
            } else {
                restantes = medidaMatriz - i - 1;
            }
            if (restantes >= 4) {
                if (arreglo2d[i][j] == arreglo2d[i + 1][j - 1] &&
                        arreglo2d[i][j] == arreglo2d[i + 2][j - 2] &&
                        arreglo2d[i][j] == arreglo2d[i + 3][j - 3]) {
                    secuencias++;
                }
            }
            i++;
            j--;
        }
        return secuencias;
    }

    static int secuenciasDiagonalDeIz(char[][] arreglo2d, int medidaMatriz) {
        int i = 0;
        int j = 0;
        int secuencias = 0;
        for (int indiceJ = 0; indiceJ <= medidaMatriz - 1; indiceJ++) {
            if (indiceJ == medidaMatriz - 1) {
                for (int indiceI = 0; indiceI <= medidaMatriz - 1; indiceI++) {
                    int contador = diagonalDerIzq(arreglo2d, medidaMatriz, indiceI, indiceJ, "I");
                    secuencias += contador;
                }
            } else {
                int contador = diagonalDerIzq(arreglo2d, medidaMatriz, i, indiceJ, "J");
                secuencias += contador;
            }
            if (secuencias > 1) {
                return secuencias;
            }
        }
        return secuencias;
    }

    public static boolean isMutant(String[] adn) {
        int medidaMatriz = adn.length; // El valor del tamaño de la matríz es la longitud del string que ingresa
        char[][] arreglo2d = new char[medidaMatriz][medidaMatriz]; // Genera el arreglo de 2 dimensiones
        if (!controlMatriz(arreglo2d, adn)) { // Si el control devuelve false
            return false;
        }
        int totalHorizontal=0;
        totalHorizontal = horizontal(arreglo2d, medidaMatriz);
        if (totalHorizontal > 1) {
            return true;
        }

        int totalVertical=0;
        totalVertical = vertical(arreglo2d, medidaMatriz);
        if ((totalHorizontal+totalVertical)>1){
            return true;
        }

        int totalDiagonalIzqDer=0;
        totalDiagonalIzqDer = secuenciasDiagonalIzDe(arreglo2d, medidaMatriz);
        if ((totalHorizontal+totalVertical+totalDiagonalIzqDer)>1){
            return true;
        }

        int totalSecuenceDiagDerIzq=0;
        totalSecuenceDiagDerIzq = secuenciasDiagonalDeIz(arreglo2d, medidaMatriz);
        if ((totalHorizontal+totalVertical+totalDiagonalIzqDer+totalSecuenceDiagDerIzq)>1){
            return true;
        }

        return false;
    }

    public void guardarADN(String[] adn) {
        String adnCadena = String.join("", adn); //Hace que el array sea una sola cadena

        //Se fija si el ADN ya existe
        if (mutanteRepository.findByAdn(adnCadena).isEmpty()) {
            boolean esMutante = isMutant(adn);
            Mutante mutante = new Mutante(adnCadena, esMutante);
            mutanteRepository.save(mutante); //Lo guarda solo si no existe
        }
    }

    public List<Mutante> obtenerTodosLosMutantes() {
        return mutanteRepository.findAll();
    }

    public Optional<Mutante> obtenerMutantePorId(Long id) {
        return mutanteRepository.findById(id);
    }

    public StatsResponse obtenerEstadisticas() {
        List<Mutante> mutantes = mutanteRepository.findAll();
        long countMutantDna = mutantes.stream().filter(Mutante::isEsMutante).count();
        long countHumanDna = mutantes.size() - countMutantDna;

        double ratio = (countHumanDna == 0) ? 0 : (double) countMutantDna / countHumanDna;

        return new StatsResponse(countMutantDna, countHumanDna, ratio);
    }
}
