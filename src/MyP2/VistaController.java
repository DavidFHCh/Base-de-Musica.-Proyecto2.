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
    		LinkedList<Integer> casos = getCasos(cancionText,artistaText, colaboracionText, durMayorText, anioMayorText, disqueraText, generoText, duracionText, durMenorText, anioText, anioMenorText);
    		ResultSet rs = null;
    		LinkedList<ResultSet> lrs = new LinkedList<ResultSet>();
    		String salida = "";
    		for(int i : casos){
    			switch(i){
    				case 1:
    					Colaboraciones_Canciones cc = new Colaboraciones_Canciones();
    					salida += "Cancion, Año, Duracion, Colaboracion\n";
    					lrs = cc.realizaBusquedaEspecial("joinTodo","","");
    					for(ResultSet rs1: lrs){
    						LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    						for(CollabsCansSalida ccs : ls){
    							salida += ccs.toString() + "\n";
    						}
    					}
    					salida += saltos();
    					break;
    				case 2:
    					salida += "Cancion, Año, Duracion, Artista\n";
    					Canciones_Artistas cc1 = new Canciones_Artistas();
    					lrs = cc1.realizaBusquedaEspecial("joinTodo","","");
    					for(ResultSet rs1: lrs){
    						LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    						for(CollabsCansSalida ccs : ls){
    							salida += ccs.toString() + "\n";
    						}
    					}
    					salida += saltos();
    					break;
    				case 3:
    					salida += "Cancion, Año, Duracion, Genero\n";
    					Generos_Cancion cc2 = new Generos_Cancion();
    					lrs = cc2.realizaBusquedaEspecial("joinTodo","","");
    					for(ResultSet rs1: lrs){
    						LinkedList<GensCansSalida> ls = Manejador.obtenListaFinalGensCans(rs1);
    						for(GensCansSalida ccs : ls){
    							salida += ccs.toString() + "\n";
    						}
    					}
    					salida += saltos();
    					break;
    				case 4:
    					salida += "Cancion, Año, Duracion, Disquera\n";
    					Disqueras_Cancion cc3 = new Disqueras_Cancion();
    					lrs = cc3.realizaBusquedaEspecial("joinTodo","","");
    					for(ResultSet rs1: lrs){
    						LinkedList<DisqsCansSalida> ls = Manejador.obtenListaFinalDisqsCans(rs1);
    						for(DisqsCansSalida ccs : ls){
    							salida += ccs.toString() + "\n";
    						}
    					}
    					salida += saltos();
    					break;

    			}
    		}
    		printTextField(salida);
    		Manejador.cerrarConexion();
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

    private String saltos(){
    	return "\n\n\n\n\n\n";
    }
    

    private LinkedList<Integer> getCasos(String cancion,String artista, String colaboracion, String durMayor, String anioMayor, String disquera, String genero, String duracion, String durMenor, String anio, String anioMenor){
    	LinkedList<Integer> l = new LinkedList<Integer>(); 
    	if(cancion.equals("") && artista.equals("") && colaboracion.equals("") && durMayor.equals("") && anioMayor.equals("") && disquera.equals("")
    		&& genero.equals("") && duracion.equals("") && durMenor.equals("") && anio.equals("") && anioMenor.equals("")){
    		throw new ExcepcionBusquedaInvalida("No pasaste ningun parametro de busqueda.");
    	}
    	if(cancion.equals("TODO") && artista.equals("TODO") && colaboracion.equals("TODO") && disquera.equals("TODO")
    		&& genero.equals("TODO") && duracion.equals("TODO") && anio.equals("TODO")){
    		l.add(1);
    		l.add(2);
    		l.add(3);
    		l.add(4);
    		return l;
    	}
    	return null;
    }
}
