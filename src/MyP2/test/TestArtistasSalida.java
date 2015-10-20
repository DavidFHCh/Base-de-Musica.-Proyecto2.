package MyP2.test;

import MyP2.ArtistasSalida;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class TestArtistasSalida{

	private String prueba;
	private String prueba2;
	private String[] s = {"a","b","c","d","e","f","g","h","i","j","k",""};

	public TestArtistasSalida(){
		prueba = "";
		for(int i = 0; i < 10000; i++)
			prueba2 += s[(int)(Math.random()*s.length)];
	}

	@Test public void testGetArtista(){
		ArtistasSalida as = new ArtistasSalida(prueba2);
		Assert.assertTrue(prueba2.equals(as.getArtista()));
		Assert.assertFalse("z".equals(as.getArtista()));
	}

	@Test public void testToString(){
		ArtistasSalida as = new ArtistasSalida(prueba2);
		Assert.assertTrue(prueba2.equals(as.toString()));
		Assert.assertFalse("z".equals(as.toString()));
	}
}