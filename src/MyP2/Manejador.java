package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;
import javafx.collections.*;
import java.util.List;
import java.util.ArrayList;

/**
* Clase Manejadora de la base de datos.
*/
public class Manejador{


	private static Connection conexion;


	/**
	* Establece una conexion con la base de datos.
	* @param test ingresa false siempre.
	* @return Connection
	* @throws ErrorBaseDeDatos cuando pasa algo con el archivo de la base.
	*/
	public synchronized static Connection abrirConexion(boolean test){
		try{
			if(conexion == null || conexion.isClosed()){
				Class.forName("org.sqlite.JDBC");
				if(test)
                	conexion = DriverManager.getConnection("jdbc:sqlite:./lib/Musica_Prueba.db");
            	else
            		conexion = DriverManager.getConnection("jdbc:sqlite:./lib/Musica.db");	
			}
		}catch(Exception e){
			throw new ErrorBaseDeDatos("No se pudo establecer una conexion.");
		}
		return conexion;
	}

	/**
	* Cierra conexion con la base de datos.
	* @throws ErrorBaseDeDatos cuando pasa algo con el archivo de la base.
	*/	
	public synchronized static void cerrarConexion(){
		try{
			if(conexion == null || conexion.isClosed())
				return;
			conexion.close();
		}catch(Exception e){
			throw new ErrorBaseDeDatos("No se pudo cerrar la conexion con la Base de Datos.");
		}
	}

	@SuppressWarnings("unchecked")
 	public static ObservableList<CancionesSalida> obtenListaFinalCanciones(ResultSet rs){
 		ObservableList<CancionesSalida> ol = null;
 		List l = new ArrayList();
 		try{
 			while(rs.next()){
 				l.add(new CancionesSalida(rs.getString("cancion"),rs.getString("año"),rs.getString("duracion")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
		ol = FXCollections.observableList(l);
 		return ol;
 	}

	@SuppressWarnings("unchecked")
 	public static ObservableList<ArtistasSalida> obtenListaFinalArtistas(ResultSet rs){
 		ObservableList<ArtistasSalida> ol = null;
 		List l = new ArrayList();
 		try{
 			while(rs.next()){
 				l.add(new ArtistasSalida(rs.getString("artista")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
		ol = FXCollections.observableList(l);
 		return ol;
 	}

 	@SuppressWarnings("unchecked")
 	public static ObservableList<DisquerasSalida> obtenListaFinalDisqueras(ResultSet rs){
 		ObservableList<DisquerasSalida> ol = null;
 		List l = new ArrayList();
 		try{
 			while(rs.next()){
 				l.add(new DisquerasSalida(rs.getString("Recod_Label")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
		ol = FXCollections.observableList(l);
 		return ol;
 	}

 	@SuppressWarnings("unchecked")
 	public static ObservableList<GenerosSalida> obtenListaFinalGeneros(ResultSet rs){
 		ObservableList<GenerosSalida> ol = null;
 		List l = new ArrayList();
 		try{
 			while(rs.next()){
 				l.add(new GenerosSalida(rs.getString("generos")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
		ol = FXCollections.observableList(l);
 		return ol;
 	}

 	@SuppressWarnings("unchecked")
 	public static ObservableList<CollabsCansSalida> obtenListaFinalColabsCans(ResultSet rs){
 		ObservableList<CollabsCansSalida> ol = null;
 		List l = new ArrayList();
 		try{
 			while(rs.next()){
 				l.add(new CollabsCansSalida(rs.getString("cancion"),rs.getString("año"),rs.getString("duracion"),rs.getString("artista")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
		ol = FXCollections.observableList(l);
 		return ol;
 	} 

 	@SuppressWarnings("unchecked")
 	public static ObservableList<DisqsCansSalida> obtenListaFinalDisqsCans(ResultSet rs){
 		ObservableList<DisqsCansSalida> ol = null;
 		List l = new ArrayList();
 		try{
 			while(rs.next()){
 				l.add(new DisqsCansSalida(rs.getString("cancion"),rs.getString("año"),rs.getString("duracion"),rs.getString("Recod_Label")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
		ol = FXCollections.observableList(l);
 		return ol;
 	} 

 	@SuppressWarnings("unchecked")
 	public static ObservableList<GensCansSalida> obtenListaFinalGensCans(ResultSet rs){
 		ObservableList<GensCansSalida> ol = null;
 		List l = new ArrayList();
 		try{
 			while(rs.next()){
 				l.add(new GensCansSalida(rs.getString("cancion"),rs.getString("año"),rs.getString("duracion"),rs.getString("generos")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
		ol = FXCollections.observableList(l);
 		return ol;
 	} 
}