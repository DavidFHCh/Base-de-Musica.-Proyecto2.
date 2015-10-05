package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

public class Manejador{

	public Manejador(){}

	public ResultSet buscar(int anio, int anioIni, int anioFin, String colab, String artist, String disquera,String canciones, Connection c){
		return null;
	}

	public ResultSet elimina(){
		return null;
	}

	public ResultSet agrega(){
		return null;
	}

	private boolean esNula(String aEvaluar){
		return aEvaluar == null;
	}

	private String select1(String column,String table,String columnRel, String tableRel, String idPrincipal ,String idSecundario){
		return "SELECT " + column + " FROM " + table + " JOIN (SELECT " + columnRel + " FROM " + tableRel + ") ON " + idPrincipal + " = " + idSecundario + ";";
	}

	private String select1Esp(String column,String table,String columnRel, String tableRel, String idPrincipal ,String idSecundario,int idEspecifico, String idFiltro){
		return "SELECT " + column + " FROM " + table + " JOIN (SELECT " + columnRel + " FROM " + tableRel +" WHERE " + idEspecifico + " = " + idFiltro + ") ON " + idPrincipal + " = " + idSecundario +";";
	}

}