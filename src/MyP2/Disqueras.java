package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

public class Disqueras{

	private static String tabla = "";
	private static String id = "id";
	private static String disquera = "Recod_Label";
	private String label;
	private int idLabel;

	public Disqueras(int idLabel, String label){
		this.idLabel = idLabel;
		this.label = label;
	}

	public String update(){
		return "UPDATE " + tabla + " SET " + disquera + "='" + label + "' WHERE " + id + " = " + idLabel + ";";
	}

	public String delete(){
		return "DELETE FROM " + tabla + " WHERE " + disquera + " = '" + label + "';"  
	}

	public String insert(){
		return "INSERT INTO " + tabla "("+ id + "," + disquera + ") " +  " VALUES " + "('" + idLabel + "','" + label + "');";
	}

	public String select(){
		return "SELECT " + disquera + " FROM " tabla + " WHERE " + id " = " + idLabel + ";";
	}

	public String selectTodo(){
		return "SELECT * FROM " + tabla +";";
	}

	public String selectLike(){
		return "SELECT " + disquera + " FROM " + tabla + " WHERE lower(" + disquera + ") LIKE '%" + label.toLowerCase() "%';";
	}

	public void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = conexion.createStatement();
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
		Manejador.cerrarConexion();
	}

	public ResultSet realizaBusqueda(String operacion){
		String comando ="";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = conexion.createStatement();
		ResultSet rs = null;
		switch(operacion){
			case "select":
			comando = select();
				rs = stmt.executeUpdate(comando);
				break;
			case "selectTodo":
				comando = selectTodo();
				rs = stmt.executeUpdate(comando);
				break;
			case "selectLike":
				comando = selectLike();
				rs = stmt.executeUpdate();
				break;
			default:
				Manejador.cerrarConexion();
				throw new ErrorBaseDeDatos("No conozco esa operacion.");
		}
		Manejador.cerrarConexion();
		return rs;
	}
	
}