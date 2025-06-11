package logica;

import java.util.regex.Pattern;

public class Validaciones {

    public static boolean validarNumeroTelefono(String telefono) {
        if (telefono == null) {
            return false;
        }

        String regexTelefono = "^\\d{10}$";
        return Pattern.matches(regexTelefono, telefono);
    }

    public static boolean validarSoloNumeros(String numero) {
        if (numero == null || numero.isEmpty()) {
            return false;
        }

        String regexNumeros = "^\\d+$";
        return Pattern.matches(regexNumeros, numero);
    }


    public static boolean validarSoloAlfabeticos(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }

        String regexAlfabeticos = "^[\\p{L}\\s]+$";
        return Pattern.matches(regexAlfabeticos, texto);
    }

    public static boolean validarSoloAlfabeticos(String texto, int longitudMinima, int longitudMaxima) {
        if (texto == null || texto.isEmpty() || longitudMinima < 0 || longitudMaxima < longitudMinima) {
            return false;
        }
        String regexAlfabeticos = "^[\\p{L}\\s]{" + longitudMinima + "," + longitudMaxima + "}$";
        return Pattern.matches(regexAlfabeticos, texto);
    }
}