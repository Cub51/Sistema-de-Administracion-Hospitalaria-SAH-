package controlador.utilidades;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class UtilidadesControlador {

    /**
     * Método que determina los años de edad a partir de la fecha de nacimiento.
     * @param fechaNacimientoString Fecha de nacimiento (de la forma: dd/MM/yyyy), es de tipo String.
     * @return Retorna la edad en años, es de tipo int.
     */
    public static int determinarEdad(String fechaNacimientoString) {
        DateTimeFormatter fechaFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoString, fechaFormato);
        Period edad = Period.between(fechaNacimiento, LocalDate.now());
        return edad.getYears();
    }
}
