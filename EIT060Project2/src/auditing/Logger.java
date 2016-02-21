package auditing;

import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Logging class
 */
public class Logger 
{
	private PrintStream writer;

	public Logger() 
	{
		Date dt = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd_HH-mm-ss.SSS");
		String fileName = "AuditLog_" + ft.format(dt) + ".txt";
		try 
		{
			writer = new PrintStream(fileName);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	/*
	 * Writes to file
	 * @param message Message written to file
	 */
	public void writeLog(String message) 
	{
		Date dt = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd_HH:mm:ss.SSS");
		String dateString = ft.format(dt);

		writer.println(dateString + " " + message);
		writer.flush();
	}
}