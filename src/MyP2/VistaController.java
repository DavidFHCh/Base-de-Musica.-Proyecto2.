package MyP2;

import org.sqlite.*;
import java.sql.*;
import java.util.*;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.collections.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ProgressBar;


/**
 *
 * @author davif
 * Clase Controladora
 */
public class VistaController implements Initializable {

	private class PalabrasClave{

    public final Pattern regex;// Un objeto de tipo pattern.
    public final int ficha;

    /**
    * Constructor para tipos de Ficha.
    * @param regex - la expresion regular que regira lo que sea este tipo de ficha.
    * @param ficha - un identificador.
    */
    public PalabrasClave(Pattern regex, int ficha){
      this.regex = regex;
      this.ficha = ficha;
    }
  }
    

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
    @FXML private TextArea results;

    @FXML private void handleBotonBuscar(ActionEvent event){
    	LinkedList<PalabrasClave> palabras = new LinkedList<PalabrasClave>(); 
    	agregaPalabrasClave(palabras);
    	String cancionText = cancion.getCharacters().toString();
    	String artistaText = artista.getCharacters().toString();
    	String colaboracionText = colaboracion.getCharacters().toString();
    	String durMayorText = durMayor.getCharacters().toString();
    	String anioMayorText = anioMayor.getCharacters().toString();
    	String disqueraText = disquera.getCharacters().toString();
    	String generoText = genero.getCharacters().toString();
    	String duracionText = duracion.getCharacters().toString();
    	String durMenorText = durMenor.getCharacters().toString();
    	String anioText = anio.getCharacters().toString();
    	String anioMenorText = anioMenor.getCharacters().toString();
    	if(matcheador(cancionText,palabras) || matcheador(artistaText,palabras) ||
    		matcheador(colaboracionText,palabras) || matcheador(durMayorText,palabras) || matcheador(anioMayorText,palabras) || 
    		matcheador(disqueraText,palabras) || matcheador(generoText,palabras) || matcheador(duracionText,palabras) || 
    		matcheador(durMenorText,palabras) || matcheador(anioText,palabras) || matcheador(anioMenorText,palabras)){
    		printTextField("No puedo realizar la accion solicitada.");
    	}else{
    		if(cancionText.equals("TODO")){
    			Canciones c = new Canciones();
    			ResultSet rs = c.realizaBusqueda("selectTodoID",0,"","");
    			LinkedList<CancionesSalida> l = Manejador.obtenListaFinalCanciones(rs);
    			String s = "";
    			for(CancionesSalida cs : l){
    				s += cs.toString();
    			}
    			printTextField(s);
    		}
    	}

    }


    /**
    * Metodo que inicializa objetos desde que abre interfaz.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    } 

    private void agrega(String regex, int ficha,LinkedList<PalabrasClave> palabras) {
    PalabrasClave pc = new PalabrasClave(Pattern.compile("^("+regex+")"), ficha);
    palabras.add(pc);
    }

    private void agregaPalabrasClave(LinkedList<PalabrasClave> palabras){
      agrega("DROP|SELECT|UPDATE|DELETE|drop|select|update|delete", 1,palabras);
    }  

    private void printTextField(String text) {
        results.setText(text);
    } 

    private boolean matcheador(String s, LinkedList<PalabrasClave> palabras){
    	String s2 = new String(s);
    	for(PalabrasClave pc : palabras){
          Matcher m = pc.regex.matcher(s2);
          if(m.find()){
            return true;
          }
        }
        return false;
    }  
    
}
