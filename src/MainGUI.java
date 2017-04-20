/*
 * Piepelle Shopping Center GUI
 * Made by Taehyun Lee
 * 
 * Functions: the GUI boots up in two modes: navigate and configure
 * Navigate can be reached by anyone, but configure needs an id and password.
 * There can be multiple IDs and Passwords, which can be placed in the Security.txt file.
 * Password can be manipulated by the user in the configure window.
 * The comments may seen scarce, but this is due to most of them only involving with the components
 * of the GUI, such as which buttons are visible or not, or it was due to it being shown before.
 */
//implementation of all the needed methods.
import java.awt.*;
import java.io.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.ImageIcon;
public class MainGUI extends JFrame implements ActionListener{
	
	//declaration of all the variables in MainGUI.
	private JButton navigate, configure, career, allStores, back, changePassword;
	private JButton[] floors = new JButton[3];
	private JButton[] storesf1 = new JButton[7];
	private JButton[] storesf2 = new JButton[7];
	private JButton[] storesf3 = new JButton[7];
	private JButton[] careerPg = new JButton[3];
	private JButton[] selectCareer = new JButton[9];
	private JButton[] fourChoices = new JButton[4];
	private JLabel background, storeType;
	private JLabel[] storesCareer = new JLabel[9];
	private JLabel[] webCareer = new JLabel[9];
	private JLabel[] storeTypeCareer = new JLabel[9];
	private JComboBox stores;
	private JTextArea options;
	//pgNum keeps track of which page the user is at in the career window.
	private int pgNum;
	//conOrNav is a boolean that will turn true if the user is in configure mode
	//and turn false if the user is in navigate mode.
	private boolean conOrNav;
	//this is the constructor method that initializes all the components of the GUI.
	public MainGUI() throws IOException
	{
		this.setSize(661,491);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle("Pipelle Shopping Center GUI");
		Container c = getContentPane(); //Memorize
		
		c.setBackground(new Color(206,224,240));
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ImageIcon conButton = new ImageIcon("IMG/Configure Button.png");
		ImageIcon navButton = new ImageIcon("IMG/Navigate Button.png");
		
		allStores = new JButton("All Stores");
		c.add(allStores);
		allStores.setVisible(false);
		allStores.setBounds(515, 11, 100, 30);
		allStores.setFont(new Font("Helvetica", Font.BOLD, 16));
		allStores.setForeground(new Color(66, 66, 66));
		allStores.setBackground(new Color(69, 128, 140));
		allStores.addActionListener(this);
		
		//initializeStores.initStores() is a function in initializeStores class
		//this class was created to keep MainGUI organized by taking some lines off.
		Stores[] allTheStores = initializeStores.initStores();
		//initializeStores.initStores() returns an array of Stores object from an IO textfile.
		//The bottom block of code creates an array of string that contains stores with store names.
		int numOccupiedStores = 0, allOccupiedStoresCount = 0;
		for(int i = 0; i<allTheStores.length; i++)
		{
			if(!allTheStores[i].getStoreType().equalsIgnoreCase(" "))
				{
					//numOccupiedStores is the number of stores that have a store type.
					//This is used to set up the parameter of String allOccupiedStores.
					numOccupiedStores++;
				}
		}
		String [] allOccupiedStores = new String[numOccupiedStores];
		for(int i = 0; i<allTheStores.length; i++)
		{
			if(!allTheStores[i].getStoreType().equalsIgnoreCase(" "))
				{
					numOccupiedStores++;
					allOccupiedStores[allOccupiedStoresCount] = allTheStores[i].getCodeNum()+" "+allTheStores[i].getName();
					allOccupiedStoresCount++;
				}
		}
		//the array of store names are used to then initialize the JComboBox.
		stores = new JComboBox(allOccupiedStores);
		stores.setBounds(453, 124, 189, 29);
		c.add(stores);
		stores.setVisible(false);
		stores.addActionListener(this);
		navigate = new JButton("Navigate", navButton);
		configure = new JButton("Configure", conButton);
		c.add(navigate);
		c.add(configure);
		navigate.setFont(new Font("Georgia", Font.BOLD , 0 ));
		configure.setFont(new Font("Georgia", Font.BOLD , 0 ));
		navigate.setBounds(255, 190, 151, 42);
		configure.setBounds(255, 250, 151, 42);
		navigate.addActionListener(this);
		configure.addActionListener(this);
		
		//This is the button that allows the user to change the password of one of the ids.
		changePassword = new JButton("Change Password");
		c.add(changePassword);
		changePassword.setBounds(510, 450, 150, 20);
		changePassword.addActionListener(this);
		changePassword.setVisible(false);
		changePassword.setFont(new Font("Arial", Font.PLAIN, 15));
		
		ImageIcon carButton = new ImageIcon("IMG/Career Opportunities.png");
		career = new JButton(carButton);
		c.add(career);
		career.addActionListener(this);
		career.setBounds(453,192, 188, 39);
		career.setVisible(false);

		int widthC = 455;
		for(int i = 0;i<4;i++)
		{
			ImageIcon type = new ImageIcon("IMG/Choice"+i+".png");
			fourChoices[i] = new JButton(type);
			c.add(fourChoices[i]);
			fourChoices[i].setBounds(widthC,53,35,35);
			fourChoices[i].addActionListener(this);
			fourChoices[i].setVisible(false);
			widthC += 50;
			
		}
		
		back = new JButton("Back to Main");
		c.add(back);
		back.setBounds(550, 10, 100, 20);
		back.setFont(new Font("Arial", Font.PLAIN, 14));
		back.addActionListener(this);
		back.setBackground(new Color(158, 196, 230));
		back.setVisible(false);
		
		int heightCS = 104; 
		ImageIcon buttonCS = new ImageIcon("IMG/Career Select Button.png");
		for(int i = 0; i < selectCareer.length; i++)
		{
			selectCareer[i] = new JButton(buttonCS);
			storesCareer[i] = new JLabel();
			webCareer[i] = new JLabel();
			storeTypeCareer[i] = new JLabel();
			c.add(selectCareer[i]);
			c.add(storesCareer[i]);
			c.add(webCareer[i]);
			c.add(storeTypeCareer[i]);
			selectCareer[i].setBounds(11, heightCS, 14, 13);
			storesCareer[i].setBounds(30, heightCS-3, 150, 20);
			webCareer[i].setBounds(190, heightCS-3, 300, 20);
			storeTypeCareer[i].setBounds(395, heightCS-2, 20, 20);
			selectCareer[i].setFont(new Font("Georgia", Font.BOLD , 0 ));
			storesCareer[i].setFont(new Font("Helvetica", Font.PLAIN, 14));
			webCareer[i].setFont(new Font("Helvetica", Font.PLAIN, 14));
			selectCareer[i].addActionListener(this);
			storesCareer[i].setVisible(false);
			selectCareer[i].setVisible(false);
			storeTypeCareer[i].setVisible(false);
			heightCS += 35;
		}
		
		
		
		int widthFN = 323;
		for(int i = 0; i < floors.length; i++)
		{
			ImageIcon floor = new ImageIcon("IMG/floor "+(i+1)+" button.png");
			floors[i] = new JButton(floor);
			c.add(floors[i]);
			floors[i].setBounds(widthFN, 445, 36, 27);
			floors[i].setFont(new Font("Georgia", Font.BOLD , 0 ));
			floors[i].addActionListener(this);
			floors[i].setVisible(false);
			widthFN += 36;
		}
		int widthCP = 359;
		for(int i = 0; i < careerPg.length; i++)
		{
			ImageIcon cpgNum = new ImageIcon("IMG/C Page "+(i+1)+" button.png");
			careerPg[i] = new JButton(cpgNum);
			c.add(careerPg[i]);
			careerPg[i].setBounds(widthCP, 445, 36, 27);
			careerPg[i].setFont(new Font("Georgia", Font.BOLD , 0 ));
			careerPg[i].addActionListener(this);
			careerPg[i].setVisible(false);
			widthCP += 36;
		}
		//The following 3 blocks of code initializes the button arrays storesf1, storesf2, and storesf3
		int heightF = 340;
		for(int i = 0; i<3;i++)
		{
			//The names of each specific buttons are placed by going through all the Stores objects
			//stored in allTheStores.
			String posNum = (i+1)+"";
			storesf1[i] = new JButton(allTheStores[i].getName());
			c.add(storesf1[i]);
			storesf1[i].setBounds(31, heightF,90,50);
			storesf1[i].addActionListener(this);
			storesf1[i].setVisible(false);
	
			storesf2[i] = new JButton(allTheStores[i+7].getName());
			c.add(storesf2[i]);
			storesf2[i].setBounds(70, heightF,90,50);
			storesf2[i].addActionListener(this);
			storesf2[i].setVisible(false);
			
			storesf3[i] = new JButton(allTheStores[i+14].getName());
			c.add(storesf3[i]);
			storesf3[i].setBounds(107, heightF,90,50);
			storesf3[i].addActionListener(this);
			storesf3[i].setVisible(false);
			
			//if there is no store name to be found, the names are automatically set to 
			//"Empty Store "+corresponding integer.
			if(allTheStores[i].getName().equalsIgnoreCase(" "))
				storesf1[i].setText("Empty Store "+i);
			if(allTheStores[i+7].getName().equalsIgnoreCase(" "))
				storesf2[i].setText("Empty Store "+(i+7));
			if(allTheStores[i+14].getName().equalsIgnoreCase(" "))
				storesf3[i].setText("Empty Store "+(i+14));
			
			storesf1[i].setFont(new Font("Arial", Font.PLAIN, 10));
			storesf2[i].setFont(new Font("Arial", Font.PLAIN, 10));
			storesf3[i].setFont(new Font("Arial", Font.PLAIN, 10));
			heightF -= 98;
		}

		storesf1[3] = new JButton(allTheStores[3].getName());
		c.add(storesf1[3]);
		storesf1[3].addActionListener(this);
		storesf1[3].setBounds( 131, 114, 90, 50);
		
		storesf2[3] = new JButton(allTheStores[10].getName());
		c.add(storesf2[3]);
		storesf2[3].addActionListener(this);
		storesf2[3].setBounds(170, 114, 90, 50);
		
		storesf3[3] = new JButton(allTheStores[17].getName());
		c.add(storesf3[3]);
		storesf3[3].addActionListener(this);
		storesf3[3].setBounds(207, 114, 90, 50);
		
		if(allTheStores[3].getName().equalsIgnoreCase(" "))
			storesf1[3].setText("Empty Store 3");
		if(allTheStores[10].getName().equalsIgnoreCase(" "))
			storesf2[3].setText("Empty Store 10");
		if(allTheStores[17].getName().equalsIgnoreCase(" "))
			storesf3[3].setText("Empty Store 17");
		
		storesf1[3].setFont(new Font("Arial", Font.PLAIN, 10));
		storesf2[3].setFont(new Font("Arial", Font.PLAIN, 10));
		storesf3[3].setFont(new Font("Arial", Font.PLAIN, 10));
		
		
		storesf1[3].setVisible(false);
		storesf2[3].setVisible(false);
		storesf3[3].setVisible(false);

		heightF = 340;
		int posNum = 7;
		for(int i = 4; i<7;i++)
		{
			
			storesf1[i] = new JButton(allTheStores[posNum-1].getName());
			c.add(storesf1[i]);
			storesf2[i] = new JButton(allTheStores[posNum+6].getName());
			c.add(storesf2[i]);
			storesf3[i] = new JButton(allTheStores[posNum+13].getName());
			c.add(storesf3[i]);
			
			if(allTheStores[posNum-1].getName().equalsIgnoreCase(" "))
				storesf1[i].setText("Empty Store "+(posNum-1));
			if(allTheStores[posNum+6].getName().equalsIgnoreCase(" "))
				storesf2[i].setText("Empty Store "+(posNum+6));
			if(allTheStores[posNum+13].getName().equalsIgnoreCase(" "))
				storesf3[i].setText("Empty Store "+(posNum+13));
			storesf1[i].setBounds(231, heightF,90,50);
			storesf2[i].setBounds(272, heightF,90,50);
			storesf3[i].setBounds(308, heightF,90,50);
			storesf1[i].setVisible(false);
			storesf2[i].setVisible(false);
			storesf3[i].setVisible(false);
			storesf1[i].addActionListener(this);
			storesf2[i].addActionListener(this);
			storesf3[i].addActionListener(this);
			storesf1[i].setFont(new Font("Arial", Font.PLAIN, 10));
			storesf2[i].setFont(new Font("Arial", Font.PLAIN, 10));
			storesf3[i].setFont(new Font("Arial", Font.PLAIN, 10));
			posNum--;
			heightF -= 98;
		}

		options = new JTextArea();
		options.setLineWrap(true);
		options.setWrapStyleWord(true);
		c.add(options);
		options.setBounds(468, 86, 193, 403);
		options.setVisible(false);
		options.setBackground(new Color(146, 194, 124));
		
		storeType = new JLabel("All Stores");
		c.add(storeType);
		storeType.setFont(new Font("Helvetica", Font.BOLD , 15 ));
		storeType.setVisible(false);
		storeType.setBounds(459, 101, getWidth(), 15);
		
		background = new JLabel();
		background.setBounds(0, 0, getWidth(), getHeight());
		c.add(background);
		ImageIcon choiceBackGround = new ImageIcon("IMG/Choice Summative.png");
		background.setIcon(choiceBackGround);
	}
	//This is the actionPerformed method
	//All events come out of this method.
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==changePassword)
			actionChangePassword();
		if(e.getSource()==back)
			actionBackToNavigate();
		if(e.getSource()==navigate)
			actionNavigate();
		if(e.getSource()== configure)
			actionConfigure();
		if(e.getSource()== career)
			actionCareer();
		if(e.getSource() == stores)
			{
				actionComboToGUI();
			}
		if(e.getSource()==allStores)
		{
			actionChangeComboBox(4);
		}
		for(int i = 0; i<selectCareer.length; i++)
		{
			if(e.getSource()==selectCareer[i])
			{
				actionSelectCareer(i);
			}
		}
		for(int i = 0; i<careerPg.length; i++)
		{
			if(e.getSource()==careerPg[i])
			{
				actionCareerSort(i);
			}
		}
		for(int i = 0; i<fourChoices.length; i++)
		{
			if(e.getSource()==fourChoices[i])
			{
				actionChangeComboBox(i);
			}
		}
		
		for(int i = 0; i < floors.length; i++)
		{
			if(e.getSource()==floors[i])
			{
				actionRenameStores();
				actionChangeFloors(i);
			}
		}
		for(int i = 0; i < 7; i++)
		{
			if(e.getSource()==storesf1[i])
			{
				actionFloorOne(i);
			}
			if(e.getSource()==storesf2[i])
			{
				actionFloorTwo(i);
			}
			if(e.getSource()==storesf3[i])
			{
				actionFloorThree(i);
			}
		}
	}
	//The following three methods create either a StoreInfoGUI or ConfigureGUI depending on
	//the state of conOrNav.
	public void actionFloorOne(int i)
	{
		Stores [] allStores = initializeStores.initStores();
		for(int j = 0; j<allStores.length; j++)
		{
			//this line of code checks for a Stores class from the IO textfile with the same name
			//as the text on the button pressed.
			if(allStores[j].getName().equalsIgnoreCase(storesf1[i].getText())&&!allStores[j].getName().equalsIgnoreCase(" "))
			{
				//this if statement decides whether or not to open StoreInfoGUI or ConfigureGUI
				if(!conOrNav)
				{
					StoreInfoGUI gu;
					try 
					{
						gu = new StoreInfoGUI(storesf1[i].getText());
						gu.show();
					} catch (IOException e1)
					{
				
					}
				}
				if(conOrNav)
				{
					ConfigureGUI gu;
					gu = new ConfigureGUI(storesf1[i].getText());
					gu.show();
					actionRenameStores();
				}
			}
			//this line of code checks if the store is empty by checking if the button has a text saying
			//Empty Store i.
			if(storesf1[i].getText().indexOf(" ")>0&&storesf1[i].getText().substring(0, storesf1[i].getText().lastIndexOf(" ")).equalsIgnoreCase("Empty Store"))
			{
				//the following two lines of code determines which store this Empty Store is referring to
				//by checking the integer that comes after "Empty Store".
				String hold = storesf1[i].getText();
				int pos = Integer.parseInt(hold.substring(hold.lastIndexOf(" ")+1, hold.length()));
				allStores[pos].getCodeNum();
				//in this case, only ConfigureGUI will be opened, since an empty store
				//should not be creating a StoreInfoGUI that is also empty of any information.
				if(conOrNav)
				{
					ConfigureGUI gu;
					gu = new ConfigureGUI(allStores[pos].getCodeNum());
					gu.show();
					actionRenameStores();
				}
				j = 30;
			}
		}
	}
	public void actionFloorTwo(int i)
	{
		Stores [] allStores = initializeStores.initStores();
		for(int j = 0; j<allStores.length; j++)
		{
			if(allStores[j].getName().equalsIgnoreCase(storesf2[i].getText())&&!allStores[j].getName().equalsIgnoreCase(" "))
			{
				if(!conOrNav)
				{
					StoreInfoGUI gu;
					try 
					{
						gu = new StoreInfoGUI(storesf2[i].getText());
						gu.show();
					} catch (IOException e1)
					{
				
					}
				}
				if(conOrNav)
				{
					ConfigureGUI gu;
					gu = new ConfigureGUI(storesf2[i].getText());
					gu.show();
					actionRenameStores();
				}
			}
			if(storesf2[i].getText().indexOf(" ")>0&&storesf2[i].getText().substring(0, storesf2[i].getText().lastIndexOf(" ")).equalsIgnoreCase("Empty Store"))
			{
				String hold = storesf2[i].getText();
				int pos = Integer.parseInt(hold.substring(hold.lastIndexOf(" ")+1, hold.length()));
				if(conOrNav)
				{
					ConfigureGUI gu;
					gu = new ConfigureGUI(allStores[pos].getCodeNum());
					gu.show();
					actionRenameStores();
				}
				j = 30;
			}
		}
	}
	public void actionFloorThree(int i)
	{
		Stores [] allStores = initializeStores.initStores();
		for(int j = 0; j<allStores.length; j++)
		{
			if(allStores[j].getName().equalsIgnoreCase(storesf3[i].getText())&&!allStores[j].getName().equalsIgnoreCase(" "))
			{
				if(!conOrNav)
				{
					StoreInfoGUI gu;
					try 
					{
						gu = new StoreInfoGUI(storesf3[i].getText());
						gu.show();
					} catch (IOException e1)
					{
				
					}
				}
				if(conOrNav)
				{
					ConfigureGUI gu;
					gu = new ConfigureGUI(storesf3[i].getText());
					gu.show();
					actionRenameStores();
				}
			}
			if(storesf3[i].getText().indexOf(" ")>0&&storesf3[i].getText().substring(0, storesf3[i].getText().lastIndexOf(" ")).equalsIgnoreCase("Empty Store"))
			{
				String hold = storesf3[i].getText();
				int pos = Integer.parseInt(hold.substring(hold.lastIndexOf(" ")+1, hold.length()));
				if(conOrNav)
				{
					ConfigureGUI gu;
					gu = new ConfigureGUI(allStores[pos].getCodeNum());
					gu.show();
					actionRenameStores();
				}
				j = 30;
			}
		}
	}
	//This method simply resets the names of the stores so that when the store names are configured,
	//the new name will be shown right away.
	public void actionRenameStores()
	{
		//These codes are just the text initialization of the array of buttons that occurred
		//in the constructor method.
		Stores [] allStores = initializeStores.initStores();
		for(int i = 0; i<3;i++)
		{
			String posNum = (i+1)+"";
			storesf1[i].setText(allStores[i].getName());
			storesf2[i].setText(allStores[i+7].getName());
			storesf3[i].setText(allStores[i+14].getName());
			
			if(allStores[i].getName().equalsIgnoreCase(" "))
				storesf1[i].setText("Empty Store "+i);
			if(allStores[i+7].getName().equalsIgnoreCase(" "))
				storesf2[i].setText("Empty Store "+(i+7));
			if(allStores[i+14].getName().equalsIgnoreCase(" "))
				storesf3[i].setText("Empty Store "+(i+14));
		}
		storesf1[3].setText(allStores[3].getName());
		storesf2[3].setText(allStores[10].getName());
		storesf3[3].setText(allStores[17].getName());

		if(allStores[3].getName().equalsIgnoreCase(" "))
			storesf1[3].setText("Empty Store 3");
		if(allStores[10].getName().equalsIgnoreCase(" "))
			storesf2[3].setText("Empty Store 10");
		if(allStores[17].getName().equalsIgnoreCase(" "))
			storesf3[3].setText("Empty Store 17");
		int posNum = 7;
		for(int i = 4; i<7;i++)
		{
			storesf1[i].setText(allStores[posNum-1].getName());
	
			storesf2[i].setText(allStores[posNum+6].getName());
	
			storesf3[i].setText(allStores[posNum+13].getName());

			if(allStores[posNum-1].getName().equalsIgnoreCase(" "))
				storesf1[i].setText("Empty Store "+(posNum-1));
			if(allStores[posNum+6].getName().equalsIgnoreCase(" "))
				storesf2[i].setText("Empty Store "+(posNum+6));
			if(allStores[posNum+13].getName().equalsIgnoreCase(" "))
				storesf3[i].setText("Empty Store "+(posNum+13));
			posNum--;
		}
	}
	//This is simply a method that brings back all the setting of the navigate function
	public void actionBackToNavigate()
	{
		for(int i = 0; i<selectCareer.length; i++)
		{
			selectCareer[i].setVisible(false);
			storesCareer[i].setVisible(false);
			webCareer[i].setVisible(false);
			storeTypeCareer[i].setVisible(false);
		}
		for(int i = 0; i<careerPg.length; i++)
		{
			careerPg[i].setVisible(false);
		}
		options.setVisible(false);
		back.setVisible(false);
		actionNavigate();
	}
	//This method takes in an input from the JComboBox and opens up a StoreInfoGUI or
	//ConfigureGUI depending on  the state of conOrNav.
	public void actionComboToGUI()
	{
		Stores[] allStores = initializeStores.initStores();
		for(int i = 0; i<allStores.length; i++)
		{
			String check =allStores[i].getCodeNum()+" "+allStores[i].getName();
			//This line of code searches for a Stores object with the same code number and store name as
			//The input from the JComboBox
			if(stores.getSelectedItem().equals(check))
			{
				//The rest of the method is essentially the same as the actionFloorOne, actionFloorTwo,
				//and actionFloorThree methods
				if(!conOrNav)
				{
					StoreInfoGUI gu;
					try 
					{
						gu = new StoreInfoGUI(allStores[i].getName());
						gu.show();
					} catch (IOException e1)
					{
				
					}
				}
				if(conOrNav)
				{
					ConfigureGUI gu;
					gu = new ConfigureGUI(allStores[i].getName());
					gu.show();
				}
				
			}
		}
	}
	//This method creates a JOptionPane that takes in three inputs from the user
	//so that the user may change the password with the correct authorization
	public void actionChangePassword()
	{
		JTextField Username = new JTextField();
		JTextField Password = new JTextField();
		JTextField newPassword = new JTextField();
		Security.initializeIDandPassword();
		Object [] fields = { "UserName", Username, "Password", Password, "New Password", newPassword};
		JOptionPane.showConfirmDialog(null, fields, "Change Password", JOptionPane.OK_CANCEL_OPTION);
		//source is https://www.youtube.com/watch?v=r0S9GLxca1w
		String id = Username.getText();
		String password = Password.getText();
		String np = newPassword.getText();
		//through the use of Security class, the new password is set, as long as it is a valid password
		if(Security.getCheckPassword(id, password, np))
		{
			Security.setPassword(id, password, np);
			Security.saveIDandPassword();
		}
		else if(id.isEmpty())
		{
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Access Denied");
			actionChangePassword();
		}
	}
	//This method simply lays out the window depending on the floor number and the state of conOrNav
	//In other words, it simply lays out the buttons and the background image
	public void actionChangeFloors(int i)
	{
		ImageIcon floorChange = new ImageIcon();
		if(!conOrNav)	
		{
			floorChange = new ImageIcon("IMG/Navigate Floor "+(i+1)+".png");
		}
		else
		{
			floorChange = new ImageIcon("IMG/Configure Floor "+(i+1)+".png");
		}
		if(i == 0)
		{
			background.setIcon(floorChange);
			int widthFN = 323;
			for(int j = 0; j < floors.length; j++)
			{
				floors[j].setBounds(widthFN, 445, 36, 27);
				widthFN += 36;
			}
			for(int j = 0; j < storesf1.length; j++)
			{
				storesf1[j].setVisible(true);
				storesf2[j].setVisible(false);
				storesf3[j].setVisible(false);
			}
		}
		if(i == 1)
		{
			background.setIcon(floorChange);
			floors[0].setBounds(0, 445, 36,27);
			floors[1].setBounds(359, 445, 36, 27);
			floors[2].setBounds(395, 445, 36, 27);
			for(int j = 0; j < storesf1.length; j++)
			{
				storesf1[j].setVisible(false);
				storesf2[j].setVisible(true);
				storesf3[j].setVisible(false);
			}
		}
		if(i == 2)
		{
			background.setIcon(floorChange);
			floors[0].setBounds(0, 445, 36,27);
			floors[1].setBounds(36, 445, 36, 27);
			floors[2].setBounds(395, 445, 36, 27);
			for(int j = 0; j < storesf1.length; j++)
			{
				storesf1[j].setVisible(false);
				storesf2[j].setVisible(false);
				storesf3[j].setVisible(true);
			}
		}
			
	}
	@SuppressWarnings("unchecked")
	//This method remodels the JComboBox depending on which button is pressed.
	//it finds which stores are initialized, and what type of store it is, in the same way
	//the JComboBox is first initialized in the constructor method.
	public void actionChangeComboBox(int c)
	{
			if(c == 4)
			{
				for(int i = 0; i<fourChoices.length;i++)
				{
					ImageIcon notSelected = new ImageIcon("IMG/Choice"+i+".png");
					fourChoices[i].setIcon(notSelected);
				}
				Stores allStores[] = initializeStores.initStores();
				storeType.setText("All Stores");

				int numOccupiedStores = 0, allOccupiedStoresCount = 0;
				for(int j = 0; j<allStores.length; j++)
				{
					if(!allStores[j].getStoreType().equalsIgnoreCase(" "))
						{
							numOccupiedStores++;
						}
				}
				String [] allOccupiedStores = new String[numOccupiedStores];
				for(int j = 0; j<allStores.length; j++)
				{
					if(!allStores[j].getStoreType().equalsIgnoreCase(" "))
						{
							numOccupiedStores++;
							allOccupiedStores[allOccupiedStoresCount] = allStores[j].getCodeNum()+" "+allStores[j].getName();
							allOccupiedStoresCount++;
						}
				}
				//This line of code is from http://stackoverflow.com/questions/396896/changing-the-contents-of-the-jcombobox
				//It allows the user to remodel the JComboBox with a new array of Strings.
				stores.setModel(new JComboBox<>(allOccupiedStores).getModel());
			}

		for(int i = 0; i < fourChoices.length; i++)
		{
			ImageIcon selected = new ImageIcon("IMG/Choice"+i+"On.png");
			ImageIcon notSelected = new ImageIcon("IMG/Choice"+i+".png");
			if(i != c)
				fourChoices[i].setIcon(notSelected);
			else
			{
				fourChoices[i].setIcon(selected);
				Stores allStores[] = initializeStores.initStores();
				if(i == 0)
					{
						storeType.setText("Cloth Store");
						int numClothStores = 0;
						for(int j = 0; j<allStores.length;j++)
						{
							if(allStores[j].getStoreType().equalsIgnoreCase("Cloth"))
								{
									numClothStores++;
								}
						}
						int clothCount = 0;
						String Cloth[] = new String[numClothStores];
						for(int j = 0; j<allStores.length;j++)
						{
							if(allStores[j].getStoreType().equalsIgnoreCase("Cloth"))
								{
									Cloth[clothCount] = allStores[j].getCodeNum()+" "+allStores[j].getName();
									clothCount++;
								}
						}
						stores.setModel(new JComboBox<>(Cloth).getModel());
					}
				if(i == 1)
					{
						storeType.setText("Food Store");
						
						int numFoodStores = 0;
						for(int j = 0; j<allStores.length;j++)
						{
							if(allStores[j].getStoreType().equalsIgnoreCase("Food"))
								{
									numFoodStores++;
								}
						}
						int foodCount = 0;
						String Food[] = new String[numFoodStores];
						for(int j = 0; j<allStores.length;j++)
						{
							if(allStores[j].getStoreType().equalsIgnoreCase("Food"))
								{
									Food[foodCount] = allStores[j].getCodeNum()+" "+allStores[j].getName();
									foodCount++;
									
								}
						}
						stores.setModel(new JComboBox<>(Food).getModel());
						
					}
				if(i == 2)
					{
						storeType.setText("General Store");
						int numGeneralStores = 0;
						for(int j = 0; j<allStores.length;j++)
						{
							if(allStores[j].getStoreType().equalsIgnoreCase("General"))
								{
									numGeneralStores++;
								}
						}
						int generalCount = 0;
						String General[] = new String[numGeneralStores];
						for(int j = 0; j<allStores.length;j++)
						{
							if(allStores[j].getStoreType().equalsIgnoreCase("General"))
								{
									General[generalCount] = allStores[j].getCodeNum()+" "+allStores[j].getName();
									generalCount++;
								}
						}
						stores.setModel(new JComboBox<>(General).getModel());
					}
				if(i == 3)
					{
						storeType.setText("Tech Store");

						int numTechStores = 0;
						for(int j = 0; j<allStores.length;j++)
						{
							if(allStores[j].getStoreType().equalsIgnoreCase("Tech"))
								{
									numTechStores++;
								}
						}
						int techCount = 0;
						String Tech[] = new String[numTechStores];
						for(int j = 0; j<allStores.length;j++)
						{
							if(allStores[j].getStoreType().equalsIgnoreCase("Tech"))
								{
									Tech[techCount] = allStores[j].getCodeNum()+" "+allStores[j].getName();
									techCount++;
								}
						}
						stores.setModel(new JComboBox<>(Tech).getModel());
					}
				
			}
		}
	}
	//This method simply sets up the background image and the state of conOrNav
	//so that when the actionSetUp method is called, it will show the correct button with correct
	//functions.
	public void actionNavigate()
	{
		actionSetup("IMG/Navigate Floor 1.png", true);
		conOrNav = false;
	}
	//This method asks for the user to authorize themselves
	//When the authorization is met, same thing as actionNavigate method occurs
	public void actionConfigure()
	{
		JTextField Username = new JTextField();
		JTextField Password = new JTextField();
		Security.initializeIDandPassword();
		Object [] fields = { "UserName", Username, "Password", Password};
		JOptionPane.showConfirmDialog(null, fields, "Login", JOptionPane.OK_CANCEL_OPTION);
		//source is https://www.youtube.com/watch?v=r0S9GLxca1w
		String id = Username.getText();
		String password = Password.getText();
		
		if(Security.checkPassword(id, password))
		{
			actionSetup("IMG/Configure Floor 1.png", false);
			conOrNav = true;
			changePassword.setVisible(true);
		}
		else if(id.isEmpty())
		{
		}
		//when the user fails to input the correct id and password, actionConfigure method repeats.
		else
		{
			JOptionPane.showMessageDialog(null, "Access Denied");
			actionConfigure();
		}
	}
	//This method simply sets up all the buttons and backgrounds at the right place
	//for both navigate and configure window.
	public void actionSetup(String imageName, boolean careerWindow)
	{
		ImageIcon newBackground = new ImageIcon(imageName);
		background.setIcon(newBackground);
		
		
		stores.setVisible(true);
		
		for(int i = 0; i<storesf1.length;i++)
		{
			storesf1[i].setVisible(true);
			storesf2[i].setVisible(false);
			storesf3[i].setVisible(false);
		}
		for(int i = 0; i<floors.length; i++)
		{
			floors[i].setVisible(true);
		}
		allStores.setVisible(true);
		navigate.setVisible(false);
		configure.setVisible(false);
		if(careerWindow)
			career.setVisible(true);
		storeType.setVisible(true);
		for(int i = 0; i<fourChoices.length; i++)
		{
			fourChoices[i].setVisible(true);
		}
	}
	//This method has the same functionality as actionSetup method, but for career window.
	public void actionCareer()
	{
		ImageIcon careerBackground = new ImageIcon("IMG/careerBackground.png");
		storeType.setVisible(false);
		background.setIcon(careerBackground);
		allStores.setVisible(false);
		stores.setVisible(false);
		back.setVisible(true);
		for(int i = 0; i<storesf1.length;i++)
		{
			storesf1[i].setVisible(false);
			storesf2[i].setVisible(false);
			storesf3[i].setVisible(false);
		}
		for(int i = 0; i < floors.length; i++)
		{
			floors[i].setVisible(false);
		}
		for(int i = 0; i<fourChoices.length; i++)
		{
			fourChoices[i].setVisible(false);
		}
		navigate.setVisible(false);
		configure.setVisible(false);
		career.setVisible(false);
		options.setVisible(true);
		
		
		sortJob();
	}
	//This is just an extension of the actionCareer method
		public void sortJob()
		{
			Stores[] allStores = initializeStores.initStores();

			int storesWithJob = initializeStores.getNumJobs();
			if(storesWithJob>9)
			{
				careerPg[0].setVisible(true);
				careerPg[1].setVisible(true);
			}
			if(storesWithJob>18)
			{
				careerPg[0].setVisible(true);
				careerPg[1].setVisible(true);
				careerPg[2].setVisible(true);
			}
			int buttonNum = 0;
			for(int i = 0; i<allStores.length; i++)
			{
				
				if(allStores[i].getJobAvailability()&&buttonNum<9)
				{
					selectCareer[buttonNum].setVisible(true);
					storesCareer[buttonNum].setText(allStores[i].getCodeNum()+" "+allStores[i].getName());
					storesCareer[buttonNum].setVisible(true);
					if(allStores[i].getWebURL()!=null)
					{
						webCareer[buttonNum].setText(allStores[i].getWebURL());
						webCareer[buttonNum].setVisible(true);
					}
					ImageIcon type = new ImageIcon("IMG/Career "+allStores[i].getStoreType()+".png");
					storeTypeCareer[buttonNum].setIcon(type);
					storeTypeCareer[buttonNum].setVisible(true);
					buttonNum++;
					
				}
			}
			pgNum = 1;
		}
	//This method checks for which careerPg is pressed.
	//According to the pressed careerPg, it sets up selectCareer, storesCareer, webCareer,
	//and storeTypeCareer
	public void actionCareerSort(int i)
	{
		ImageIcon notSelected = new ImageIcon("IMG/Career Select Button.png");
		for(int j = 0; j<selectCareer.length; j++)
		{
			selectCareer[j].setIcon(notSelected);
			selectCareer[j].setVisible(false);
			storesCareer[j].setVisible(false);
			webCareer[j].setVisible(false);
			storeTypeCareer[j].setVisible(false);
		}
		Stores[] allStores = initializeStores.initStores();
		if(i == 0)
		{
			int buttonNum = 0;
			for(int j = 0; j<allStores.length; j++)
			{
				//This line of code will initialize the arrays selectCareer, storesCareer, etc within
				//the vicinity of 9, which is the max number of stores with jos that can be shown
				//in one page.
				if(allStores[j].getJobAvailability()&&buttonNum<9)
				{
					selectCareer[buttonNum].setVisible(true);
					storesCareer[buttonNum].setText(allStores[j].getCodeNum()+" "+allStores[j].getName());
					storesCareer[buttonNum].setVisible(true);
					webCareer[buttonNum].setText(allStores[j].getWebURL());
					webCareer[buttonNum].setVisible(true);
					ImageIcon type = new ImageIcon("IMG/Career "+allStores[j].getStoreType()+".png");
					storeTypeCareer[buttonNum].setIcon(type);
					storeTypeCareer[buttonNum].setVisible(true);
					buttonNum++;
				}
			}
			//initialization of pgNum that will be used in actionSelectCareer
			pgNum = 1;
		}
		if(i == 1)
		{
			int buttonNum = 0, secondButtonNum = 0;
			for(int j = 0; j<allStores.length; j++)
			{
				if(allStores[j].getJobAvailability())
					buttonNum++;
				if(allStores[j].getJobAvailability()&&buttonNum>9&&buttonNum<19)
				{
					selectCareer[secondButtonNum].setVisible(true);
					storesCareer[secondButtonNum].setText(allStores[j].getCodeNum()+" "+allStores[j].getName());
					storesCareer[secondButtonNum].setVisible(true);
					
					if(allStores[j].getWebURL()!= null)
					{
						
						webCareer[secondButtonNum].setText(allStores[j].getWebURL());
						webCareer[secondButtonNum].setVisible(true);
					}
					ImageIcon type = new ImageIcon("IMG/Career "+allStores[j].getStoreType()+".png");
					storeTypeCareer[secondButtonNum].setIcon(type);
					storeTypeCareer[secondButtonNum].setVisible(true);
					secondButtonNum++;
				}
			}
			pgNum = 2;
		}
		if(i == 2)
		{
			int buttonNum = 0, secondButtonNum = 0;
			for(int j = 0; j<allStores.length; j++)
			{
				if(allStores[j].getJobAvailability())
					buttonNum++;
				if(allStores[j].getJobAvailability()&&buttonNum<18)
				{
					selectCareer[secondButtonNum].setVisible(true);
					storesCareer[secondButtonNum].setText(allStores[j].getCodeNum()+" "+allStores[j].getName()+" "+allStores[j].getStoreType());
					storesCareer[secondButtonNum].setVisible(true);
					webCareer[secondButtonNum].setText(allStores[j].getWebURL());
					webCareer[secondButtonNum].setVisible(true);
					ImageIcon type = new ImageIcon("IMG/Career "+allStores[j].getStoreType()+".png");
					storeTypeCareer[secondButtonNum].setIcon(type);
					storeTypeCareer[secondButtonNum].setVisible(true);
					secondButtonNum++;
				}
			}
			pgNum = 3;
		}
	}
	//This method finds the Stores instance that matches the selected Store and shows
	//the positions available on the options JTextArea
	public void actionSelectCareer(int i)
	{
		Stores[] allStores = initializeStores.initStores();
		ImageIcon selected = new ImageIcon("IMG/Career Select Button Selected.png");
		ImageIcon notSelected = new ImageIcon("IMG/Career Select Button.png");
		//This line of code checks for which career page the function is being done on.
		if(pgNum==1)
		{
			int buttonNum = 0, selectNum = 0;
			for(int j = 0; j<allStores.length; j++)
			{
				if(allStores[j].getJobAvailability())
					{
						buttonNum++;
					}
				if(allStores[j].getJobAvailability()&&buttonNum<9)
				{
					selectCareer[selectNum].setIcon(notSelected);
					selectNum++;
				}
				//This line of code activates when the for loop finds the correct Stores instance
				if(buttonNum == (i+1)&&allStores[j].getJobAvailability())
					{
						//The getPositionAvailable() function is a special output method that sends
						//the positions one line down from each other
						options.setText("Jobs Available:\n"+allStores[j].getPositionsAvailable());
						selectCareer[buttonNum-1].setIcon(selected);
					}
			}
			
		}
		if(pgNum==2)
		{
			int buttonNum = 0, selectNum = 0;
			for(int j = 0; j<allStores.length; j++)
			{
				if(allStores[j].getJobAvailability())
					{
						buttonNum++;
					}
				if(allStores[j].getJobAvailability()&&buttonNum>9&&buttonNum<19)
				{
					selectCareer[selectNum].setIcon(notSelected);
					selectNum++;
				}
				if(buttonNum == (i+10)&&allStores[j].getJobAvailability()&&buttonNum>9&&buttonNum<19)
					{
						options.setText("Jobs Available:\n"+allStores[j].getPositionsAvailable());
						selectCareer[selectNum-1].setIcon(selected);
					}
			}
		}
		if(pgNum==3)
		{
			int buttonNum = 0, selectNum = 0;
			for(int j = 0; j<allStores.length; j++)
			{
				if(allStores[j].getJobAvailability())
					{
						buttonNum++;
					}
				if(allStores[j].getJobAvailability()&&buttonNum>17)
				{
					selectCareer[selectNum].setIcon(notSelected);
					selectNum++;
				}
				if(buttonNum == (i+19)&&allStores[j].getJobAvailability()&&buttonNum>18)
					{
						options.setText("Jobs Available:\n"+allStores[j].getPositionsAvailable());
						selectCareer[selectNum-1].setIcon(selected);
					}
			}
		}
	}
	//This is the main method that runs the MainGUI
	public static void main(String[] args) throws IOException {
		
		MainGUI gui = new MainGUI();
		gui.show();
	}
}