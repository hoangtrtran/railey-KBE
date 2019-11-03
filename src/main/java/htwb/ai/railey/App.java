package htwb.ai.railey;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.cli.*;

public class App 
{
	public static void main( String[] args )
    {
    	Options options = new Options();
        
        Option jar = new Option("jar", true, "testrunner-1.0-jar-with-dependencies.jar");
        jar.setRequired(true);
        options.addOption(jar);
    	
        Option tested_class = new Option("c", true, "tested class for test runner");
        tested_class.setRequired(true);
        options.addOption(tested_class);
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        Object classname;
        
        try {
        	cmd = parser.parse(options, args);
			classname = TestRunner.createFromSystemProperty(cmd.getOptionValue("c"));

	    	TestRunner.testMethodResult(classname, TestRunner.getMethode(classname));
			
			
		} 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} 
        catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }
    }
    
    public static String returnsHelloWorld() {
        return "Hello World!";
    }
}
