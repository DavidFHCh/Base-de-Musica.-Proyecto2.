package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

public class Canciones{

private static String tabla = "Canciones";
	private static String id = "id";
	private static String cancion = "cancion";
	private static String anio = "año";
	private static String duracion = "duracion";
	private String song;
	private String year;
	private String length;
	private int idSong;

	public Canciones(int idGenre, String song, String year, String length){
		this.idSong = idSong;
		this.year = year;
		this.length = length;
		this.song = song;
	}

	public Canciones(){}

	public String update(){
		return "UPDATE " + tabla + " SET " + anio + " = " + year + " , " + duracion + " = " + length + " WHERE " + id + " = " + idSong + ";";
	}

	public String updateAnio(){
		return "UPDATE " + tabla + " SET " + anio + " = " + year + " WHERE " + id + " = " + idSong + ";";
	}

	public String updateDuracion(){
		return "UPDATE " + tabla + " SET " + duracion + " = " + length + " WHERE " + id + " = " + idSong + ";";
	}

	public String delete(){
		return "DELETE FROM " + tabla + " WHERE " + cancion + " = '" + song + "';";  
	}

	public String insert(){
		return "INSERT INTO " + tabla + "(" + id + "," + cancion + "," + anio + "," + duracion + ") " +  " VALUES " + "('" + idSong + "','" + song + "'," + year + "," + length + ");";
	}

	public String select(int idSong){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE " + id + " = " + idSong + ";";
	}

	public String selectTodo(){
		return "SELECT * FROM " + tabla + ";";
	}

	public String selectLike(String song){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE lower(" + cancion + ") LIKE '%" + song.toLowerCase() + "%';";
	}

	public String selectAnio(String year){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE" + anio + " = " + year + ";";
	}

	public String selectEntreAnios(String anio1, String anio2){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE" + anio + " BETWEEN " + anio1 + " AND " + anio2 + ";";
	}

	public String selectDuracion(String length){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE" + duracion + " = " + length + ";";
	}

	public String selectEntreDuraciones(String dur1, String dur2){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE" + duracion + " BETWEEN " + dur1 + " AND " + dur2 + ";";
	}

//falta corregir estos metodos que siguen.
	
	public void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion(false);
		Statement stmt = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "update":
					comando = update();
					stmt.executeUpdate(comando);
					break;
				case "updateAnio":
					comando = updateAnio();
					stmt.executeUpdate(comando);
					break;
				case "updateDuracion":
					comando = updateDuracion();
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

	public ResultSet realizaBusqueda(String operacion,int id,String param1,String param2){
		String comando ="";
		Connection conexion = Manejador.abrirConexion(false);
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "select":
					comando = select(id);
					rs = stmt.executeQuery(comando);
					break;
				case "selectTodo":
					comando = selectTodo();
					rs = stmt.executeQuery(comando);
					break;
				case "selectLike":
					comando = selectLike(param1);
					rs = stmt.executeQuery(comando);
					break;
				case "selectAnio":
					comando = selectAnio(param1);
					rs = stmt.executeQuery(comando);
					break;
				case "selectEntreAnios":
					comando = selectEntreAnios(param1,param2);
					rs = stmt.executeQuery(comando);
					break;
				case "selectDuracion":
					comando = selectDuracion(param1);
					rs = stmt.executeQuery(comando);
					break;
					case "selectEntreDuraciones":
					comando = selectEntreDuraciones(param1,param2);
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