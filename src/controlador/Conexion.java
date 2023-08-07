
package controlador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexion {
    
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public Conexion() {
        this.setup();
    }
        
    private static void setup() {
        if (em == null) {
            Conexion.emf = Persistence.createEntityManagerFactory("SistemaHospitalarioPU");
            Conexion.em = Conexion.emf.createEntityManager();
        }
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }

    public static void setEmf(EntityManagerFactory emf) {
        Conexion.emf = emf;
    }

    public static EntityManager getEm() {
        return em;
    }

    public static void setEm(EntityManager em) {
        Conexion.em = em;
    }

    public static void main(String[] args) {
        new Conexion();
    }
}