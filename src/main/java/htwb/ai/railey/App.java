package htwb.ai.railey;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.cli.*;

public class App 
{
	public static void main(String[] args )
    {
        Object classname;
        
        try {
			classname = TestRunner.createFromSystemProperty(getClassNameFromConsole(args));
	    	TestRunner.testMethodResult(classname);
		} 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} 
    }
	
	public static String getClassNameFromConsole(String[] args) {
		
		Options options = new Options();
        
        Option jar = new Option("jar", true, "testrunner-1.0-jar-with-dependencies.jar");
        jar.setRequired(true);
        options.addOption(jar);
    	
        Option tested_class = new Option("c", true, "tested class for test runner");
        tested_class.setRequired(true);
        options.addOption(tested_class);
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
            formatter.printHelp("Options for Parse Arguments:", options);
		}
        
        return cmd.getOptionValue("c");	
	}

}
