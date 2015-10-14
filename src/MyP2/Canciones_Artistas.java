package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

public class Canciones_Artistas{

	private static String tabla = "Canciones_Artistas";
	private static String idArtista = "id_artistas";
	private static String idCancion = "id_cancion";
	private int idArtist;
	private int idSong;

	public Canciones_Artistas(int idArtist, int idSong){
		this.idArtist = idArtist;
		this.idSong = idSong;
	}

	public Canciones_Artistas(){}

	public String updateArtista(){
		return "UPDATE " + tabla + " SET " + idArtista + " ='" + idArtist + "' WHERE " + idSong + " = " + idCancion + ";";
	}

	public String updateCancion(){
		return "UPDATE " + tabla + " SET " + idCancion + " ='" + idSong + "' WHERE " + idArtist + " = " + idArtista + ";";
	}

	public String deleteArtista(){
		return "DELETE FROM " + tabla + " WHERE " + idArtista + " = '" + idArtist + "';";
	}

	public String deleteCancion(){
		return "DELETE FROM " + tabla + " WHERE " + idCancion + " = '" + idSong + "';";  
	}

	public String insert(){
		return "INSERT INTO " + tabla + "(" + idArtista + "," + idCancion + ")" +  " VALUES " + "('" + idArtist + "','" + idSong + "');"; 
	}

	public String selectArtista(int idSong){
		return "SELECT " + idArtista + " FROM " + tabla + " WHERE " + idCancion + " = " + idSong + ";";
	}

	public String selectCancion(int idArtist){
		return "SELECT " + idCancion + " FROM " + tabla + " WHERE " + idArtista + " = " + idArtist + ";";
	}

	public String selectTodo(){
		return "SELECT * FROM " + tabla + ";";
	}

	public void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "updateArtista":
					comando = updateArtista();
					stmt.executeUpdate(comando);
					break;
				case "updateCancion":
					comando = updateCancion();
					stmt.executeUpdate(comando);
					break;
				case "deleteArtista":
					comando = deleteArtista();
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

	public ResultSet realizaBusqueda(String operacion,int id){
		String comando ="";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "selectArtista":
					comando = selectArtista(id);
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