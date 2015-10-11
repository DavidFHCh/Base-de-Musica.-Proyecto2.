package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

/**
* Clase Manejadora de la base de datos.
*/
public class Manejador{

	private static Connection conexion;

	public synchronized static Connection abrirConexion(){
		try{
			if(conexion == null || conexion.isClosed()){
				Class.forName("org.sqlite.JDBC");
                conexion = DriverManager.getConnection("jdbc:sqlite:./lib/Musica.db");
			}
		}catch(Exception e){
			throw new ErrorBaseDeDatos("No se pudo establecer una conexion.");
		}
	}

	public synchronized static void cerrarConexion(){
		try{
			if(conexion == null || conexion.isClosed())
				return;
			conexion.close();
		}catch(Exception e){
			throw new ErrorBaseDeDatos("No se pudo cerrar la conexion con la Base de Datos.")
		}
	}

}