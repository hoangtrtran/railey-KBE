package htwb.ai.railey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import htwb.ai.railey.App;

public class AppTest {
    @Test
    public void test_getClassNameFromConsole() {
		assertEquals("MyTestClasses.TestClassFromRailey", App.getClassNameFromConsole(new String[]{"java","-jar", "testrunner-1.0-jar-with-dependencies-jar", 
				"-c", "MyTestClasses.TestClassFromRailey"}));
    }

    @Test
    public void test_getEmptyClassnameFromConsole() {
		assertEquals("", App.getClassNameFromConsole(new String[]{"java","-jar", "testrunner-1.0-jar-with-dependencies-jar", 
				"-c", ""}));
    }
    
    @Test
    public void test_getClassnameWithoutArgumentsFromConsole() {
		assertEquals("", App.getClassNameFromConsole(new String[]{"java","-jar", "testrunner-1.0-jar-with-dependencies-jar", 
				"", ""}));
    }

    @Test
    public void test_getClassnameTwistedFromConsole() {
		assertEquals("", App.getClassNameFromConsole(new String[]{"java","-jar", "testrunner-1.0-jar-with-dependencies-jar", 
				"MyTestClasses.TestClassFromRailey", "-c"}));
    }
    
}
