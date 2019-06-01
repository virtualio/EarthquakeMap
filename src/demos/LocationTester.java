package demos;

/**
 * A class to illustrate class design and use. Used in module 2 of the UC San
 * Diego MOOC Object Oriented Programming in Java
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Star Dust
 * Date: May 17, 2019
 *
 */
public class LocationTester {
	public static void main(String[] args) {
		SimpleLocation ucsd = new SimpleLocation(32.9, -117.2);
		SimpleLocation lima = new SimpleLocation(-12.0, -77.0);

		// latitude = -12.04;
		System.out.println(ucsd.distance(lima));
	}

}
