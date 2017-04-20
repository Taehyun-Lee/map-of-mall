/*
 * The Security class is in charge of keeping track of all the ids and passwords
 */
public class Security {
	//These two arrays of strings are inputs from an IO textFile that contains all the passwords and ids
	private static String [] password;
	private static String [] ID;
	//This is where arrays password and ID are initialized
	public static void initializeIDandPassword()
	{
		IO.openInputFile("Security.txt");
		String line = IO.readLine();
		int numLines = 0;
		
		while(line != null)
		{
			numLines++;
			line = IO.readLine();
		}
		IO.closeInputFile();
		
		String[]IDandPassword = new String[numLines];
		IO.openInputFile("Security.txt");
		ID = new String[numLines];
		password = new String[numLines];
		for(int i = 0; i < numLines; i++)
		{
			IDandPassword[i] = IO.readLine();
			ID[i] = IDandPassword[i].substring(0, IDandPassword[i].indexOf(" "));
			password[i] = IDandPassword[i].substring(IDandPassword[i].indexOf(" ")+1, IDandPassword[i].length());
			System.out.println(ID[i]);
		}
		IO.closeInputFile();
		
		
	}
	//This method checks both the id and the password from the user input to check if the user
	//is authorized to proceed, then returns a boolean according to what it finds.
	public static boolean checkPassword(String id, String p)
	{
		
		boolean output = true;
		for(int i = 0; i<password.length; i++)
		{
			//this line checks if the password is correct for the correct id.
			if(p.equalsIgnoreCase(password[i])&&id.equalsIgnoreCase(ID[i]))
				{
					output = true;
					i = password.length;
				}
			else
				output = false;
		}	
		return output;
	}
	//This method checks the same things as checkPassword, but it also checks if the new password is
	//also a valid password.
	public static boolean getCheckPassword(String id, String p, String np)
	{
		boolean output = false;
		if(approvePassword(np))
		{
			for(int i = 0; i<password.length; i++)
			{
		
				if(password[i].equalsIgnoreCase(p)&&ID[i].equalsIgnoreCase(id))
				{
					output = true;
					i = password.length;
				}
				else
					output = false;
			}
			
		}
		else
			output = false;
		return output;
	}
	//This method checks the same thing as getCheckPassword, but this time, it actually sets the 
	//new password
	//This method was just created to make sure that the mutator portion and the accessor portion remain
	//seperate.
	public static void setPassword(String id, String p, String np)
	{
		for(int i = 0; i<password.length; i++)
		{
			if(password[i].equalsIgnoreCase(p)&&ID[i].equalsIgnoreCase(id))
			{
				password[i] = np;
				i=password.length;
			}
		}
	}
	//This method checks if the new password is usable
	private static boolean approvePassword(String np)
	{
		if(np.indexOf(" ")>0||np.length()<1)
			return false;
		else
			return true;
	}
	//This method saves the configured passwords and ids to the IO textfile.
	public static void saveIDandPassword()
	{
		IO.createOutputFile("Security.txt");
		for(int i = 0; i<password.length; i++)
		{
			IO.println(ID[i]+" "+password[i]);
		}
		IO.closeOutputFile();
	}
}
