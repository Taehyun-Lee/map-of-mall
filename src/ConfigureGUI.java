/*
 * This class is the ConfigureGUI
 * This is where the user with authorization is able to make changes to all the aspects of 
 * one Stores instance through the use of a Stores instance holder.
 */
//The imports needed for ConfigureGUI
import java.awt.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;

import javax.swing.ImageIcon;
public class ConfigureGUI extends JFrame implements ActionListener{
	//the components of ConfigureGUI
	private JButton apply, reset, jobsOn;
	private JTextArea changeName, changeGeneralInfo, changeWebURL, changePositionAvailable;
	private JLabel infoName, infoGeneralInfo, infoWebURL, infoJobs, infoPositionAvailable, infoType;
	private JButton [] fourChoices = new JButton[4];
	//This is the holder Stores instance that will receive all the changes from ConfigureGUI
	private Stores holder;
	private boolean Reset;
	public ConfigureGUI(String l)
	{
		//Since I cannot set a private variable in a for loop or an if statement, hold must
		//be set to the specific store instance then set to holder.
		Stores hold = new Stores();
		Stores[] allStores = initializeStores.initStores();
		//This block of code finds all the attributes of the selected Stores instance
		for(int i = 0; i<allStores.length; i++)
		{
			//This line of code searches for the Stores instance with the same name.
			if(allStores[i].getName().equalsIgnoreCase(l))
				{
					hold = allStores[i];
				}
			else if(allStores[i].getCodeNum().equalsIgnoreCase(l))
			{
				hold = allStores[i];
				
			}
		}
		//Those informations are then transfered into the new holder Stores instance
		
		holder = hold;
		this.setSize(475, 450);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle("Configure "+holder.getCodeNum()+" "+holder.getName());
		try
		{
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("IMG/Configure Info Background.png")))));
		} catch(IOException e)
		{
			
		}
		Container c = getContentPane();
		
		//Components are made according to the holder's data
		changeName = new JTextArea(holder.getName());
		c.add(changeName);
		changeName.setBounds(139, 48, 100, 20);
		
		infoName = new JLabel("Store Name:");
		c.add(infoName);
		infoName.setBounds(55, 48, 100, 17);
		
		changeGeneralInfo = new JTextArea(holder.getGeneralInfo());
		c.add(changeGeneralInfo);
		changeGeneralInfo.setBounds(123, 300, 300, 20);
		changeGeneralInfo.setLineWrap(true);
		changeGeneralInfo.setWrapStyleWord(true);
		
		infoGeneralInfo = new JLabel("General Info");
		c.add(infoGeneralInfo);
		infoGeneralInfo.setBounds(123, 281, 100, 17);
		
		changePositionAvailable = new JTextArea(holder.getPosAvailable());
		c.add(changePositionAvailable);
		changePositionAvailable.setBounds(123, 205, 300, 50);
		changePositionAvailable.setLineWrap(true);
		changePositionAvailable.setWrapStyleWord(true);
		
		infoPositionAvailable = new JLabel("Looking For: (Make sure you put commas after every job)");
		c.add(infoPositionAvailable);
		infoPositionAvailable.setFont(new Font("Arail", Font.PLAIN, 11));
		infoPositionAvailable.setBounds(120, 190, 310, 17);
		
		if(!holder.getJobAvailability())
			{
				changePositionAvailable.setVisible(false);
				infoPositionAvailable.setVisible(false);
			}
		
		changeWebURL = new JTextArea(holder.getWebURL());
		c.add(changeWebURL);
		changeWebURL.setBounds(139, 148, 200, 20);
		
		infoWebURL = new JLabel("Web URL:");
		c.add(infoWebURL);
		infoWebURL.setBounds(56, 148, 100, 17);
		
		apply = new JButton("Apply");
		c.add(apply);
		apply.setBounds(410, 409, 50, 20);
		apply.setFont(new Font("Arial", Font.PLAIN, 15));
		apply.addActionListener(this);
		
		reset = new JButton("Reset");
		c.add(reset);
		reset.setBounds(351, 409, 50, 20);
		reset.setFont(new Font("Arial", Font.PLAIN, 15));
		reset.addActionListener(this);
		
		ImageIcon jobNotAvailable = new ImageIcon("IMG/Career Select Button.png");
		ImageIcon jobAvailable = new ImageIcon("IMG/Career Select Button Selected.png");
		if(holder.getJobAvailability())
		{
			jobsOn = new JButton("1",jobAvailable);
			c.add(jobsOn);
			jobsOn.setBounds(103, 174, 14, 13);
			jobsOn.addActionListener(this);
		}
		else
		{
			jobsOn = new JButton("0",jobNotAvailable);
			c.add(jobsOn);
			jobsOn.setBounds(103, 174, 14, 13);
			jobsOn.addActionListener(this);
		}
		jobsOn.setFont(new Font("Arial", Font.BOLD, 0));
		
		infoJobs = new JLabel("Jobs:");
		c.add(infoJobs);
		infoJobs.setBounds(56, 171, 100, 17);
		
		int widthC = 250, selected = 5;
		if(holder.getStoreType().equalsIgnoreCase("Cloth"))
		{
			selected = 0;
		}
		else if(holder.getStoreType().equalsIgnoreCase("Food"))
		{
			selected = 1;
		}
		else if(holder.getStoreType().equalsIgnoreCase("General"))
		{
			selected = 2;
		}
		else if(holder.getStoreType().equalsIgnoreCase("Tech"))
		{
			selected = 3;
		}
		for(int i = 0;i<4;i++)
		{
			ImageIcon type = new ImageIcon("IMG/Choice"+i+"Config.png");
			ImageIcon selectType = new ImageIcon("IMG/Choice"+i+"OnConfig.png");
			
			fourChoices[i] = new JButton(type);
			if(i ==selected)
				fourChoices[i].setIcon(selectType);
			c.add(fourChoices[i]);
			fourChoices[i].setBounds(widthC,71,42,44);
			fourChoices[i].addActionListener(this);
			widthC += 45;
			
		}
		infoType = new JLabel("Store Type:  "+holder.getStoreType());
		c.add(infoType);
		infoType.setBounds(56, 87, 200, 17);
	}

	//This method takes in all the ActionEvents and runs the GUI accordingly
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jobsOn)
			actionJobsOn();
		if(e.getSource()==apply)
			actionApply();
		if(e.getSource()==reset)
			actionReset();
		for(int i = 0; i<fourChoices.length; i++ )
		{
			if(e.getSource()==fourChoices[i])
				actionFourChoice(i);
		}
		
	}
	//This method checks if holder instance has jobs available and shows jobsOn,
	//infoPositionAvailable, and changePositionAvailalbe accordingly.
	public void actionJobsOn()
	{
		ImageIcon jobNotAvailable = new ImageIcon("IMG/Career Select Button.png");
		ImageIcon jobAvailable = new ImageIcon("IMG/Career Select Button Selected.png");
		if(jobsOn.getText().equalsIgnoreCase("1"))
		{
			jobsOn.setText("0");
			jobsOn.setIcon(jobNotAvailable);
			infoPositionAvailable.setVisible(false);
			changePositionAvailable.setVisible(false);
			changePositionAvailable.setText(" ");
			holder.setJobAvailability(false);
			holder.setPosAvailable(" ");
		}
		else
		{
			jobsOn.setText("1");
			jobsOn.setIcon(jobAvailable);
			infoPositionAvailable.setVisible(true);
			changePositionAvailable.setVisible(true);
			holder.setJobAvailability(true);
		}
		boolean jobsAvailable = false;
		if(jobsOn.getText().equalsIgnoreCase("1"))
			jobsAvailable = true;
		holder.setJobAvailability(jobsAvailable);
	}
	//This method set the holder instance to null, and changes all the components so that everything
	//is blank.
	public void actionReset()
	{
		holder.returnToNull();
		infoType.setText("Store Type:  Not Selected");
		changeName.setText(" "); 
		changeGeneralInfo.setText(" ");
		changeWebURL.setText(" ");
		changePositionAvailable.setText(" ");
		for(int i = 0; i<fourChoices.length; i++)
		{
			ImageIcon notSelected = new ImageIcon("IMG/Choice"+i+"Config.png");
			fourChoices[i].setIcon(notSelected);
		}
		ImageIcon jobNotAvailable = new ImageIcon("IMG/Career Select Button.png");
		jobsOn.setText("0");
		jobsOn.setIcon(jobNotAvailable);
		infoPositionAvailable.setVisible(false);
		changePositionAvailable.setVisible(false);
	}
	//This method acts exactly the same as the one from MainGUI except for the fact that instead of 
	//changing the JComboBox, it changes infoType.
	public void actionFourChoice(int c)
	{
		for(int i = 0; i<fourChoices.length; i++)
		{
			ImageIcon selected = new ImageIcon("IMG/Choice"+i+"OnConfig.png");
			ImageIcon notSelected = new ImageIcon("IMG/Choice"+i+"Config.png");
			if(i != c)
				fourChoices[i].setIcon(notSelected);
			else
			{
				fourChoices[i].setIcon(selected);
				if(c==0)
				{
					holder.setStoreType("Cloth");
				}
				if(c==1)
				{
					holder.setStoreType("Food");
				}
				if(c==2)
				{
					holder.setStoreType("General");
				}
				if(c==3)
				{
					holder.setStoreType("Tech");
				}
				infoType.setText("Store Type:  "+holder.getStoreType());
			}
		}
	}
	//This method apply the changes held to holder to the IO textfile.
	public void actionApply()
	{
		Stores [] allStores = initializeStores.initStores();
		//here, holder is initialized with user data.
		setAllInfo();
		int check = 0;
		//if there was any user changes after the press of reset button, it will check if
		//any of the required fields were not filled out, using the pass method.
		if(!Reset)	
			check = pass();
		//according to the results of the pass method, the program will show the appropriate
		//JOptionPane
		if(check == 0)
		{
			
			for(int i = 0; i<allStores.length; i++)
			{
				if(allStores[i].getCodeNum().equalsIgnoreCase(holder.getCodeNum()))
				{
					//If all fields were filled out, holder's information is finally stored.
					allStores[i] = holder;
				}
			}
			initializeStores.saveChanges(allStores);
			JOptionPane.showMessageDialog(null, "Your Changes have been applied");
		}
		if(check == 1)
		{
			JOptionPane.showMessageDialog(null, "Please Enter a name");
		}
		if(check == 2)
		{
			JOptionPane.showMessageDialog(null, "Please select a store type");
		}
		if(check == 3)
		{
			JOptionPane.showMessageDialog(null, "Please fill in the Find Position section");
		}
		
		if(check == 4)
		{
			JOptionPane.showMessageDialog(null, "Please fill in the general information section");
		}
	}
	//This method checks if all the required fields are filled
	public int pass()
	{
		//This line checks for the name
		if(!holder.getName().equalsIgnoreCase("")&&!holder.getName().equalsIgnoreCase(" "))
		{
			//This line checks for the store type
			if(!holder.getStoreType().equalsIgnoreCase("")&&!holder.getStoreType().equalsIgnoreCase(" "))
			{
				//This line checks for the jobs and positions
				if(holder.getJobAvailability()&&!(holder.getPosAvailable()==null)&&!holder.getPosAvailable().equalsIgnoreCase(" ")&&!holder.getPosAvailable().equalsIgnoreCase(""))
				{
					//This line checks for the general information.
					if(!holder.getGeneralInfo().equalsIgnoreCase("")&&!holder.getGeneralInfo().equalsIgnoreCase(" "))
						return 0;
					else
						return 4;
				}
				else if(!holder.getJobAvailability()&&(holder.getPosAvailable() == null||holder.getPosAvailable().equalsIgnoreCase(" ")||holder.getPosAvailable().equalsIgnoreCase("")))
				{
					if(!holder.getGeneralInfo().equalsIgnoreCase("")&&!holder.getGeneralInfo().equalsIgnoreCase(" "))
						return 0;
					else
						return 4;
				}
					return 3;
			}
			else
			{
				return 2;
			}
		}
		else
		{
			return 1;
		}
	}
	//This method initializes all user inputs
	public void setAllInfo()
	{
		holder.setName(changeName.getText());
		holder.setWebURL(changeWebURL.getText());
		holder.setGeneralInfo(changeGeneralInfo.getText());
		holder.setPosAvailable(changePositionAvailable.getText());
		System.out.println(holder.getStoreType());
		System.out.println(holder.getName());
		System.out.println(holder.getWebURL());
		System.out.println(holder.getGeneralInfo());
		System.out.println(holder.getJobAvailability());
		//If all the fields are empty, then the program picks up that the user pressed the reset button and sets Reset to true
		if(holder.getStoreType().equalsIgnoreCase(" ")&&holder.getName().equalsIgnoreCase(" ")&&holder.getWebURL().equalsIgnoreCase("")&&holder.getGeneralInfo().equalsIgnoreCase(" ")&&!holder.getJobAvailability())
			Reset = true;
		else
			Reset = false;
		
	}
	
}
