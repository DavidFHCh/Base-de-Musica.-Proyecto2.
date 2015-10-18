package MyP2;

import org.sqlite.*;
import java.sql.*;
import java.util.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;


/**
 *
 * @author davif
 * Clase Controladora
 */
public class VistaController implements Initializable {
    

    @FXML private TextField artista;
    @FXML private TextField colaboracion;
    @FXML private TextField durMayor;
    @FXML private TextField anioMayor;
    @FXML private TextField disquera;
    @FXML private TextField genero;
    @FXML private TextField duracion;
    @FXML private TextField durMenor;
    @FXML private TextField anio;
    @FXML private TextField cancion;
    @FXML private TextField anioMenor;
    @FXML private ProgressBar barraProgreso;
    @FXML private TableView table;


    /**
    * Metodo que inicializa objetos desde que abre interfaz.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }    
    
}
