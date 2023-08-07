package controlador.DAO;

import controlador.PersonaJpaController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;
import modelo.Persona;

public class PersonaDAO {

    private PersonaJpaController PersonaJpa = new PersonaJpaController();
    private RolDAO rolDAO = new RolDAO();
    private Persona persona;

    public Persona getPersona() {
        if (persona == null) {
            persona = new Persona();
        }
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * Método que busca la persona solicitada.
     *
     * @param persona Objeto que va a ser buscado en el sistema, es de tipo
     * Persona.
     * @return Retorna Persona si se logró encontrar la persona, caso contrario,
     * devuelve vacio.
     */
    public Persona buscarPersona(Persona persona) {
        Persona aux = new Persona();
        try {
            aux = PersonaJpa.findPersona(persona.getId_persona());
            return aux;
        } catch (Exception e) {
            return aux;
        }
    }

    /**
     * Método que busca la persona solicitada por su identificador.
     *
     * @param id identificador que va a ser buscado en el sistema, es de tipo
     * Long.
     * @return Retorna Persona si se logró encontrar la persona, caso contrario,
     * devuelve vacio.
     */
    public Persona buscarPersonaPorId(Long id) {
        Persona aux = new Persona();
        try {
            aux = PersonaJpa.findPersona(id);
            return aux;
        } catch (Exception e) {
            return aux;
        }
    }

    /**
     * Método que busca la persona solicitada por su cédula.
     *
     * @param cedula cédula que va a ser buscada en el sistema, es de tipo
     * String.
     * @return Retorna Persona si se logró encontrar la persona, caso contrario,
     * devuelve vacio.
     */
    public Persona buscarPorCedula(String cedula) {
        for (Object p : listarPersonas()) {
            if (buscarPersona((Persona) p).getCedula().equals(cedula)) {
                System.out.println("SI ENCONTRO PERSONA");
                return buscarPersona((Persona) p);
            }
        }
        return null;
    }

    /**
     * Método que permite agregar una persona.
     *
     * @param persona Objeto que va a ser agregado al sistema, es de tipo
     * Persona.
     * @return Retorna true si se logró agregar la persona, caso contrario,
     * devuelve false.
     */
    public boolean agregarPersona(Persona persona) {
        try {
            PersonaJpa.create(persona);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    /**
     * Método que devuelve una lista con todas las personas ingresadas en el
     * sistema.
     *
     * @return Retorna una lista de tipo List.
     */
    public List listarPersonas() {
        List<Persona> listaPersona = new ArrayList<Persona>();
        try {
            listaPersona = PersonaJpa.findPersonaEntities();
            return listaPersona;
        } catch (Exception e) {
            return listaPersona;
        }
    }

    /**
     * Método que devuelve una lista con todos los personas que cumplan con el
     * estado indicado.
     *
     * @param estado Estado por el cual se van a filtar los personas, es de tipo
     * String.
     * @return Retorna una lista de tipo List.
     */
    public List<Persona> getPersonasPorEstado(String estado) {
        return PersonaJpa.getPersonasPorEstado(estado);
    }

    /**
     * Método que permite editar una persona ingresada en el sistema.
     *
     * @param persona Objeto que contiene los datos actualizados de la persona,
     * es de tipo Persona.
     * @return Retorna true si se logró actualizar la persona, caso contrario,
     * devuelve false.
     */
    public boolean editarPersona(Persona persona) {
        try {
            PersonaJpa.edit(persona);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /**
     * Método que permite verificar si un correo es válido.
     *
     * @param correo Correo que se validará si está escrito correctamente, es de
     * tipo String.
     * @return Retorna true si se logró validar el correo, caso contrario,
     * devuelve false.
     */
    public boolean verificarCorreo(String correo) {
        return EMAIL_PATTERN.matcher(correo).matches();
    }

    /**
     * Método que permite verificar si los campos telefónicos tienen una
     * longitud correcta.
     *
     * @param telefono Telefóno que se validará si esta escrito correctamente,
     * es de tipo String.
     * @param telefonoAuxiliar Telefóno que se validará si esta escrito
     * correctamente, es de tipo String.
     * @return Retorna true si se logró validar los teléfonos, caso contrario,
     * devuelve false.
     */
    public boolean verificarLongitudDiez(String telefono, String telefonoAuxiliar) {
        if (telefono.length() == 10 && telefonoAuxiliar.length() == 10) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que permite verificar si una cédula es válida.
     *
     * @param cedula Cédula que se validará si esta escrita correctamente, es de
     * tipo String.
     * @return Retorna true si se logró validar la cédula, caso contrario,
     * devuelve false.
     */
    public boolean verificarCedula(String cedula) {
        Character cedulaC[] = new Character[10];
        Character comp[] = {'2', '1', '2', '1', '2', '1', '2', '1', '2'};
        int ver = 0;
        int suma = 0;
        boolean validar = false;

        for (int i = 0; i < cedulaC.length; i++) {
            cedulaC[i] = cedula.charAt(i);
        }
        for (int i = 0; i < cedulaC.length - 1; i++) {
            ver = Integer.parseInt(String.valueOf(cedulaC[i])) * Integer.parseInt(String.valueOf(comp[i]));
            if (ver > 9) {
                ver = ver - 9;
            }
            suma += ver;
        }
        if ((suma - (suma % 10) + 10 - suma) == Integer.parseInt(String.valueOf(cedulaC[9]))) {
            validar = true;
        }
        return validar;
    }

    /**
     * Método que permite verificar si una fecha es mayor que la fecha actual.
     *
     * @param fcita Fecha que se validará si fue seleccionada correctamente, es
     * de tipo String.
     * @return Retorna true si se logró validar la fecha, caso contrario,
     * devuelve false.
     */
    public boolean MetodoVaidarFechaCita(String fcita) {
        boolean verificacion = false;
        try {
            Calendar fecha = new GregorianCalendar();
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            String actual = dia + "/" + (mes + 1) + "/" + año;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaactual = formato.parse(actual);
            Date fechaCita = formato.parse(fcita);
            if (fechaCita.compareTo(fechaactual) >= 0) {
                //System.out.println(" fecha correcta");
                verificacion = true;
            } else {
                //System.out.println("fecha incorrecta");
                verificacion = false;
            }
        } catch (ParseException ex) {
            System.out.println("Error: " + ex);
        }
        return verificacion;
    }

    /**
     * Método que permite verificar si una fecha es menor que la fecha actual.
     *
     * @param fnac Fecha que se validará si fue seleccionada correctamente, es
     * de tipo String.
     * @return Retorna true si se logró validar la fecha, caso contrario,
     * devuelve false.
     */
    public boolean MetodoVaidarFechaNacimiento(String fnac) {
        boolean verificacion = false;
        try {
            Calendar fecha = new GregorianCalendar();
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            String actual = dia + "/" + (mes + 1) + "/" + año;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaactual = formato.parse(actual);
            Date fechaNaci = formato.parse(fnac);
            if (fechaNaci.compareTo(fechaactual) <= 0) {
                //System.out.println(" fecha correcta");
                verificacion = true;
            } else {
                //System.out.println("fecha incorrecta");
                verificacion = false;
            }
        } catch (ParseException ex) {
            System.out.println("Error: " + ex);
        }
        return verificacion;
    }

    /**
     * Método que devuelve una lista con todas las personas encontradas con el
     * estado "activo" y con un rol "PERSONA".
     *
     * @return Retorna una lista de tipo List.
     */
    public List buscarPersonasPresentar() {
        List<Persona> personas = new ArrayList();
        for (Object p : getPersonasPorEstado("activo")) {
            if (buscarPersona((Persona) p).getRol().toString().equals(rolDAO.buscarRolId(1).toString())) {
                personas.add((Persona) p);
            }
        }
        return personas;
    }
}
