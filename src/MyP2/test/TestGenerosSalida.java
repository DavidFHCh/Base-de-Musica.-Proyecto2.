package MyP2.test;

import MyP2.GenerosSalida;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class TestGenerosSalida{

	private String prueba;
	private String prueba2;
	private String[] s = {"a","b","c","d","e","f","g","h","i","j","k",""};

	public TestGenerosSalida(){
		prueba = "";
		for(int i = 0; i < 10000; i++)
			prueba2 += s[(int)(Math.random()*s.length)];
	}

	@Test public void testGetGenero(){
		GenerosSalida as = new GenerosSalida(prueba2);
		Assert.assertTrue(prueba2.equals(as.getGenero()));
		Assert.assertFalse("z".equals(as.getGenero()));
	}

	@Test public void testToString(){
		GenerosSalida as = new GenerosSalida(prueba2);
		Assert.assertTrue(prueba2.equals(as.toString()));
		Assert.assertFalse("z".equals(as.toString()));
	}
}