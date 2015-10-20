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
import javafx.scene.control.Label;


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
    @FXML private TextArea results;
    @FXML private Label buscando;

    @FXML private void handleBotonBuscar(ActionEvent event){
        try{
    	buscando.setText("Buscando...");
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
    		if(anioMenorText.matches("[a-zA-Z]") || anioMayorText.matches("[a-zA-Z]") || anioText.matches("[a-zA-Z]") || duracionText.matches("[a-zA-Z]") || durMayorText.matches("[a-zA-Z]") || durMenorText.matches("[a-zA-Z]"))
    			printTextField("Checa que no metas letras donde deben ir numeros!");
    		else{
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
    					case 5:
    						salida += "Cancion, Año, Duracion, Colaboracion\n";
    						Colaboraciones_Canciones cc4 = new Colaboraciones_Canciones();
    						lrs = cc4.realizaBusquedaEspecial("joinCancionesAColaboracionesIDLike",cancionText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 6:
    						salida += "Cancion, Año, Duracion, Artista\n";
    						Canciones_Artistas cc5 = new Canciones_Artistas();
    						lrs = cc5.realizaBusquedaEspecial("joinCancionesAArtistasIDLike",cancionText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 7:
    						salida += "Cancion, Año, Duracion, Genero\n";
    						Generos_Cancion cc6 = new Generos_Cancion();
    						lrs = cc6.realizaBusquedaEspecial("joinCancionesAGenerosIDLike",cancionText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<GensCansSalida> ls = Manejador.obtenListaFinalGensCans(rs1);
    							for(GensCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 8:
    						salida += "Cancion, Año, Duracion, Disquera\n";
    						Disqueras_Cancion cc7 = new Disqueras_Cancion();
    						lrs = cc7.realizaBusquedaEspecial("joinCancionesADisquerasIDLike",cancionText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<DisqsCansSalida> ls = Manejador.obtenListaFinalDisqsCans(rs1);
    							for(DisqsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 9:
    						salida += "Cancion, Año, Duracion, Artista\n";
    						Canciones_Artistas cc8 = new Canciones_Artistas();
    						lrs = cc8.realizaBusquedaEspecial("joinArtistasACancionesID",artistaText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 10:
    						salida += "Cancion, Año, Duracion, Colaboracion\n";
    						Colaboraciones_Canciones cc9 = new Colaboraciones_Canciones();
    						lrs = cc9.realizaBusquedaEspecial("joinArtistasACancionesID",colaboracionText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 11:
    						salida += "Cancion, Año, Duracion, Disquera\n";
    						Disqueras_Cancion cc10 = new Disqueras_Cancion();
    						lrs = cc10.realizaBusquedaEspecial("joinDisquerasACancionesID",disqueraText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<DisqsCansSalida> ls = Manejador.obtenListaFinalDisqsCans(rs1);
    							for(DisqsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 12:
    						salida += "Cancion, Año, Duracion, Genero\n";
    						Generos_Cancion cc11 = new Generos_Cancion();
    						lrs = cc11.realizaBusquedaEspecial("joinGenerosACancionesID",generoText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<GensCansSalida> ls = Manejador.obtenListaFinalGensCans(rs1);
    							for(GensCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 13:
    						salida += "Cancion, Año, Duracion, Artista\n";
    						Canciones_Artistas cc12 = new Canciones_Artistas();
    						lrs = cc12.realizaBusquedaEspecial("joinTodoDuracion",duracionText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 14:
    						salida += "Cancion, Año, Duracion, Colaboracion\n";
    						Colaboraciones_Canciones cc13 = new Colaboraciones_Canciones();
    						lrs = cc13.realizaBusquedaEspecial("joinTodoDuracion",duracionText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 15:
    						salida += "Cancion, Año, Duracion, Genero\n";
    						Generos_Cancion cc14 = new Generos_Cancion();
    						lrs = cc14.realizaBusquedaEspecial("joinTodoDuracion",duracionText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<GensCansSalida> ls = Manejador.obtenListaFinalGensCans(rs1);
    							for(GensCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 16:
    						salida += "Cancion, Año, Duracion, Disquera\n";
    						Disqueras_Cancion cc15 = new Disqueras_Cancion();
    						lrs = cc15.realizaBusquedaEspecial("joinTodoDuracion",duracionText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<DisqsCansSalida> ls = Manejador.obtenListaFinalDisqsCans(rs1);
    							for(DisqsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 17:
    						salida += "Cancion, Año, Duracion, Artista\n";
    						Canciones_Artistas cc16 = new Canciones_Artistas();
    						if(Double.valueOf(durMenorText) > Double.valueOf(durMayorText)){
    							printTextField("checa que la duracion menor este a la izquierda.");
    						}
    						lrs = cc16.realizaBusquedaEspecial("joinTodoEntreDur",durMenorText,durMayorText);
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 18:
    						salida += "Cancion, Año, Duracion, Colaboracion\n";
    						Colaboraciones_Canciones cc17 = new Colaboraciones_Canciones();
    						if(Double.valueOf(durMenorText) > Double.valueOf(durMayorText)){
    							printTextField("checa que la duracion menor este a la izquierda.");
    						}
    						lrs = cc17.realizaBusquedaEspecial("joinTodoEntreDur",durMenorText,durMayorText);
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 19:
    						salida += "Cancion, Año, Duracion, Genero\n";
    						Generos_Cancion cc18 = new Generos_Cancion();
    						if(Double.valueOf(durMenorText) > Double.valueOf(durMayorText)){
    							printTextField("checa que la duracion menor este a la izquierda.");
    						}
    						lrs = cc18.realizaBusquedaEspecial("joinTodoEntreDur",durMenorText,durMayorText);
    						for(ResultSet rs1: lrs){
    							LinkedList<GensCansSalida> ls = Manejador.obtenListaFinalGensCans(rs1);
    							for(GensCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 20:
    						salida += "Cancion, Año, Duracion, Disquera\n";
    						Disqueras_Cancion cc19 = new Disqueras_Cancion();
    						if(Double.valueOf(durMenorText) > Double.valueOf(durMayorText)){
    							printTextField("checa que la duracion menor este a la izquierda.");
    						}
    						lrs = cc19.realizaBusquedaEspecial("joinTodoEntreDur",durMenorText,durMayorText);
    						for(ResultSet rs1: lrs){
    							LinkedList<DisqsCansSalida> ls = Manejador.obtenListaFinalDisqsCans(rs1);
    							for(DisqsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 21:
    						salida += "Cancion, Año, Duracion, Artista\n";
    						Canciones_Artistas cc20 = new Canciones_Artistas();
    						if(Double.valueOf(durMenorText) > Double.valueOf(durMayorText)){
    							printTextField("checa que la duracion menor este a la izquierda.");
    						}
    						lrs = cc20.realizaBusquedaEspecial("joinCancionesAArtistasIDEntreAnios",anioMenorText,anioMayorText);
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 22:
    						salida += "Cancion, Año, Duracion, Artista\n";
    						Canciones_Artistas cc201 = new Canciones_Artistas();
    						if(Double.valueOf(durMenorText) > Double.valueOf(durMayorText)){
    							printTextField("checa que la duracion menor este a la izquierda.");
    						}
    						lrs = cc201.realizaBusquedaEspecial("joinTodoEntreAnio",anioMenorText,anioMayorText);
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 23:
    						salida += "Cancion, Año, Duracion, Colaboracion\n";
    						Colaboraciones_Canciones cc21 = new Colaboraciones_Canciones();
    						if(Double.valueOf(durMenorText) > Double.valueOf(durMayorText)){
    							printTextField("checa que la duracion menor este a la izquierda.");
    						}
    						lrs = cc21.realizaBusquedaEspecial("joinTodoEntreAnio",anioMenorText,anioMayorText);
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 24:
    						salida += "Cancion, Año, Duracion, Genero\n";
    						Generos_Cancion cc22 = new Generos_Cancion();
    						if(Double.valueOf(durMenorText) > Double.valueOf(durMayorText)){
    							printTextField("checa que la duracion menor este a la izquierda.");
    						}
    						lrs = cc22.realizaBusquedaEspecial("joinTodoEntreAnio",anioMenorText,anioMayorText);
    						for(ResultSet rs1: lrs){
    							LinkedList<GensCansSalida> ls = Manejador.obtenListaFinalGensCans(rs1);
    							for(GensCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 25:
    						salida += "Cancion, Año, Duracion, Disquera\n";
    						Disqueras_Cancion cc23 = new Disqueras_Cancion();
    						if(Double.valueOf(durMenorText) > Double.valueOf(durMayorText)){
    							printTextField("checa que la duracion menor este a la izquierda.");
    						}
    						lrs = cc23.realizaBusquedaEspecial("joinTodoEntreAnio",anioMenorText,anioMayorText);
    						for(ResultSet rs1: lrs){
    							LinkedList<DisqsCansSalida> ls = Manejador.obtenListaFinalDisqsCans(rs1);
    							for(DisqsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 26:
    						salida += "Cancion, Año, Duracion, Artista\n";
    						Canciones_Artistas cc24 = new Canciones_Artistas();
    						lrs = cc24.realizaBusquedaEspecial("joinTodoAnio",anioText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							System.out.println(ls.size());
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 27:
    						salida += "Cancion, Año, Duracion, Colaboracion\n";
    						Colaboraciones_Canciones cc25 = new Colaboraciones_Canciones();
    						lrs = cc25.realizaBusquedaEspecial("joinTodoAnio",anioText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<CollabsCansSalida> ls = Manejador.obtenListaFinalColabsCans(rs1);
    							for(CollabsCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 28:
    						salida += "Cancion, Año, Duracion, Genero\n";
    						Generos_Cancion cc26 = new Generos_Cancion();
    						lrs = cc26.realizaBusquedaEspecial("joinTodoAnio",anioText,"");
    						for(ResultSet rs1: lrs){
    							LinkedList<GensCansSalida> ls = Manejador.obtenListaFinalGensCans(rs1);
    							for(GensCansSalida ccs : ls){
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					case 29:
    						salida += "Cancion, Año, Duracion, Disquera\n";
    						Disqueras_Cancion cc27 = new Disqueras_Cancion();
    						lrs = cc27.realizaBusquedaEspecial("joinTodoAnio",anioText,"");
    						//System.out.println(lrs.size());
    						for(ResultSet rs1: lrs){
    							LinkedList<DisqsCansSalida> ls = Manejador.obtenListaFinalDisqsCans(rs1);
    							//System.out.println(ls.size());
    							for(DisqsCansSalida ccs : ls){
    								//System.out.println(salida);
    								salida += ccs.toString() + "\n";
    							}
    						}
    						salida += saltos();
    						break;
    					default:
    						printTextField("Busqueda no implementada, intenta con otra combinacion de parametros.");

    				}
    			}
    			printTextField(salida);
    			Manejador.cerrarConexion();
    			buscando.setText("");
    		}
    	}
    }catch(Exception e){
        printTextField(e.getMessage());
    }
	}


    /**
    * Metodo que inicializa objetos desde que abre interfaz.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	results.setEditable(false);
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
    	if((!cancion.equals("") && artista.equals("TODO") && colaboracion.equals("TODO") && disquera.equals("TODO")
    		&& genero.equals("TODO") && duracion.equals("0") && anio.equals("0")) && !durMenor.equals("-1") && !durMayor.equals("-1")){
    		l.add(20);
    		l.add(17);
			l.add(18);
			l.add(19);
			return l;
    	}
    	if((!cancion.equals("") && artista.equals("TODO") && colaboracion.equals("TODO") && disquera.equals("TODO")
    		&& genero.equals("TODO") && duracion.equals("0") && anio.equals("0")) && !anioMenor.equals("-1") && !anioMayor.equals("-1")){
			l.add(22);
    		l.add(23);
			l.add(24);
			l.add(25);
			return l;
    	}
    	if((cancion.equals("TODO") && artista.equals("TODO") && colaboracion.equals("TODO") && disquera.equals("TODO")
    		&& genero.equals("TODO") && duracion.equals("0") && anio.equals("0"))){
    		l.add(1);
    		l.add(2);
    		l.add(3);
    		l.add(4);
    		return l;
    	}
    	if((!cancion.equals("") && artista.equals("TODO") && colaboracion.equals("TODO") && disquera.equals("TODO")
    		&& genero.equals("TODO") && duracion.equals("0") && anio.equals("0"))){
    		l.add(5);
    		l.add(6);
    		l.add(7);
    		l.add(8);
    		return l;
    	}
    	if((cancion.equals("TODO") && !artista.equals("") && colaboracion.equals("TODO") && disquera.equals("TODO")
    	&& genero.equals("TODO") && duracion.equals("0") && anio.equals("0"))){
    		l.add(9);
    		return l;
    	}
    	if((cancion.equals("TODO") && artista.equals("TODO") && !colaboracion.equals("") && disquera.equals("TODO")
    	&& genero.equals("TODO") && duracion.equals("0") && anio.equals("0"))){
    		l.add(10);
    		return l;
    	}
		if((cancion.equals("TODO") && artista.equals("TODO") && colaboracion.equals("TODO") && !disquera.equals("")
    	&& genero.equals("TODO") && duracion.equals("0") && anio.equals("0"))){
    		l.add(11);
    		return l;
    	}
    	if((cancion.equals("TODO") && artista.equals("TODO") && colaboracion.equals("TODO") && disquera.equals("TODO")
    	&& !genero.equals("") && duracion.equals("0") && anio.equals("0"))){
    		l.add(12);
    		return l;
    	}
    	if((cancion.equals("TODO") && artista.equals("TODO") && colaboracion.equals("TODO") && disquera.equals("TODO")
    	&& genero.equals("TODO") && !duracion.equals("") && anio.equals("0"))){
    		l.add(13);
    		l.add(14);
			l.add(15);
			l.add(16);
    		return l;
    	}
    	if((cancion.equals("TODO") && artista.equals("TODO") && colaboracion.equals("TODO") && disquera.equals("TODO")
    	&& genero.equals("TODO") && duracion.equals("0") && !anio.equals(""))){
    		l.add(26);
    		l.add(27);
			l.add(28);
			l.add(29);
    		return l;
    	}
    	l.add(-1);
    	return l;
    }
}
