package MyP2.test;

import MyP2.DisquerasSalida;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class TestDisquerasSalida{

	private String prueba;
	private String prueba2;
	private String[] s = {"a","b","c","d","e","f","g","h","i","j","k",""};

	public TestDisquerasSalida(){
		prueba = "";
		for(int i = 0; i < 10000; i++)
			prueba2 += s[(int)(Math.random()*s.length)];
	}

	@Test public void testGetDisquera(){
		DisquerasSalida as = new DisquerasSalida(prueba2);
		Assert.assertTrue(prueba2.equals(as.getDisquera()));
		Assert.assertFalse("z".equals(as.getDisquera()));
	}

	@Test public void testToString(){
		DisquerasSalida as = new DisquerasSalida(prueba2);
		Assert.assertTrue(prueba2.equals(as.toString()));
		Assert.assertFalse("z".equals(as.toString()));
	}
}