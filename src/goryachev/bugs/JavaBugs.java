// Copyright © 2018 Andy Goryachev <andy@goryachev.com>
package goryachev.bugs;
import goryachev.bugs.fx.DualFocus;


/**
 * Java Bugs.
 */
public class JavaBugs
{
	public static void main(String[] args)
	{
		DualFocus.launch(DualFocus.class, args);
		
		System.out.println("OK");
		System.exit(0);
	}
}
