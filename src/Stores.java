/*
 * This is the Stores class, which holds all the essential information needed to run all the 
 * GUIs in the Summative project
 */
public class Stores {
	//These are all the attributes of a Stores instance
	private String name;
	private String codeNum;
	private String storeType;
	private int numRating;
	private double avgRating;
	private String webURL;
	private String generalInfo;
	private boolean jobAvailability;
	private String posAvailable;
	
	//These are the constructor methods
	public Stores()
	{
		this(" ", " ", 0,0,"", " ", " ", false, " ");
	}
	public Stores(String n,String s, int num, double a,String c, String w, String g, boolean j, String p)
	{
		name = n;
		storeType = s;
		codeNum = c;
		numRating = num;
		avgRating = a;
		webURL = w;
		generalInfo = g;
		jobAvailability = j;
		if(jobAvailability)
			posAvailable = p;

	}
	//These are all the mutator methods of Stores
	public void setCodeNum(String n)
	{
		codeNum = n;
	}
	public void setName(String n)
	{
		name = n;
	}
	public void setWebURL(String w)
	{
		String url = w;
		if(w.indexOf(" ")==0&&w.length()>0)
			url = w.substring(1, w.length());
			
		webURL = url;
	}
	public void setStoreType(String s)
	{
		storeType = s;
	}
	public void setGeneralInfo(String g)
	{
		generalInfo = g;
	}
	public void setJobAvailability(boolean j)
	{
		jobAvailability = j;
	}
	public void setPosAvailable(String p)
	{
		if(jobAvailability)
			posAvailable = p;
	}
	public void setAvgRating(double r, int n)
	{
		avgRating = r;
		numRating = n;
	}
	public void setAvgRating(int r)
	{
		numRating++;
		avgRating = ((avgRating*(numRating-1))+r)/numRating;
	}
	//These are all the accessor methods of Stores
	public String getName()
	{
		return name;
	}
	
	public String getWebURL()
	{
		String url = webURL;
		if(webURL.indexOf(" ")==0&&webURL.length()>0)
			url = webURL.substring(1, webURL.length());
			
		return url;
	}
	public String getStoreType()
	{
		return storeType;
	}
	public String getGeneralInfo()
	{
		return generalInfo;
	}
	public boolean getJobAvailability()
	{
		return jobAvailability;
	}
	public String getPosAvailable()
	{
		return posAvailable;
	}
	public String getPositionsAvailable()
	{
		String output = posAvailable.replace(",", "\n");
		return output;
	}
	public String getCodeNum()
	{
		return codeNum;
	}
	public int getNumRating()
	{
		return numRating;
	}
	public double getAvgRating()
	{
		return avgRating;
	}
	//This resets the Stores instance
	public void returnToNull()
	{
		name = " ";
		webURL = " ";
		numRating = 0;
		avgRating = 0;
		storeType = " ";
		generalInfo = " ";
		jobAvailability = false;
		posAvailable = " ";
	}
	//This returns all information about that particular Stores instance
	public String toString()
	{
		String trueOrFalse = "0", output;
		if(jobAvailability)
			trueOrFalse = "1";
		else
			posAvailable = "";
		output = codeNum+"@"+name+"#"+storeType+"$"+numRating+"%"+avgRating+
				"^"+webURL+"&"+generalInfo+"*"+trueOrFalse+"`"+posAvailable;
		return output;
	}
}