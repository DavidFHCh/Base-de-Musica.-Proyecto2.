package MyP2.test;

import MyP2.CancionesSalida;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class TestCancionesSalida{

	private String prueba;
	private String prueba2;
	private String prueba3;
	private String[] s = {"a","b","c","d","e","f","g","h","i","j","k",""};

	public TestCancionesSalida(){
		prueba = "";
		for(int i = 0; i < 10000; i++){
			prueba2 += s[(int)(Math.random()*s.length)];
			prueba3 += s[(int)(Math.random()*s.length)];
		}
	}

	@Test public void testGetCancion(){
		CancionesSalida as = new CancionesSalida(prueba,prueba2,prueba3);
		Assert.assertTrue(prueba.equals(as.getCancion()));
		Assert.assertFalse("z".equals(as.getCancion()));
	}

	@Test public void testGetAnio(){
		CancionesSalida as = new CancionesSalida(prueba,prueba2,prueba3);
		Assert.assertTrue(prueba2.equals(as.getAnio()));
		Assert.assertFalse("z".equals(as.getAnio()));
	}

	@Test public void testGetDuracion(){
		CancionesSalida as = new CancionesSalida(prueba,prueba2,prueba3);
		Assert.assertTrue(prueba3.equals(as.getDuracion()));
		Assert.assertFalse("z".equals(as.getDuracion()));
	}

	@Test public void testToString(){
		CancionesSalida as = new CancionesSalida(prueba,prueba2,prueba3);
		Assert.assertTrue((prueba+", "+prueba2+", "+prueba3).equals(as.toString()));
		Assert.assertFalse("z".equals(as.toString()));
	}
}