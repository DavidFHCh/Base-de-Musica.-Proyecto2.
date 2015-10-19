package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

/**
*	Clase que modela la tabla de canciones.
*/
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

	/**
	* Constructor para modelar un renglon de la tabla.
	* @param idGenre identificador de este renglon. 
	* @param song nombre de la canocion.
	* @param year año de la cancion.
	* @param length duracion de la cancion.
	*/
	public Canciones(int idGenre, String song, String year, String length){
		this.idSong = idSong;
		this.year = year;
		this.length = length;
		this.song = song;
	}

	/**
	* Constructor vacio.
	*/
	public Canciones(){}

	private String update(){
		return "UPDATE " + tabla + " SET " + anio + " = " + year + " , " + duracion + " = " + length + " WHERE " + id + " = " + idSong + ";";
	}

	private String updateAnio(){
		return "UPDATE " + tabla + " SET " + anio + " = " + year + " WHERE " + id + " = " + idSong + ";";
	}

	private String updateDuracion(){
		return "UPDATE " + tabla + " SET " + duracion + " = " + length + " WHERE " + id + " = " + idSong + ";";
	}

	private String delete(){
		return "DELETE FROM " + tabla + " WHERE " + cancion + " = '" + song + "';";  
	}

	private String insert(){
		return "INSERT INTO " + tabla + "(" + id + "," + cancion + "," + anio + "," + duracion + ") " +  " VALUES " + "('" + idSong + "','" + song + "'," + year + "," + length + ");";
	}

	private String select(int idSong){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE " + id + " = " + idSong + ";";
	}

	private String selectTodoID(){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + ";";
	}

	private String selectTodo(){
		return "SELECT * FROM " + tabla + ";";
	}

	private String selectLike(String song){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE lower(" + cancion + ") LIKE '%" + song.toLowerCase() + "%';";
	}

	private String selectLikeID(String song){
		return "SELECT * FROM " + tabla + " WHERE lower(" + cancion + ") LIKE '%" + song.toLowerCase() + "%';";
	}

	private String selectAnio(String year){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE " + anio + " = " + year + ";";
	}

	private String selectAnioID(String year){
		return "SELECT * FROM " + tabla + " WHERE " + anio + " = " + year + ";";
	}

	private String selectEntreAnios(String anio1, String anio2){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE" + anio + " BETWEEN " + anio1 + " AND " + anio2 + ";";
	}

	private String selectEntreAniosID(String anio1, String anio2){
		return "SELECT * FROM " + tabla + " WHERE" + anio + " BETWEEN " + anio1 + " AND " + anio2 + ";";
	}

	private String selectDuracion(String length){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE" + duracion + " = " + length + ";";
	}

	private String selectDuracionID(String length){
		return "SELECT * FROM " + tabla + " WHERE" + duracion + " = " + length + ";";
	}

	private String selectEntreDuraciones(String dur1, String dur2){
		return "SELECT " + cancion + "," + anio + "," + duracion + " FROM " + tabla + " WHERE" + duracion + " BETWEEN " + dur1 + " AND " + dur2 + ";";
	}

	private String selectEntreDuracionesID(String dur1, String dur2){
		return "SELECT * FROM " + tabla + " WHERE" + duracion + " BETWEEN " + dur1 + " AND " + dur2 + ";";
	}
 

	/**
	* Realiza operaciones que modifican la tabla.
	* @param operacion puede recibir cualquiera de estas frases: update, updateAnio, updateDuracion, delete, insert.
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public synchronized void realizaOperacion(String operacion){
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

	/**
	* Realiza operaciones sencillas de busqueda en la tabla.
	* @param operacion puede recibir cualquiera de estas frases: select, selectTodo,selectLike,selectAnio, selectEnteAnios, selectDuracion, selectEntreDuraciones.
	* @param id se requiere para la operacion select.
	* @param param1 Terminos de la busqueda de las dos tablas.  
	* @param param2 Terminos de la busqueda de las dos tablas.
	* @return ResultSet 
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public ResultSet realizaBusqueda(String operacion,int id,String param1,String param2){
		String comando ="";
		Connection conexion = Manejador.abrirConexion();
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
				case "selectTodoID":
					comando = selectTodoID();
					rs = stmt.executeQuery(comando);
					break;
				case "selectLike":
					comando = selectLike(param1);
					rs = stmt.executeQuery(comando);
					break;
				case "selectLikeID":
					comando = selectLikeID(param1);
					System.out.println(comando);
					rs = stmt.executeQuery(comando);
					break;
				case "selectAnio":
					comando = selectAnio(param1);
					rs = stmt.executeQuery(comando);
					break;
				case "selectAnioID":
					comando = selectAnioID(param1);
					rs = stmt.executeQuery(comando);
					break;
				case "selectEntreAnios":
					comando = selectEntreAnios(param1,param2);
					rs = stmt.executeQuery(comando);
					break;
				case "selectEntreAniosID":
					comando = selectEntreAniosID(param1,param2);
					rs = stmt.executeQuery(comando);
					break;
				case "selectDuracion":
					comando = selectDuracion(param1);
					rs = stmt.executeQuery(comando);
					break;
				case "selectDuracionID":
					comando = selectDuracionID(param1);
					rs = stmt.executeQuery(comando);
					break;
				case "selectEntreDuraciones":
					comando = selectEntreDuraciones(param1,param2);
					rs = stmt.executeQuery(comando);
					break;
				case "selectEntreDuracionesID":
					comando = selectEntreDuracionesID(param1,param2);
					rs = stmt.executeQuery(comando);
					break;
				default:					
					throw new ErrorBaseDeDatos("No conozco esa operacion.");
			}
		}catch(Exception e){	
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			throw new ErrorBaseDeDatos("Algo paso.");

		}
		return rs;
	}
	
}