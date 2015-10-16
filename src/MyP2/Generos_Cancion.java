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
		return "SELECT * FROM " + tabla + ";";
	}

	private String selectTodoIdCancion(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idCancion +";";
	}

	private String selectTodoIdGenero(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idGenero +";";
	}
//falta meterlos al switch de abajo...
	private String joinCancionesAGenerosID(String id){
		return "SELECT cancion,año,duracion,generos FROM Canciones,Generos JOIN(" + selectTodoIdCancion(id) + ") ON Generos.id = " + idGenero + " and Canciones.id = " + idCancion + ";"; 
	}

	private String joinGenerosACancionesID(String id){
		return "SELECT cancion,año,duracion,generos FROM Canciones,Generos JOIN(" + selectTodoIdGenero(id) + ") ON Generos.id = " + idGenero + " and Canciones.id = " + idCancion + ";"; 
	}
//hasta aqui


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

	public LinkedList<ResultSet> realizaBusquedaEspecial(String operacion, String param1,param2){
		String comando = "";
		Connection conexion = Manejador.abrirConexion(false);
		Statement stmt = null;
		ResultSet rs1;
		LinkedList<ResultSet> lrs = new LinkedList<ResultSet>();
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "joinCancionesAGenerosIDLike":
					Canciones can = new Canciones();
					ResultSet rs = can.realizaBusqueda("selectLikeID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAGenerosID(iden.toString());
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAGenerosIDAnio":
					Canciones can = new Canciones();
					ResultSet rs = can.realizaBusqueda("selectAnioID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAGenerosID(iden.toString());
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAGenerosIDEntreAnios":
					Canciones can = new Canciones();
					ResultSet rs = can.realizaBusqueda("selectEntreAniosID",0,param1,param2); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAGenerosID(iden.toString());
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAGenerosIDDuracion":
					Canciones can = new Canciones();
					ResultSet rs = can.realizaBusqueda("selectDuracionID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAGenerosID(iden.toString());
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAGenerosIDEntreDuraciones":
					Canciones can = new Canciones();
					ResultSet rs = can.realizaBusqueda("selectEntreDuracionesID",0,param1,param2); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAGenerosID(iden.toString());
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinGenerosACancionesID":
					Generos gen = new Generos();
					ResultSet rs = gen.realizaBusqueda("selectLikeID",0,param1); //metodo de clase Generos.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinGenerosACancionesID(iden.toString());
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}	
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
		return lrs;
	}
	
}