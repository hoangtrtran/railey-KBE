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
	
	public static ArrayList<Method> getMethods(Object o){
		if (o == null)
			throw new NullPointerException("Null is not allowed.");
		
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
	
	public static ArrayList<String> testMethodResult(Object testedClass) {
		if (testedClass == "" || testedClass == null)
			throw new IllegalArgumentException("Null or empty string is not allowed");
		
		ArrayList<Method> methodsToTest = TestRunner.getMethods(testedClass);
		ArrayList<String> messages = new ArrayList<>();
		String classname = (String) testedClass.getClass().toString();
		classname = classname.replace("class MyTestClasses.", "");
		
		System.out.println(" ----- TEST RESULTS FOR " + classname + " ----- ");
		for(Method m : methodsToTest) {
			String message = "Result for '"+ m.getName()+ "' : ";
			try {	
				if((boolean)m.invoke(testedClass))
					message += "passed";
				else
					message += "failed";
				
				messages.add(message);
				System.out.println(message);
			} 
			catch (Exception e) {
				message += "error due to NullPointerException";
				messages.add(message);
				System.out.println(message);
			}
		}
		return messages;
	}
}
