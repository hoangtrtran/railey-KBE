package htwb.ai.railey;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {
	public static Object createFromSystemProperty(String classname) 
	        throws ClassNotFoundException, InstantiationException, IllegalAccessException, 
	        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, 
	        SecurityException {

		
        //String className = System.getProperty(classname);
        
        
        //System.out.println("in createFromSystemProperty: " + className);
        Class<?> c = Class.forName(classname);
        System.out.println(c.getDeclaredConstructor().newInstance());
        return c.getDeclaredConstructor().newInstance();
    }
	
	public static Method[] getMethode(Object o){
		Method[] methode = o.getClass().getDeclaredMethods();
		System.out.println(methode[0]);
		return  o.getClass().getDeclaredMethods();
	}
}
