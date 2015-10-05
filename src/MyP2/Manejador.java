package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

/**
* Clase Manejadora de la base de datos.
*/
public class Manejador{

	private String[] ids = {"id","id_cancion","id_genero","id_disquera","id_artista_grupo","id_artistas"};
	private String[] tablas = {"Artistas","Generos","Disqueras","Canciones","Generos_Cancion","Disqueras_Cancion","Canciones_Artistas","Colaboraciones_Canciones"};
	private String[] columnas = {"cancion","año","duracion","generos","Recod_Label","artista"};

	/**
	* Constructor vacio.
	*/
	public Manejador(){}

	/**
	* Busca en la base de datos.
	* @param anio - Parametros para la busqueda en la base de datos. 
	* @param anioIni - Parametros para la busqueda en la base de datos.
	* @param anioFin - Parametros para la busqueda en la base de datos.
	* @param colab - Parametros para la busqueda en la base de datos.
	* @param artista - Parametros para la busqueda en la base de datos.
	* @param disquera - Parametros para la busqueda en la base de datos.
	* @param canciones - Parametros para la busqueda en la base de datos.
	* @param c - coneccion a la base de datos.
	* @return ResultSet 
	*/
	public ResultSet buscar(int anio, int anioIni, int anioFin, String colab, String artist, String disquera,String canciones, Connection c){
		if(anio == -1 && anioIni == -1 && anioFin == -1 
			&& colab == null && artist == null && disquera == null && canciones == null)
			throw new ExcepcionBusquedaInvalida("No me has dado ningun parametro.");
		if(anio != -1 && anioIni != -1 && anioFin != -1)
			throw new ExcepcionBusquedaInvalida("Solo puedo bucar en un año especifico o en un intrevalo de años, no en ambos.");
		Statement s = c.createStatement();
		s.setQueryTimeout(30);
		//falta mucho codigo :(
		return null;

	}

	public ResultSet elimina(){
		return null;
	}

	public ResultSet agrega(){
		return null;
	}

	private String select1(String column,String table,String columnRel, String tableRel, String idPrincipal ,String idSecundario){
		return "SELECT " + column + " FROM " + table + " JOIN (SELECT " + columnRel + " FROM " + tableRel + ") ON " + idPrincipal + " = " + idSecundario + ";";
	}

	private String select1Esp(String column,String table,String columnRel, String tableRel, String idPrincipal ,String idSecundario,int idEspecifico, String idFiltro){
		return "SELECT " + column + " FROM " + table + " JOIN (SELECT " + columnRel + " FROM " + tableRel +" WHERE " + idEspecifico + " = " + idFiltro + ") ON " + idPrincipal + " = " + idSecundario +";";
	}

	private String simpleSelect(String column,String table ,String id1,String id2){
		return "SELECT " + column + " FROM " + table + " WHERE " + id1 + " = " + id2 + ";"; 
	}

	private String simpleSelectEntre(String column,String table ,String column1, String a1, String a2){
		return "SELECT " + column + " FROM " + table + " WHERE " + column1 + " BETWEEN " + a1 + " AND " + a2 +";";
	}

	private String likeSelect(String column,String table ,String id1,String id2){
		return "SELECT " + column + " FROM " + table + " WHERE " + "LCASE(" + id1 + ") LIKE " + id2 + ";";
	}

	private String selectTriple(){
		return null;
	}

//Los separo para mejor legibilidad.

	//1
	private ResultSet buscarAnio(int anio,Statement s){
		ResultSet rs = s.executeQuery(simpleSelect("*",tablas[3],Integer.toString(anio),columnas[1]));
		return rs;
	}

	//2
	private ResultSet buscarEntreAnios(int ini, int fin, Statement s){
		ResultSet rs = s.executeQuery(simpleSelectEntre("*",tablas[3],columnas[1],Integer.toString(ini),Integer.toString(fin)));
		return rs;
	}

	//3
	private ResultSet buscarColab(String colab, Statement s){
		ResultSet rs = s.executeQuery(likeSelect("*",tablas[0],columnas[5],"%"+toLowerCase(colab)+"%"));
		int colabId = 0;
		try{
			colabId = rs.getInt("id");
		}catch(Exception e){
			throw new ExcepcionBusquedaInvalida();
		}
		rs = s.executeQuery(select1Esp("*",tablas[3],ids[1],tablas[7],tablas[3]+"."+ids[0],ids[1],Integer.toString(colabId),ids[4]));
		return rs;
	}
}