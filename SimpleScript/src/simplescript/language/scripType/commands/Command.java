package simplescript.language.scripType.commands;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import simplescript.language.scripType.Keywords;
import simplescript.language.scripType.exceptions.WrongCommandException;

/**
 * <h1>Environment class - validates and executes a command</h1>
 * <p>
 * </p>
 * 
 * @since 2015-11-27
 * @author Georgi Iliev
 * @version 1.4
 */
public abstract class Command {

    /**
     * <h1><i>execute</i></h1>
     * <p>
     * <p>
     * {@code public abstract void execute()}
     * </p>
     * Performs the neccessary low level opearations(object creation, settings,
     * validations) to execute the built command. </p>
     * 
     * @throws AWTException
     * @throws IOException
     */
    public abstract void execute() throws AWTException, IOException;

    public abstract String toString();

    /**
     * <h1><i>getCommandFormat</i></h1>
     * <p>
     * <p>
     * {@code public abstract String getCommandFormat()}
     * </p>
     * Gets the corresponding format for the command. ( based on Regular
     * Expression ) </p>
     * 
     * @return A string representing the format.
     */
    public abstract String getCommandFormat();

    /**
     * <h1><i>isValidCommand</i></h1>
     * <p>
     * <p>
     * {@code public static boolean isValidCommand(String statement)}
     * </p>
     * Evaluates whether the command is in fact valid or not. </p>
     * 
     * @param statement
     *            - the statement suspected to be invalid user command.
     * @return TRUE if command validation passes, FALSE if it does not.
     * @throws WrongCommandException
     */
    public static boolean isValidCommand(String statement)
	    throws WrongCommandException {
	Field[] fields = Keywords.class.getDeclaredFields();
	for (int i = 0; i < fields.length; i++) {
	    if (statement.equalsIgnoreCase(fields[i].getName()))
		return true;
	}
	return false;
    }
    
    /**
     * <h1><i>hasValidCommandFormat</i></h1>
     * <p>
     * <p>
     * {@code public static boolean hasValidCommandFormat(String testableClassname,String testableSt�tement))}
     * </p>
     * Validates the format of a user input command, in order for it to be
     * processed. </p>
     * 
     * @param testableClassname
     *            - the name of the class desribing the suspected user command.
     * @param testableSt�tement
     *            - the command statement from user source file.
     * @return TRUE if the format is valid, FALSE if it is not.
     */
    public static boolean hasValidCommandFormat(String testableClassname,
	    String testableSt�tement) {
	String testablePattern = "dummyPattern";

	if (testableClassname.equalsIgnoreCase(Keywords.LINE)) {
	    testablePattern = new Line().getCommandFormat();
	} else if (testableClassname.equalsIgnoreCase(Keywords.POINT)) {
	    testablePattern = new Point().getCommandFormat();
	} else if (testableClassname.equalsIgnoreCase(Keywords.TEXT)) {
	    testablePattern = new Text().getCommandFormat();
	} else if (testableClassname.equalsIgnoreCase(Keywords.CLICK)) {
	    testablePattern = new Click().getCommandFormat();
	} else if (testableClassname.equalsIgnoreCase(Keywords.MOVE)) {
	    testablePattern = new Move().getCommandFormat();
	} else if (testableClassname.equalsIgnoreCase(Keywords.OPEN)) {
	    testablePattern = new Open().getCommandFormat();
	} else if (testableClassname.equalsIgnoreCase(Keywords.PRESS)) {
	    testablePattern = new Shortcut().getCommandFormat();
	} else if (testableClassname.equalsIgnoreCase(Keywords.DELAY)) {
	    testablePattern = new Delay().getCommandFormat();
	}

	Pattern pattern = Pattern.compile(testablePattern);

	Matcher matcher = pattern.matcher(testableSt�tement.toUpperCase());

	return matcher.find();
    }
}