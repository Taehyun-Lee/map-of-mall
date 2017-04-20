/*
 * This is the StoreInfoGUI that shows all the store information.
 */
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
public class StoreInfoGUI  extends JFrame implements ActionListener{
	//These are all the components of StoreInfoGUI.
	private JLabel storeName, storeGeneral, storeRating, storeRate, storeType;
	private JButton rateStore;
	private String theCodeNumber;
	public StoreInfoGUI(String l) throws IOException
	{
		String theStoreName = "", theStoreGeneralInfo = "", codeNumber = "", theStoreType = "", theBackGround = "";
		double avgRating = 0;
		Stores[] allStores = initializeStores.initStores();
		//Here, all the necessary information is extracted from the IO textfile that relates to this
		//specific instance, which is found using the store name.
		for(int i = 0; i<allStores.length; i++)
		{
			if(allStores[i].getName().equalsIgnoreCase(l))
				{
					codeNumber = allStores[i].getCodeNum();
					theStoreName = allStores[i].getName();
					theStoreGeneralInfo = allStores[i].getGeneralInfo();
					avgRating = allStores[i].getAvgRating();
					
					if(allStores[i].getStoreType().equalsIgnoreCase("Cloth"))
						{
							theStoreType = "IMG/Type0.png";
							theBackGround = "IMG/StoreInfoGUI BackGround 0.png";
						}
					else if(allStores[i].getStoreType().equalsIgnoreCase("Food"))
						{
							theStoreType = "IMG/Type1.png";
							theBackGround = "IMG/StoreInfoGUI BackGround 1.png";
						}
					else if(allStores[i].getStoreType().equalsIgnoreCase("General"))
						{
							theStoreType = "IMG/Type2.png";
							theBackGround = "IMG/StoreInfoGUI BackGround 2.png";
						}
					else if(allStores[i].getStoreType().equalsIgnoreCase("Tech"))
						{
							theStoreType = "IMG/Type3.png";
							theBackGround = "IMG/StoreInfoGUI BackGround 3.png";
						}
				}
		}
		theCodeNumber = codeNumber;
		this.setSize(660,276);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle(codeNumber+" "+theStoreName);
		
		try
		{
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(theBackGround)))));
		} 
		catch(IOException e)
		{
			
		}
		
		Container c = getContentPane();
		
		String avgRate = avgRating+"", avgRated ="";
		if(avgRate.indexOf(".")<0)
		{
			avgRated = avgRate;
		}
		else
		{
			avgRated = avgRate.substring(0, avgRate.indexOf(".")+2);
		}
		storeRate = new JLabel("Make and Submit Rating");
		storeName = new JLabel(theStoreName);
		storeGeneral = new JLabel(theStoreGeneralInfo);
		storeRating = new JLabel("Rating: "+avgRated);
		ImageIcon type = new ImageIcon(theStoreType);
		ImageIcon theRateButton = new ImageIcon("IMG/RateButton.png");
		storeType = new JLabel(type);
		rateStore = new JButton(theRateButton);
		
		c.add(storeRate);
		c.add(storeName);
		c.add(storeGeneral);
		c.add(storeRating);
		c.add(storeType);
		c.add(rateStore);
		
		storeRate.setBounds(350, 172, 200, 27);
		storeName.setBounds(14, 28, 500, 40);
		storeGeneral.setBounds(67, 71, 400, 80);
		storeRating.setBounds(67, 172, 100, 20);
		storeType.setBounds(590, 10, 61, 61);
		rateStore.setBounds(552, 165, 44, 44);
		
		storeRate.setFont(new Font("Arial", Font.PLAIN, 17));
		storeName.setFont(new Font("Arial", Font.BOLD, 30));
		storeGeneral.setFont(new Font("Arial", Font.PLAIN, 15));
		storeRating.setFont(new Font("Arial", Font.PLAIN, 17));
		
		rateStore.addActionListener(this);
		
		
		
		c.setBackground(new Color(206,224,240));
		this.setVisible(true);
	}
	//This is the actionPerformed method. In this method, a JOptionPane takes in user input and
	//uses it to change the average rating of the selected store.
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==rateStore)
		{
			Stores[] allStores = initializeStores.initStores();
			for(int i = 0; i<allStores.length; i++)
			{
				//This line searches for the store that is selected
				if(allStores[i].getCodeNum().equalsIgnoreCase(theCodeNumber))
				{
					int rate = 0;
					boolean repeat = true;
					while(repeat)
					{
						//This try catch was found in DirectorGUI from Comp Sci resources.
						try
						{
							rate = Integer.parseInt(JOptionPane.showInputDialog("Enter a number between 1 and 5"));
						}
						catch (NumberFormatException ex)
						{
							
						}
						//This statement will check if the user input that is an integer is valid
						if(rate>0&&rate<6)
						{
							repeat = false;
						}
					}
					allStores[i].setAvgRating(rate);
					initializeStores.saveChanges(allStores);
					String avgRating = allStores[i].getAvgRating()+"";
					//The average Rating is updated right away.
					storeRating.setText("Rating: "+avgRating.substring(0, avgRating.indexOf(".")+2));
				}
			}
		}
	}

}