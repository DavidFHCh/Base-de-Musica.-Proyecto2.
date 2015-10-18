package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;


/**
*	Clase que modela la tabla de Relaciones entre Disqueras y Canciones.
*/
public class Disqueras_Cancion{

	private static String tabla = "Disqueras_Cancion";
	private static String idDisquera = "id_disquera";
	private static String idCancion = "id_cancion";
	private int idLabel;
	private int idSong;

	/**
	* Constructor para modelar un renglon de la tabla.
	* @param idLabel identificador de este renglon. 
	* @param idSong identificador de la cancion.
	*/
	public Disqueras_Cancion(int idLabel, int idSong){
		this.idLabel = idLabel;
		this.idSong = idSong;
	}

	/**
	* Constructor vacio.
	*/
	public Disqueras_Cancion(){}

	private String updateDisquera(){
		return "UPDATE " + tabla + " SET " + idDisquera + " ='" + idLabel + "' WHERE " + idSong + " = " + idCancion + ";";
	}

	private String updateCancion(){
		return "UPDATE " + tabla + " SET " + idCancion + " ='" + idSong + "' WHERE " + idDisquera + " = " + idLabel + ";";
	}

	private String deleteDisquera(){
		return "DELETE FROM " + tabla + " WHERE " + idDisquera + " = '" + idLabel + "';"; 
	}

	private String deleteCancion(){
		return "DELETE FROM " + tabla + " WHERE " + idCancion + " = '" + idSong + "';";  
	}

	private String insert(){
		return "INSERT INTO " + tabla + "(" + idDisquera + "," + idCancion + ") " +  " VALUES " + "('" + idLabel + "','" + idSong + "');"; 
	}

	private String selectDisquera(int idSong){
		return "SELECT " + idDisquera + " FROM " + tabla + " WHERE " + idCancion + " = " + idSong + ";";
	}

	private String selectCancion(int idLabel){
		return "SELECT " + idCancion + " FROM " + tabla + " WHERE " + idDisquera + " = " + idLabel + ";";
	}

	private String selectTodo(){
		return "SELECT * FROM " + tabla;
	}

	private String selectTodoIdCancion(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idCancion;
	}

	private String selectTodoIdDisquera(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idDisquera;
	}

	private String joinCancionesADisquerasID(String id){
		return "SELECT cancion,año,duracion,Recod_Label FROM Canciones,Disqueras JOIN(" + selectTodoIdCancion(id) + ") ON Disqueras.id = " + idDisquera + " and Canciones.id = " + idCancion + ";"; 
	}

	private String joinDisquerasACancionesID(String id){
		return "SELECT cancion,año,duracion,Recod_Label FROM Canciones,Disqueras JOIN(" + selectTodoIdDisquera(id) + ") ON Disqueras.id = " + idDisquera + " and Canciones.id = " + idCancion + ";"; 
	}

	private String joinTodo(){
		return "SELECT cancion,año,duracion,Recod_Label FROM Canciones,Disqueras JOIN(" + selectTodo() + ") ON Disqueras.id = " + idDisquera + " and Canciones.id = " + idCancion + ";"; 
	}

/**
	* Realiza operaciones que modifican la tabla.
	* @param operacion puede recibir cualquiera de estas frases: updateDisquera, updateCancion, deleteDisquera, deleteCancion, insert.
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/	
	public synchronized void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion();
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

	/**
	* Realiza operaciones sencillas de busqueda en la tabla.
	* @param operacion puede recibir cualquiera de estas frases: selectTodo,selectCancion,selectDisquera.
	* @param id para saber que se esta buscando.
	* @return ResultSet 
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public ResultSet realizaBusqueda(String operacion, int id){
		String comando ="";
		Connection conexion = Manejador.abrirConexion();
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
					
					throw new ErrorBaseDeDatos("No conozco esa operacion.");
			}
		}catch(SQLException sqle){
			
			throw new ErrorBaseDeDatos("Algo paso.");
		}
		
		return rs;
	}
	
	/**
	* Realiza operaciones no triviales de busqueda entre dos tablas.
	* @param operacion puede recibir cualquiera de estas frases: joinCancionesADisquerasIDLike,joinCancionesADisquerasIDAnio,joinCancionesADisquerasIDEntreAnios,joinCancionesADisquerasIDDuracion,joinCancionesADisquerasIDEntreDuraciones,joinDisquerasACancionesID.
	* @param param1 Se le pasan los parametros para realizar la busqueda.
	* @param param2 Se le pasan los parametros para realizar la busqueda. 
	* @return ResultSet 
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public LinkedList<ResultSet> realizaBusquedaEspecial(String operacion, String param1,String param2){
		String comando = "";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = null;
		ResultSet rs1 = null,rs = null;
		LinkedList<ResultSet> lrs = new LinkedList<ResultSet>();
		Canciones can = new Canciones();
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "joinTodo":
						comando = joinTodo();
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					break;
				case "joinCancionesADisquerasIDLike":
					rs = can.realizaBusqueda("selectLikeID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesADisquerasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesADisquerasIDAnio":
					rs = can.realizaBusqueda("selectAnioID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesADisquerasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesADisquerasIDEntreAnios":
					rs = can.realizaBusqueda("selectEntreAniosID",0,param1,param2); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesADisquerasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesADisquerasIDDuracion":
					rs = can.realizaBusqueda("selectDuracionID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesADisquerasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesADisquerasIDEntreDuraciones":
					rs = can.realizaBusqueda("selectEntreDuracionesID",0,param1,param2); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesADisquerasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinDisquerasACancionesID":
					Disqueras disq = new Disqueras();
				    rs = disq.realizaBusqueda("selectLikeID",0,param1); //metodo de clase Generos.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinDisquerasACancionesID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}	
					break;
				default:
				
				throw new ErrorBaseDeDatos("No conozco esa operacion."); 
			}
		}catch(SQLException sqle){
			
			throw new ErrorBaseDeDatos("Algo paso.");
		}
		
		return lrs;
	}

}