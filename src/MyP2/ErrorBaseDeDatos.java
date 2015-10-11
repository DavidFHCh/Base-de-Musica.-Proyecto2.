package MyP2;

/**
 *
 * @author davif
 */
public class ErrorBaseDeDatos extends RuntimeException {

    /**
     * Constructor vacío.
     */
    public ErrorBaseDeDatos() {
        super();
    }

    /**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra la excepción.
     */
    public ErrorBaseDeDatos(String mensaje) {
        super(mensaje);
    }
}