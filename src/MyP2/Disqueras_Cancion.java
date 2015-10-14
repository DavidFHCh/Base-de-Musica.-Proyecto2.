package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

public class Disqueras_Cancion{

	private static String tabla = "Disqueras_Cancion";
	private static String idDisquera = "id_disquera";
	private static String idCancion = "id_cancion";
	private int idLabel;
	private int idSong;

	public Disqueras_Cancion(int idLabel, int idSong){
		this.idLabel = idLabel;
		this.idSong = idSong;
	}

	public Disqueras_Cancion(){}

	public String updateDisquera(){
		return "UPDATE " + tabla + " SET " + idDisquera + " ='" + idLabel + "' WHERE " + idSong + " = " + idCancion + ";";
	}

	public String updateCancion(){
		return "UPDATE " + tabla + " SET " + idCancion + " ='" + idSong + "' WHERE " + idDisquera + " = " + idLabel + ";";
	}

	public String deleteDisquera(){
		return "DELETE FROM " + tabla + " WHERE " + idDisquera + " = '" + idLabel + "';"; 
	}

	public String deleteCancion(){
		return "DELETE FROM " + tabla + " WHERE " + idCancion + " = '" + idSong + "';";  
	}

	public String insert(){
		return "INSERT INTO " + tabla + "(" + idDisquera + "," + idCancion + ") " +  " VALUES " + "('" + idLabel + "','" + idSong + "');"; 
	}

	public String selectDisquera(int idSong){
		return "SELECT " + idDisquera + " FROM " + tabla + " WHERE " + idCancion + " = " + idSong + ";";
	}

	public String selectCancion(int idLabel){
		return "SELECT " + idCancion + " FROM " + tabla + " WHERE " + idDisquera + " = " + idLabel + ";";
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
				case "updateDisquera":
					comando = updateDisquera();
					stmt.executeUpdate(comando);
					break;
				case "updateCancion":
					comando = updateCancion();
					stmt.executeUpdate(comando);
					break;
				case "deleteDisquera":
					comando = deleteDisquera();
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
				case "selectDisquera":
					comando = selectDisquera(id);
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