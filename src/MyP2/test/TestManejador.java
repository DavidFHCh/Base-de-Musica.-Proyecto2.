package MyP2.test;

import MyP2.Manejador;
import MyP2.ExcepcionBusquedaInvalida;
import org.junit.Assert;
import org.junit.Test;
import org.sqlite.*;
import java.util.*;
import java.sql.*;


/**
 * Clase para pruebas unitarias de la clase Manejador.java
 */
public class TestManejador{

	private Manejador m; 
	private Connection c;

	/**
	* Crea un objeto de tipo Manejador para las pruebas.
	*/
	public TestManejador(){
		m = new Manejador();
		try{
		c = DriverManager.getConnection("jdbc:sqlite:Musica_Prueba.db");
		}catch(Exception e){}
	}

	/**
     * Prueba unitaria para {@link Manejador#esNula}.
     */
	@Test public void testBusca(){
		LinkedList<ResultSet> l = new LinkedList<ResultSet>();
		ResultSet rs = null;
		try{
			l = m.buscar(1990,1900,1900,null,null,null,null,c);
			Assert.fail();
		}catch(ExcepcionBusquedaInvalida ebi){}
		try{
			l = m.buscar(2015,-1,-1,"TODO","TODO","TODO","TODO",c);
			rs = l.removeFirst();
			while(rs.next()){
				Assert.assertTrue(rs.getInt("año") == 2015);
			}
			l = m.buscar(-1,2014,2015,"TODO","TODO","TODO","TODO",c);
			rs = l.removeFirst();
			while(rs.next()){
				Assert.assertTrue(rs.getInt("año") == 2015 || rs.getInt("año") == 2014);
			}
			l = m.buscar(-1,-1,-1,"M0","TODO","TODO","TODO",c);
			rs = l.removeFirst();
			while(rs.next()){
				Assert.assertTrue(rs.getString("cancion") == "Lean On");
			}
		}catch(SQLException se){}
	}
	
}
