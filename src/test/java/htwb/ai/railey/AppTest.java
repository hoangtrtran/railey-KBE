package htwb.ai.railey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import htwb.ai.railey.App;

public class AppTest {
	
    @Test
    public void test_getClassNameFromConsole() throws Exception {
		assertEquals("MyTestClasses.TestClassFromRailey", App.getClassNameFromConsole(new String[]{"-c", "MyTestClasses.TestClassFromRailey"}));
    }
 
    @Test
    public void test_getEmptyClassnameFromConsole() throws Exception {
    	Assertions.assertThrows(Exception.class, () -> {
    		App.getClassNameFromConsole(new String[]{"-c", ""});
    	  });
    }
    
    @Test
    public void test_getClassnameWithoutArgumentsFromConsole() {
    	Assertions.assertThrows(Exception.class, () -> {
    		App.getClassNameFromConsole(new String[]{});
    	  });
    }
    
    @Test
    public void test_getClassnameTwistedFromConsole() {
    	Assertions.assertThrows(Exception.class, () -> {
    		App.getClassNameFromConsole(new String[]{"-c"});
    	  });
    }
    
    @Test
    public void test_getClassnameFromConsole_UnmatchedArgument() {
    	Assertions.assertThrows(Exception.class, () -> {
    		App.getClassNameFromConsole(new String[]{"-d", "hello"});
    	  });
    }
    
    @Test
    public void test_getClassnameFromConsole_UnmatchedArgument2() {
    	Assertions.assertThrows(Exception.class, () -> {
    		App.getClassNameFromConsole(new String[]{"-c", "-d"});
    	  });
    }
    
    @Test
    public void test_getClassnameFromConsole_ArgumentWithoutJava() {
    	Assertions.assertThrows(Exception.class, () -> {
    		App.getClassNameFromConsole(new String[]{"-d"});
    	  });
    }
    
    @Test
    public void test_getClassnameFromConsole_ArgumentWrongJarData() {
    	Assertions.assertThrows(Exception.class, () -> {
    		App.getClassNameFromConsole(new String[]{"Hello", "is", "this", "me", "you", "are", "looking", "for"});
    	  });
    }
    
    @Test
    public void test_getClassnameFromConsole_ArgumentWrong2() {
    	Assertions.assertThrows(Exception.class, () -> {
    		App.getClassNameFromConsole(new String[]{"-e", "-c", "-d"});
    	  });
    }
    
    
    @Test
    public void test_getClassnameFromConsole_OnlyJavaArgument() {
    	Assertions.assertThrows(Exception.class, () -> {
    		App.getClassNameFromConsole(new String[]{"java"});
    	  });
    }
    
    @Test
    public void test_getClassnameFromConsole_IrrelevantArgument() {
    	Assertions.assertThrows(Exception.class, () -> {
    		App.getClassNameFromConsole(new String[]{"-w"});
    	  });
    }
}
