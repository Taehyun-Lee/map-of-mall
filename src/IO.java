import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class IO {
	
		//WRTING TO A FILE
	
		private static PrintWriter fileOut;
		
		//Create a new File, stored in the current folder
		
		public static void createOutputFile(String fileName)
		{
			createOutputFile(fileName, false);
			try{
				fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			}
			catch(IOException e){
				System.out.println("***Cannot create file: "+ fileName + " ***");
			}
		}
		
		//Adds to the existing file IF our append boolean is set to TRUE and rewrites the file
		//if set to FALSE
		
		public static void createOutputFile(String fileName, boolean append)
		{
			try{
				fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName, append)));
			}
			catch(IOException e){
				System.out.println("***Cannot create file: "+ fileName + " ***");
			}
		}
		
		public static void print(String text)
		{
			fileOut.print(text);
		}
		
		public static void println(String text)
		{
			fileOut.println(text);
		}
		//MUST call this method when we are done writing to a file IN ORDER TO SAVE IT
		public static void closeOutputFile()
		{
			fileOut.close();
		}
		
		//READUBG FROM A FILE
		
		private static BufferedReader fileIn;
		
		public static void openInputFile(String fileName)
		{
			try{
				fileIn = new BufferedReader(new FileReader(fileName));
			}
			catch(FileNotFoundException e)
			{
				System.out.println("*** Cannot open: " + fileName + " ***");
			}
		}
		
		public static String readLine()
		{
			try{
				return fileIn.readLine();
			}
			catch(IOException e){}
			return null;
		}
		
		public static void closeInputFile()
		{
			try{
				fileIn.close();
			}
			catch(IOException e){}
		}
		
}