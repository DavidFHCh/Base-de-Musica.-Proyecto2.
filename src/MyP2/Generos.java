package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

public class Generos{

	private static String tabla = "Generos";
	private static String id = "id";
	private static String genero = "generos";
	private String genre;
	private int idGenre;

	public Generos(int idGenre, String genre){
		this.idGenre = idGenre;
		this.genre = genre;
	}

	public String update(){
		return "UPDATE " + tabla + " SET " + genero + " =' " + genre + "' WHERE " + id + " = " + idGenre + ";";
	}

	public String delete(){
		return "DELETE FROM " + tabla + " WHERE " + genero + " = '" + genre + "';";  
	}

	public String insert(){
		return "INSERT INTO " + tabla + "(" + id + "," + genero + ") " +  " VALUES " + "('" + idGenre + "','" + genre + "');";
	}

	public String select(){
		return "SELECT " + genero + " FROM " + tabla + " WHERE " + id + " = " + idGenre + ";";
	}

	public String selectTodo(){
		return "SELECT * FROM " + tabla + ";";
	}

	public String selectLike(){
		return "SELECT " + genero + " FROM " + tabla + " WHERE lower(" + genero + ") LIKE '%" + genre.toLowerCase() + "%';";
	}

	public void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "update":
					comando = update();
					stmt.executeUpdate(comando);
					break;
				case "delete":
					comando = delete();
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

	public ResultSet realizaBusqueda(String operacion){
		String comando ="";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "select":
					comando = select();
					rs = stmt.executeQuery(comando);
					break;
				case "selectTodo":
					comando = selectTodo();
					rs = stmt.executeQuery(comando);
					break;
				case "selectLike":
					comando = selectLike();
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