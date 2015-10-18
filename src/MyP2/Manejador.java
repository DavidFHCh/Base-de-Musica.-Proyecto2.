package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

/**
* Clase Manejadora de la base de datos.
*/
public class Manejador{

	private static Connection conexion;

	/**
	* Establece una conexion con la base de datos.
	* @param test ingresa false siempre.
	* @return Connection
	* @throws ErrorBaseDeDatos cuando pasa algo con el archivo de la base.
	*/
	public synchronized static Connection abrirConexion(boolean test){
		try{
			if(conexion == null || conexion.isClosed()){
				Class.forName("org.sqlite.JDBC");
				if(test)
                	conexion = DriverManager.getConnection("jdbc:sqlite:./lib/Musica_Prueba.db");
            	else
            		conexion = DriverManager.getConnection("jdbc:sqlite:./lib/Musica.db");	
			}
		}catch(Exception e){
			throw new ErrorBaseDeDatos("No se pudo establecer una conexion.");
		}
		return conexion;
	}

	/**
	* Cierra conexion con la base de datos.
	* @throws ErrorBaseDeDatos cuando pasa algo con el archivo de la base.
	*/	
	public synchronized static void cerrarConexion(){
		try{
			if(conexion == null || conexion.isClosed())
				return;
			conexion.close();
		}catch(Exception e){
			throw new ErrorBaseDeDatos("No se pudo cerrar la conexion con la Base de Datos.");
		}
	}

}