package demos;

/**
 * A class to store information about a Student Used in module 4 of the UC San
 * Diego MOOC Object Oriented Programming in Java
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Star Dust
 * Date: May 17, 2019
 */
public class Student extends Person {
	public Student(String name) {
		super(name);
	}

	public boolean isAsleep(int hr) // override
	{
		return 2 < hr && 8 > hr;
	}
	
	public int getID(){
		return 1;		
		
	}

	public static void main(String[] args) {
		Person p;
		p = new Student("Sally");
		System.out.println(p.getID());
		p.status(1);
	}
}
