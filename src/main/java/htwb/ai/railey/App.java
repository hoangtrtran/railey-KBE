package htwb.ai.railey;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    try {
    	
    	Object classname = TestRunner.createFromSystemProperty(args[4]);
    	//System.out.println(classname);
    	for(Method m : TestRunner.getMethode(classname)) {
        	//System.out.println(m.getName());
    	}
    	TestRunner.test(classname, TestRunner.getMethode(classname));
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchMethodException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    
    public static String returnsHelloWorld() {
        return "Hello World!";
    }
}
