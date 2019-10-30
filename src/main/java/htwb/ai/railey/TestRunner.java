package htwb.ai.railey;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import htwb.ai.MyTest;

public class TestRunner {
	public static Object createFromSystemProperty(String classname) 
	        throws ClassNotFoundException, InstantiationException, IllegalAccessException, 
	        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, 
	        SecurityException {

		
        //String className = System.getProperty(classname);
        
        
        //System.out.println("in createFromSystemProperty: " + className);
        Class<?> c = Class.forName(classname);
        //System.out.println(c.getDeclaredConstructor().newInstance());
        return c.getDeclaredConstructor().newInstance();
    }
	
	public static ArrayList<Method> getMethode(Object o){
		Method[] methode = o.getClass().getDeclaredMethods();
		return  filterMethods(o.getClass().getDeclaredMethods());
	}
	
	private static ArrayList<Method> filterMethods(Method[] methods) {
		ArrayList<Method> filteredMethods = new ArrayList<Method>();
		for(Method m : methods) {
			if(m.getParameterCount() == 0 &&
					m.getReturnType() == boolean.class &&
					m.getAnnotation(MyTest.class) != null &&
				 	m.getModifiers()== Modifier.PUBLIC) {
				filteredMethods.add(m);
			}
		}
		return  filteredMethods;
	}
	
	public static void test( Object objectToTest, ArrayList<Method> methodsToTest) {
		System.out.println(" ----- TEST RESULTS FOR "+objectToTest.toString()+" ----- ");
		for(Method m : methodsToTest) {
			try {
				System.out.print("Result for '"+ m.getName()+ " : ");
				if((boolean)m.invoke(objectToTest)) {
					System.out.println("passed");
				}
				else {
					System.out.println("failed");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error due to NullpointerException");
			}
		}
	}
}
