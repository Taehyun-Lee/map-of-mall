/*
 * The initializeStores method is the method created to make sure that all the classes within the
 * Summative project is able to access the array of Stores created from the IO file.
 * The user can get array of Stores or save an array of Stores that has been modified.
 */
public class initializeStores {
	public static Stores[] initStores()
	{
		Stores[] allStores = new Stores[21];
		//Here, allStores object is initialized from an input from an IO textfile.
		IO.openInputFile("StoresInfo.txt");
		String line = IO.readLine();
		for(int i = 0; i<allStores.length; i++)
		{
			allStores[i] = new Stores(getTheName(line), getTheStoreType(line), getTheNumRating(line),  getTheAvgRating(line), getTheCodeNum(line), getTheWebURL(line), 
			getTheGeneralInfo(line), getTheJobAvailable(line), getThePosition(line));
			line = IO.readLine();
		}
		IO.closeInputFile();
		return allStores;
	}
	//These methods are there to make the extraction of each component of the one line of text cleaner.
	public static String getTheCodeNum(String l)
	{
		String codeNum = l.substring(0, l.indexOf("@"));
		return codeNum;
	}
	public static String getTheName(String l)
	{
		String name = l.substring(l.indexOf("@")+1, l.indexOf("#"));
		return name;
	}
	public static String getTheStoreType(String l)
	{
		String store = l.substring(l.indexOf("#")+1, l.indexOf("$"));
		return store;
	}
	public static int getTheNumRating(String l)
	{
		String numRate = l.substring(l.indexOf("$")+1, l.indexOf("%"));
		int numRating = Integer.parseInt(numRate);
		return numRating;
	}
	public static double getTheAvgRating(String l)
	{
		String avgRate = l.substring(l.indexOf("%")+1, l.indexOf("^"));
		double avgRating = Double.parseDouble(avgRate);
		return avgRating;
	}
	public static String getTheWebURL(String l)
	{
		String webURL = l.substring(l.indexOf("^")+1, l.indexOf("&"));
		return webURL;
	}
	public static String getTheGeneralInfo(String l)
	{
		String GI = l.substring(l.indexOf("&")+1, l.indexOf("*"));
		return GI;
	}
	public static boolean getTheJobAvailable(String l)
	{
		String GI = l.substring(l.indexOf("*")+1, l.indexOf("`"));
		int numBoolean = Integer.parseInt(GI);
		if(numBoolean==1)
			{
				return true;
			}
		else
			{
				return false;
			}
		
	}
	public static String getThePosition(String l)
	{
		String GP = l.substring(l.indexOf("`")+1, l.length());
		return GP;
	}
	//This method takes in an array of Stores, modifies the IO text file and saves it.
	public static void saveChanges(Stores [] allStores)
	{
		IO.createOutputFile("StoresInfo.txt");
		for(int i = 0; i<allStores.length; i++)
		{
			IO.println(allStores[i].toString());
		}
		IO.closeOutputFile();
	}
	//This method is used to further compact the MainGUI by finding the number of stores with
	//jobs needed.
	public static int getNumJobs()
	{
		Stores[] allStores = initializeStores.initStores();
		int numJobs = 0;
		for(int i = 0; i<allStores.length; i++)
		{
			if(allStores[i].getJobAvailability())
			{
				numJobs++;
			}
		}
		return numJobs;
	}
}
