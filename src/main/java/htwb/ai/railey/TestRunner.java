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
        Class<?> c = Class.forName(classname);
        return c.getDeclaredConstructor().newInstance();
    }
	
	public static ArrayList<Method> getMethode(Object o){
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
	
	public static void testMethodResult(Object testedClass, ArrayList<Method> methodsToTest) {
		
		String classname = (String) testedClass.getClass().toString();
		classname = classname.replace("class MyTestClasses.", "");
		
		System.out.println(" ----- TEST RESULTS FOR " + classname + " ----- ");
		for(Method m : methodsToTest) {
			try {
				System.out.print("Result for '"+ m.getName()+ "' : ");
				if((boolean)m.invoke(testedClass)) {
					System.out.println("passed");
				}
				else {
					System.out.println("failed");
				}
			} 
			catch (Exception e) {
				System.out.println("error due to NullPointerException");
			}
		}
	}
}
