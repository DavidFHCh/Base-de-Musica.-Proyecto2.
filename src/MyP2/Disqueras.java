package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

/**
*	Clase que modela la tabla de Relaciones entre Disqueras.
*/
public class Disqueras{

	private static String tabla = "";
	private static String id = "id";
	private static String disquera = "Recod_Label";
	private String label;
	private int idLabel;

	

	/**
	* Constructor para modelar un renglon de la tabla.
	* @param idLabel identificador de este renglon. 
	* @param label nombre del artista.
	*/
	public Disqueras(int idLabel, String label){
		this.idLabel = idLabel;
		this.label = label;
	}

	/**
	* Constructor vacio.
	*/
	public Disqueras(){}

	private String update(){
		return "UPDATE " + tabla + " SET " + disquera + " = '" + label + "' WHERE " + id + " = " + idLabel + ";";
	}

	private String delete(){
		return "DELETE FROM " + tabla + " WHERE " + disquera + " = '" + label + "';";  
	}

	private String insert(){
		return "INSERT INTO " + tabla + "(" + id + "," + disquera + ") " +  " VALUES " + "('" + idLabel + "','" + label + "');";
	}

	private String select(int idLabel){
		return "SELECT " + disquera + " FROM " + tabla + " WHERE " + id + " = " + idLabel + ";";
	}

	private String selectTodo(){
		return "SELECT " + disquera + " FROM " + tabla + ";";
	}

	private String selectTodoID(){
		return "SELECT * FROM " + tabla + ";";
	}

	private String selectLike(String label){
		return "SELECT " + disquera + " FROM " + tabla + " WHERE lower(" + disquera + ") LIKE '%" + label.toLowerCase() + "%';";
	}

	private String selectLikeID(String label){
		return "SELECT * FROM " + tabla + " WHERE lower(" + disquera + ") LIKE '%" + label.toLowerCase() + "%';";
	} 

	/**
	* Realiza operaciones que modifican la tabla.
	* @param operacion puede recibir cualquiera de estas frases: update, delete, insert.
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public synchronized void realizaOperacion(String operacion){
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
	* @param operacion puede recibir cualquiera de estas frases: select, selectTodo,selectLike.
	* @param id se requiere para la operacion select.
	* @param param Terminos de la busqueda de las dos tablas.
	* @return ResultSet 
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public ResultSet realizaBusqueda(String operacion, int id, String param){
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
				case "selectTodoID":
					comando = selectTodoID();
					rs = stmt.executeQuery(comando);
					break;
				case "selectLike":
					comando = selectLike(param);
					rs = stmt.executeQuery(comando);
					break;
				case "selectLikeID":
					comando = selectLikeID(param);
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