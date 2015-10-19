package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;



/**
*	Clase que modela la tabla de Relaciones entre Generos y Canciones.
*/
public class Generos_Cancion{

	private static String tabla = "Generos_Cancion";
	private static String idGenero = "id_genero";
	private static String idCancion = "id_cancion";
	private int idGenre;
	private int idSong;

	/**
	* Constructor para modelar un renglon de la tabla.
	* @param idGenre identificador de este renglon. 
	* @param idSong identificador de la cancion.
	*/
	public Generos_Cancion(int idGenre, int idSong){
		this.idGenre = idGenre;
		this.idSong = idSong;
	}

	/**
	* Constructor vacio.
	*/
	public Generos_Cancion(){}

	private String updateGenero(){
		return "UPDATE " + tabla + " SET " + idGenero + " ='" + idGenre + "' WHERE " + idSong + " = " + idCancion + ";";
	}

	private String updateCancion(){
		return "UPDATE " + tabla + " SET " + idCancion + " ='" + idSong + "' WHERE " + idGenero + " = " + idGenre + ";";
	}

	private String deleteGenero(){
		return "DELETE FROM " + tabla + " WHERE " + idGenero + " = '" + idGenre + "';";  
	}

	private String deleteCancion(){
		return "DELETE FROM " + tabla + " WHERE " + idCancion + " = '" + idSong + "';";  
	}

	private String insert(){
		return "INSERT INTO " + tabla + "(" + idGenero + "," + idCancion + ") " +  " VALUES " + "('" + idGenre + "','" + idSong + "');"; 
	}

	private String selectGenero(int idSong){
		return "SELECT " + idGenero + " FROM " + tabla + " WHERE " + idCancion + " = " + idSong + ";";
	}

	private String selectCancion(int idGenre){
		return "SELECT " + idCancion + " FROM " + tabla + " WHERE " + idGenero + " = " + idGenre + ";";
	}

	private String selectTodo(){
		return "SELECT * FROM " + tabla;
	}

	private String selectTodoIdCancion(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idCancion;
	}

	private String selectTodoIdGenero(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idGenero;
	}

	private String joinCancionesAGenerosID(String id){
		return "SELECT cancion,año,duracion,generos FROM Canciones,Generos JOIN(" + selectTodoIdCancion(id) + ") ON Generos.id = " + idGenero + " and Canciones.id = " + idCancion + ";"; 
	}

	private String joinGenerosACancionesID(String id){
		return "SELECT cancion,año,duracion,generos FROM Canciones,Generos JOIN(" + selectTodoIdGenero(id) + ") ON Generos.id = " + idGenero + " and Canciones.id = " + idCancion + ";"; 
	}

	private String joinTodo(){
		return "SELECT cancion,año,duracion,generos FROM Canciones,Generos JOIN(" + selectTodo() + ") ON Generos.id = " + idGenero + " and Canciones.id = " + idCancion + ";"; 
	}

	private String joinTodoAnio(String anio){
		return "SELECT cancion,año,duracion,generos FROM Canciones,Generos JOIN(" + selectTodo() + ") ON Generos.id = " + idGenero + " and Canciones.id = " + idCancion + " and año = + " + anio + ";"; 
	}

	private String joinTodoDuracion(String dur){
		return "SELECT cancion,año,duracion,generos FROM Canciones,Generos JOIN(" + selectTodo() + ") ON Generos.id = " + idGenero + " and Canciones.id = " + idCancion + " and duracion = + " + dur + ";"; 
	}

	private String joinTodoEntreDur(String dur1, String dur2){
		return "SELECT cancion,año,duracion,generos FROM Canciones,Generos JOIN(" + selectTodo() + ") ON Generos.id = " + idGenero + " and Canciones.id = " + idCancion + " and duracion BETWEEN "  + dur1 + " AND "  + dur2 + ";"; 
	}

	private String joinTodoEntreAnio(String dur1, String dur2){
		return "SELECT cancion,año,duracion,generos FROM Canciones,Generos JOIN(" + selectTodo() + ") ON Generos.id = " + idGenero + " and Canciones.id = " + idCancion + " and año BETWEEN "  + dur1 + " AND "  + dur2 + ";"; 
	}

	/**
	* Realiza operaciones que modifican la tabla.
	* @param operacion puede recibir cualquiera de estas frases: updateGenero, updateCancion, deleteGenero, deleteCancion, insert.
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public synchronized void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion();
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

	/**
	* Realiza operaciones sencillas de busqueda en la tabla.
	* @param operacion puede recibir cualquiera de estas frases: selectTodo,selectCancion,selectGenero.
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
					
					throw new ErrorBaseDeDatos("No conozco esa operacion.");
			}
		}catch(SQLException sqle){
			
			throw new ErrorBaseDeDatos("Algo paso.");
		}
		
		return rs;
	}

	/**
	* Realiza operaciones no triviales de busqueda entre dos tablas.
	* @param operacion puede recibir cualquiera de estas frases: joinCancionesAGenerosIDLike,joinCancionesAGenerosIDAnio,joinCancionesAGenerosIDEntreAnios,joinCancionesAGenerosIDDuracion,joinCancionesAGenerosIDEntreDuraciones,joinGenerosACancionesID.
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
				case "joinTodoAnio":
						comando = joinTodoAnio(param1);
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					break;
					case "joinTodoDuracion":
						comando = joinTodoDuracion(param1);
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					break;
					case "joinTodoEntreDur":
						comando = joinTodoEntreDur(param1,param2);
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					break;
					case "joinTodoEntreAnio":
						comando = joinTodoEntreAnio(param1,param2);
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					break;
				case "joinCancionesAGenerosIDLike":
					rs = can.realizaBusqueda("selectLikeID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAGenerosID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAGenerosIDAnio":
					rs = can.realizaBusqueda("selectAnioID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAGenerosID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAGenerosIDEntreAnios":
					rs = can.realizaBusqueda("selectEntreAniosID",0,param1,param2); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAGenerosID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAGenerosIDDuracion":
					rs = can.realizaBusqueda("selectDuracionID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAGenerosID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAGenerosIDEntreDuraciones":
					rs = can.realizaBusqueda("selectEntreDuracionesID",0,param1,param2); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAGenerosID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinGenerosACancionesID":
					Generos gen = new Generos();
				    rs = gen.realizaBusqueda("selectLikeID",0,param1); //metodo de clase Generos.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinGenerosACancionesID(Integer.toString(iden));
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