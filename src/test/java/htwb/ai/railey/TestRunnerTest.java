package htwb.ai.railey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import htwb.ai.railey.TestRunner;

public class TestRunnerTest {
    
    @Test
    public void test_createFromSystemProperty_classExist1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Object testedClass = TestRunner.createFromSystemProperty("MyTestClasses.TestClassFromRailey");
		assertEquals((String) testedClass.getClass().toString(), "class MyTestClasses.TestClassFromRailey");
    }
    
    @Test
    public void test_createFromSystemProperty_classExist2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Object testedClass = TestRunner.createFromSystemProperty("htwb.ai.railey.App");
		assertEquals((String) testedClass.getClass().toString(), "class htwb.ai.railey.App");
    }
    
    @Test
    public void test_createFromSystemProperty_classExist3() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Object testedClass = TestRunner.createFromSystemProperty("htwb.ai.railey.TestRunner");
		assertEquals((String) testedClass.getClass().toString(), "class htwb.ai.railey.TestRunner");
    }
    
    @Test
    public void test_createFromSystemProperty_classNotExist() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Assertions.assertThrows(ClassNotFoundException.class, () -> {
    		@SuppressWarnings("unused")
			Object testedClass = TestRunner.createFromSystemProperty("MyTestClasses.HelloNewDay");
    	  });
    }
    
    @Test
    public void test_createFromSystemProperty_packageNotExist() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Assertions.assertThrows(ClassNotFoundException.class, () -> {
    		@SuppressWarnings("unused")
			Object testedClass = TestRunner.createFromSystemProperty("WhereAmI.HelloHeoHello");
    	  });
    }
    
    @Test
    public void test_createFromSystemProperty_classWithEmptyString() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Assertions.assertThrows(ClassNotFoundException.class, () -> {
    		@SuppressWarnings("unused")
			Object testedClass = TestRunner.createFromSystemProperty("");
    	  });
    }
    
    @Test
    public void test_GetMethods1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Object testedClass = TestRunner.createFromSystemProperty("MyTestClasses.TestClassFromRailey");
    	ArrayList<Method> methods = TestRunner.getMethods(testedClass);
    	assertEquals(methods.size(), 3);
    	assertEquals(methods.get(0).toString(), "public boolean MyTestClasses.TestClassFromRailey.testMethod_Return_False()");
    	assertEquals(methods.get(1).toString(), "public boolean MyTestClasses.TestClassFromRailey.testMethod_Return_True()");
    	assertEquals(methods.get(2).toString(), "public boolean MyTestClasses.TestClassFromRailey.testMethod_NullPointerException()");
    }
    
    @Test
    public void test_GetMethods2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Object testedClass = TestRunner.createFromSystemProperty("htwb.ai.railey.App");
    	ArrayList<Method> methods = TestRunner.getMethods(testedClass);
    	assertEquals(methods.size(), 0);
    }
    
    @Test
    public void test_GetMethods3() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Object testedClass = TestRunner.createFromSystemProperty("htwb.ai.railey.TestRunner");
    	ArrayList<Method> methods = TestRunner.getMethods(testedClass);
    	assertEquals(methods.size(), 0);
    }
    
    @Test
    public void test_GetMethods4() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	ArrayList<Method> methods = TestRunner.getMethods(new ArrayList<Object>());
    	assertEquals(methods.size(), 0);
    }
    
    @Test
    public void test_GetMethods_NullAlsParameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Assertions.assertThrows(NullPointerException .class, () -> {
    		@SuppressWarnings("unused")
    		ArrayList<Method> methods = TestRunner.getMethods(null);
    	  });
    }
    
    @Test
    public void test_TestMethodResult1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Object testedClass = TestRunner.createFromSystemProperty("MyTestClasses.TestClassFromRailey");
    	ArrayList<String> messages = TestRunner.testMethodResult(testedClass);
    	assertEquals(messages.size(), 3);
    	assertEquals(messages.get(0), "Result for 'testMethod_Return_False' : failed");
    	assertEquals(messages.get(1), "Result for 'testMethod_Return_True' : passed");
    	assertEquals(messages.get(2), "Result for 'testMethod_NullPointerException' : error due to NullPointerException");
    }
    
    @Test
    public void test_TestMethodResult2_UnmatchedRequirement() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Object testedClass = TestRunner.createFromSystemProperty("htwb.ai.railey.App");
    	ArrayList<String> messages = TestRunner.testMethodResult(testedClass);
    	assertEquals(messages.size(), 0);
    }
    
    @Test
    public void test_TestMethodResult3_EmptyStringClass() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		@SuppressWarnings("unused")
    		ArrayList<String> messages = TestRunner.testMethodResult("");
    	  });
    }
    
    @Test
    public void test_TestMethodResult3_NullAlsParameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		@SuppressWarnings("unused")
    		ArrayList<String> messages = TestRunner.testMethodResult(null);
    	  });
    }
}
