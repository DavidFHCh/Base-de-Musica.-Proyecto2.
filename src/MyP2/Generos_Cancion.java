package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

public class Generos_Cancion{

	private static String tabla = "Generos_Cancion";
	private static String idGenero = "id_genero";
	private static String idCancion = "id_cancion";
	private int idGenre;
	private int idSong;

	public Generos_Cancion(int idGenre, int idSong){
		this.idGenre = idGenre;
		this.idSong = idSong;
	}

	public Generos_Cancion(){}

	public String updateGenero(){
		return "UPDATE " + tabla + " SET " + idGenero + " ='" + idGenre + "' WHERE " + idSong + " = " + idCancion + ";";
	}

	public String updateCancion(){
		return "UPDATE " + tabla + " SET " + idCancion + " ='" + idSong + "' WHERE " + idGenero + " = " + idGenre + ";";
	}

	public String deleteGenero(){
		return "DELETE FROM " + tabla + " WHERE " + idGenero + " = '" + idGenre + "';";  
	}

	public String deleteCancion(){
		return "DELETE FROM " + tabla + " WHERE " + idCancion + " = '" + idSong + "';";  
	}

	public String insert(){
		return "INSERT INTO " + tabla + "(" + idGenero + "," + idCancion + ") " +  " VALUES " + "('" + idGenre + "','" + idSong + "');"; 
	}

	public String selectGenero(int idSong){
		return "SELECT " + idGenero + " FROM " + tabla + " WHERE " + idCancion + " = " + idSong + ";";
	}

	public String selectCancion(int idGenre){
		return "SELECT " + idCancion + " FROM " + tabla + " WHERE " + idGenero + " = " + idGenre + ";";
	}

	public String selectTodo(){
		return "SELECT * FROM " + tabla + ";";
	}

	public void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion(false);
		Statement stmt = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "updateGenero":
					comando = updateGenero();
					stmt.executeUpdate(comando);
					break;
				case "updateCancion":
					comando = updateCancion();
					stmt.executeUpdate(comando);
					break;
				case "deleteGenero":
					comando = deleteGenero();
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
		}catch(SQLException sqle){
			Manejador.cerrarConexion();
			throw new ErrorBaseDeDatos("Algo paso.");
		}
		Manejador.cerrarConexion();
	}

	public ResultSet realizaBusqueda(String operacion, int id){
		String comando ="";
		Connection conexion = Manejador.abrirConexion(false);
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "selectGenero":
					comando = selectGenero(id);
					rs = stmt.executeQuery(comando);
					break;
				case "selectCancion":
					comando = selectCancion(id);
					rs = stmt.executeQuery(comando);
					break;
				case "selectTodo":
					comando = selectTodo();
					rs = stmt.executeQuery(comando);
					break;
				default:
					Manejador.cerrarConexion();
					throw new ErrorBaseDeDatos("No conozco esa operacion.");
			}
		}catch(SQLException sqle){
			Manejador.cerrarConexion();
			throw new ErrorBaseDeDatos("Algo paso.");
		}
		Manejador.cerrarConexion();
		return rs;
	}
	
}