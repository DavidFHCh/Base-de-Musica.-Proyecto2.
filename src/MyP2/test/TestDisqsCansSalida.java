package MyP2.test;

import MyP2.DisqsCansSalida;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class TestDisqsCansSalida{

	private String prueba;
	private String prueba2;
	private String prueba3;
	private String prueba4;
	private String[] s = {"a","b","c","d","e","f","g","h","i","j","k",""};

	public TestDisqsCansSalida(){
		prueba = "";
		for(int i = 0; i < 10000; i++){
			prueba2 += s[(int)(Math.random()*s.length)];
			prueba3 += s[(int)(Math.random()*s.length)];
			prueba4 += s[(int)(Math.random()*s.length)];
		}
	}

	@Test public void testGetCancion(){
		DisqsCansSalida as = new DisqsCansSalida(prueba,prueba2,prueba3,prueba4);
		Assert.assertTrue(prueba.equals(as.getCancion()));
		Assert.assertFalse("z".equals(as.getCancion()));
	}

	@Test public void testGetAnio(){
		DisqsCansSalida as = new DisqsCansSalida(prueba,prueba2,prueba3,prueba4);
		Assert.assertTrue(prueba2.equals(as.getAnio()));
		Assert.assertFalse("z".equals(as.getAnio()));
	}

	@Test public void testGetDuracion(){
		DisqsCansSalida as = new DisqsCansSalida(prueba,prueba2,prueba3,prueba4);
		Assert.assertTrue(prueba3.equals(as.getDuracion()));
		Assert.assertFalse("z".equals(as.getDuracion()));
	}

	@Test public void testGetDisquera(){
		DisqsCansSalida as = new DisqsCansSalida(prueba,prueba2,prueba3,prueba4);
		Assert.assertTrue(prueba4.equals(as.getDisquera()));
		Assert.assertFalse("z".equals(as.getDisquera()));
	}

	@Test public void testToString(){
		DisqsCansSalida as = new DisqsCansSalida(prueba,prueba2,prueba3,prueba4);
		Assert.assertTrue((prueba+", "+prueba2+", "+prueba3+", "+prueba4).equals(as.toString()));
		Assert.assertFalse("z".equals(as.toString()));
	}
}