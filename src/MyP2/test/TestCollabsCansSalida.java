package MyP2.test;

import MyP2.CollabsCansSalida;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class TestCollabsCansSalida{

	private String prueba;
	private String prueba2;
	private String prueba3;
	private String prueba4;
	private String[] s = {"a","b","c","d","e","f","g","h","i","j","k",""};

	public TestCollabsCansSalida(){
		prueba = "";
		for(int i = 0; i < 10000; i++){
			prueba2 += s[(int)(Math.random()*s.length)];
			prueba3 += s[(int)(Math.random()*s.length)];
			prueba4 += s[(int)(Math.random()*s.length)];
		}
	}

	@Test public void testGetCancion(){
		CollabsCansSalida as = new CollabsCansSalida(prueba,prueba2,prueba3,prueba4);
		Assert.assertTrue(prueba.equals(as.getCancion()));
		Assert.assertFalse("z".equals(as.getCancion()));
	}

	@Test public void testGetAnio(){
		CollabsCansSalida as = new CollabsCansSalida(prueba,prueba2,prueba3,prueba4);
		Assert.assertTrue(prueba2.equals(as.getAnio()));
		Assert.assertFalse("z".equals(as.getAnio()));
	}

	@Test public void testGetDuracion(){
		CollabsCansSalida as = new CollabsCansSalida(prueba,prueba2,prueba3,prueba4);
		Assert.assertTrue(prueba3.equals(as.getDuracion()));
		Assert.assertFalse("z".equals(as.getDuracion()));
	}

	@Test public void testGetArtista(){
		CollabsCansSalida as = new CollabsCansSalida(prueba,prueba2,prueba3,prueba4);
		Assert.assertTrue(prueba4.equals(as.getArtista()));
		Assert.assertFalse("z".equals(as.getArtista()));
	}

	@Test public void testToString(){
		CollabsCansSalida as = new CollabsCansSalida(prueba,prueba2,prueba3,prueba4);
		Assert.assertTrue((prueba+", "+prueba2+", "+prueba3+", "+prueba4).equals(as.toString()));
		Assert.assertFalse("z".equals(as.toString()));
	}
}