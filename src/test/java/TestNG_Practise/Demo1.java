package TestNG_Practise;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class Demo1 {

	@Test
	public void m1() {
		System.out.println("m1");
	}

	@Test
	public void m2() {
		int count = 0;
		System.out.println("m2" + "  "+ count++);
		
		AssertJUnit.assertEquals(100, 200);
	}

	@Test
	public void m3() {
		System.out.println("m3");
	}

	@Test
	public void m4() {
		System.out.println("m4");
	}

	@Test
	public void m5() {
		System.out.println("m5");
	}

}
