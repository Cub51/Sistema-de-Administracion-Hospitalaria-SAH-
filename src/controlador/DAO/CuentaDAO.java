package controlador.DAO;

import controlador.CuentaJpaController;
import java.util.ArrayList;
import java.util.List;
import modelo.Cuenta;

public class CuentaDAO {
    private CuentaJpaController cuentaJpa = new CuentaJpaController();
    private Cuenta cuenta;

    public Cuenta getCuenta() {
        if(this.cuenta == null){
            this.cuenta = new Cuenta();
        }
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    /**
     * Método que permite agregar una cuenta.
     *
     * @param cuenta Objeto que va a ser agregado al sistema, es de tipo Cuenta.
     * @return Retorna true si se logró agregar la cuenta, caso contrario, devuelve false.
     */
    public boolean agregarCuenta(Cuenta cuenta){
        try {
            cuentaJpa.create(cuenta);
            return true;
        } catch (Exception e) {
            System.out.println("Error: "+e);
            return false;
        }
    }
    
    /**
     * Método que busca la cuenta solicitada.
     *
     * @param cuenta Objeto que va a ser buscado en el sistema, es de tipo Cuenta.
     * @return Retorna Cuenta si se logró encontrar la cuenta, caso contrario, devuelve vacio.
     */
    public Cuenta buscarCuenta(Cuenta cuenta){
        Cuenta aux = new Cuenta();
        try {
            aux = cuentaJpa.findCuenta(cuenta.getId_cuenta());
            return aux;
        } catch (Exception e) {
            return aux;
        }
    }
    
    /**
     * Método que devuelve una lista con todas las cuentas ingresadas en el sistema.
     *
     * @return Retorna una lista de tipo List.
     */
    public List listarCuentas(){
        List<Cuenta> listaCuenta = new ArrayList<Cuenta>();
        try {
            listaCuenta = cuentaJpa.findCuentaEntities();
            return listaCuenta;
        } catch (Exception e) {
            return listaCuenta;
        }
    }
}
