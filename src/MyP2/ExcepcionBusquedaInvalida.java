package MyP2;

/**
 *
 * @author davif
 */
public class ExcepcionBusquedaInvalida extends RuntimeException {

    /**
     * Constructor vacío.
     */
    public ExcepcionBusquedaInvalida() {
        super();
    }

    /**
     * Constructor que recibe un mensaje para el usuario.
     * @param mensaje un mensaje que verá el usuario cuando ocurra la excepción.
     */
    public ExcepcionBusquedaInvalida(String mensaje) {
        super(mensaje);
    }
}