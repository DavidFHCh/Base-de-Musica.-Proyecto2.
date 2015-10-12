package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

public class Colaboraciones_Canciones{

	private static String tabla = "Colaboraciones_Canciones";
	private static String idColaboracion = "id_artista_grupo";
	private static String idCancion = "id_cancion";
	private int idCollab;
	private int idSong;

	public Colaboraciones_Canciones(int idCollab, int idSong){
		this.idCollab = idCollab;
		this.idSong = idSong;
	}

	public String updateColaboracion(){
		return "UPDATE " + tabla + " SET " + idColaboracion + " ='" + idCollab + "' WHERE " + idSong + " = " + idCancion + ";";
	}

	public String updateCancion(){
		return "UPDATE " + tabla + " SET " + idCancion + " ='" + idSong + "' WHERE " + idColaboracion + " = " + idCollab + ";";
	}

	public String deleteColaboracion(){
		return "DELETE FROM " + tabla + " WHERE " + idColaboracion + " = '" + idCollab + "';"  
	}

	public String deleteCancion(){
		return "DELETE FROM " + tabla + " WHERE " + idCancion + " = '" + idSong + "';"  
	}

	public String insert(){
		return "INSERT INTO " + tabla "(" + idColaboracion + "," + idCancion + ") " +  " VALUES " + "('" + idCollab + "','" + idSong + "');"; 
	}

	public String selectColaboracion(){
		return "SELECT " + idColaboracion + " FROM " tabla + " WHERE " + idCancion " = " + idSong + ";";
	}

	public String selectCancion(){
		return "SELECT " + idCancion + " FROM " tabla + " WHERE " + idColaboracion " = " + idCollab + ";";
	}

	public String selectTodo(){
		return "SELECT * FROM " tabla ";";
	}

	public void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = conexion.createStatement();
		switch(operacion){
			case "updateColaboracion":
				comando = updateColaboracion();
				stmt.executeUpdate(comando);
				break;
			case "updateCancion":
				comando = updateCancion();
				stmt.executeUpdate(comando);
				break;
			case "deleteColaboracion":
				comando = deleteColaboracion();
				stmt.executeUpdate(comando);
				break;
			case "deleteCancion":
				comando = deleteCancion();
				stmt.executeUpdate(comando);
				break;
			case "insert":
				comando = insert();
				stmt.executeUpdate(comando);
				break;
			default:
				Manejador.cerrarConexion();
				throw new ErrorBaseDeDatos("No conozco esa operacion.");
		}
		Manejador.cerrarConexion();
	}

	public ResultSet realizaBusqueda(String operacion){
		String comando ="";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = conexion.createStatement();
		ResultSet rs = null;
		switch(operacion){
			case "selectColaboracion":
				comando = selectColaboracion();
				rs = stmt.executeUpdate(comando);
				break;
			case "selectCancion":
				comando = selectCancion();
				rs = stmt.executeUpdate(comando);
				break;
			case "selectTodo":
				comando = selectTodo();
				rs = stmt,executeUpdate(comando);
				break;
			default:
				Manejador.cerrarConexion();
				throw new ErrorBaseDeDatos("No conozco esa operacion.");
		}
		return rs;
	}
	
}