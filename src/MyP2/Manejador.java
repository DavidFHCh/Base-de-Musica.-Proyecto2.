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


	public static Connection conexion;


	/**
	* Establece una conexion con la base de datos.
	* @param test ingresa false siempre.
	* @return Connection
	* @throws ErrorBaseDeDatos cuando pasa algo con el archivo de la base.
	*/
	public synchronized static Connection abrirConexion(){
		try{
			if(conexion == null || conexion.isClosed()){
				Class.forName("org.sqlite.JDBC");
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

	
 	public static LinkedList<CancionesSalida> obtenListaFinalCanciones(ResultSet rs){
 		LinkedList<CancionesSalida> l = new LinkedList<CancionesSalida>();
 		try{
 			while(rs.next()){
 				l.add(new CancionesSalida(rs.getString("cancion"),rs.getString("año"),rs.getString("duracion")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
 		return l;
 	}

	
 	public static LinkedList<ArtistasSalida> obtenListaFinalArtistas(ResultSet rs){
 		LinkedList<ArtistasSalida> l = new LinkedList<ArtistasSalida>();
 		try{
 			while(rs.next()){
 				l.add(new ArtistasSalida(rs.getString("artista")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
 		return l;
 	}

 	
 	public static LinkedList<DisquerasSalida> obtenListaFinalDisqueras(ResultSet rs){
 		LinkedList<DisquerasSalida> l = new LinkedList<DisquerasSalida>();
 		try{
 			while(rs.next()){
 				l.add(new DisquerasSalida(rs.getString("Recod_Label")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
 		return l;
 	}

 	
 	public static LinkedList<GenerosSalida> obtenListaFinalGeneros(ResultSet rs){
 		LinkedList<GenerosSalida> l = new LinkedList<GenerosSalida>();
 		try{
 			while(rs.next()){
 				l.add(new GenerosSalida(rs.getString("generos")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
 		return l;
 	}

 	
 	public static LinkedList<CollabsCansSalida> obtenListaFinalColabsCans(ResultSet rs){
 		LinkedList<CollabsCansSalida> l = new LinkedList<CollabsCansSalida>();
 		try{
 			while(rs.next()){
 				l.add(new CollabsCansSalida(rs.getString("cancion"),rs.getString("año"),rs.getString("duracion"),rs.getString("artista")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
 		return l;
 	} 

 	
 	public static LinkedList<DisqsCansSalida> obtenListaFinalDisqsCans(ResultSet rs){
 		LinkedList<DisqsCansSalida> l = new LinkedList<DisqsCansSalida>();
 		try{
 			while(rs.next()){
 				l.add(new DisqsCansSalida(rs.getString("cancion"),rs.getString("año"),rs.getString("duracion"),rs.getString("Recod_Label")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
 		return l;
 	} 

 	
 	public static LinkedList<GensCansSalida> obtenListaFinalGensCans(ResultSet rs){
 		LinkedList<GensCansSalida> l = new LinkedList<GensCansSalida>();
 		try{
 			while(rs.next()){
 				l.add(new GensCansSalida(rs.getString("cancion"),rs.getString("año"),rs.getString("duracion"),rs.getString("generos")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
 		return l;
 	} 
}