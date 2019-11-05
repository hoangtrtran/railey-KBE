package htwb.ai.railey;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.cli.*;

public class App 
{
	public static void main(String[] args ) throws Exception
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
	
	public static String getClassNameFromConsole(String[] args) throws Exception {
		
		Options options = new Options();

        Option tested_class = new Option("c", true, "tested class for test runner");
        tested_class.setRequired(true);
        options.addOption(tested_class);
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
			cmd = parser.parse(options, args);
			
			if (cmd.getOptionValue("c").isEmpty() || String.valueOf(cmd.getOptionValue("c").charAt(0)).equals("-"))
				throw new IllegalArgumentException("Error: Arguments in the console are wrong. "
						+ "Please put following argument in console: java -jar testrunner-1.0-jar-with-dependencies.jar -c classname");
		} catch (ParseException e) {
			formatter.printHelp("Options for Parse Arguments:", options);
			System.out.println("Please put following argument in console: java -jar testrunner-1.0-jar-with-dependencies.jar -c classname");
		}
        
        return cmd.getOptionValue("c");	
	}

}
