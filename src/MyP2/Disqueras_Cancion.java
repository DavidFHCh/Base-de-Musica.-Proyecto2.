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
		return "SELECT * FROM " + tabla + ";";
	}

	private String selectTodoIdCancion(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idCancion +";";
	}

	private String selectTodoIdDisquera(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idDisquera +";";
	}

	private String joinCancionesADisquerasID(String id){
		return "SELECT cancion,año,duracion,Recod_Label FROM Canciones,Disqueras JOIN(" + selectTodoIdCancion(id) + ") ON Disqueras.id = " + idDisquera + " and Canciones.id = " + idCancion + ";"; 
	}

	private String joinDisquerasACancionesID(String id){
		return "SELECT cancion,año,duracion,Recod_Label FROM Canciones,Disqueras JOIN(" + selectTodoIdDisquera(id) + ") ON Disqueras.id = " + idDisquera + " and Canciones.id = " + idCancion + ";"; 
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
	
	public LinkedList<ResultSet> realizaBusquedaEspecial(String operacion, String param1,String param2){
		String comando = "";
		Connection conexion = Manejador.abrirConexion(false);
		Statement stmt = null;
		ResultSet rs1 = null,rs = null;
		LinkedList<ResultSet> lrs = new LinkedList<ResultSet>();
		Canciones can = new Canciones();
		try{
			stmt = conexion.createStatement();
			switch(operacion){
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