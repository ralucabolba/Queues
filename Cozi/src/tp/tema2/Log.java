package tp.tema2;
import java.io.*;
public class Log 
{
	public void append(String sir)
	{
		try
		{
			  FileWriter fstream = new FileWriter("Log.txt",true);
			  BufferedWriter out = new BufferedWriter(fstream);
			  
			  out.write(sir);
			  out.newLine();
			  out.close();
		}
		catch (Exception e)
		{
			  System.err.println("Error: " + e.getMessage());
		}
	}
}
