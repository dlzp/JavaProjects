import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Component.*;
import java.text.*;

/** Assignment 9 Bagel House
@author David Pinheiro
@version 1.1
*/

class BagelShop extends JFrame implements ActionListener, ItemListener
{
	private static final int FRAME_WIDTH = 355;
	private static final int FRAME_HEIGHT = 340;
	private static final int X_LOCATION = 425;
	private static final int Y_LOCATION = 100;
	private static final String TITLE = "Algoma Bagel Shop";
	private static double subCost = 0.0;
	private static double ret;
	private static String subPrice = "0.0"; 
	private static String taxPrice = "0.0";
	private static String totalPrice = "0.0";
	
	private static double tax3 = 0.0;
	private static double total3 =0.0;
	private static final double PERTAX = .13;
	private static String [] toppings = {"Cream Cheese ($.50)", "Butter ($.25)", "Blueberry Jam ($.25)",
									"Raspberry Jam ($.75)", "Peach Jelly($.75)"};
	private static String [] bagelType ={ "None","White ($1.25)","Whole Wheat ($1.50)"};
	private static String [] coffee = {"None", "Regular Coffee ($1.25)", "Cappuccino ($2.00)", "Cafe au lait ($1.75)"};
	
	private static JMenuBar menuBar = new JMenuBar();
	private static JMenu file = new JMenu ("File");
	private static JMenuItem quit = new JMenuItem ("Quit"); 
	
	private static JPanel panelBT = new JPanel();
	private static JPanel panelBagel = new JPanel();
	private static JPanel panelToppings = new JPanel();
	private static JPanel panelCoffee = new JPanel();
	private static JPanel panelPrice = new JPanel();
	private static JPanel panelPrCoff = new JPanel();
	private static JPanel panelButtons = new JPanel();
	private static JPanel panel = new JPanel();
	
	private static JButton calculate = new JButton("Calculate");
	private static JButton reset = new JButton("Reset");
	
	private static JLabel subtotal = new JLabel();
	private static JLabel subtotal2 =new JLabel();
	private static JLabel tax = new JLabel();
	private static JLabel tax2 =new JLabel();
	private static JLabel total = new JLabel();
	private static JLabel total2 =new JLabel();

	private static JRadioButton[] bagelChoice = new JRadioButton[bagelType.length];
	private static JRadioButton[] coffeeChoice = new JRadioButton[coffee.length];
	private static ButtonGroup bagel = new ButtonGroup();
	private static ButtonGroup coffeeGroup = new ButtonGroup();
	private static JCheckBox[] tCheckBox = new JCheckBox [ toppings.length];
	private static Container bs;	
	
	/** Constructor with no arguments creates GUI
	*/
	public  BagelShop()
		{
			// Creates Frame
			setSize(FRAME_WIDTH, FRAME_HEIGHT);
			setTitle(TITLE);
			setVisible(true);
			setResizable(false);
			setLocation(X_LOCATION, Y_LOCATION);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			bs = this.getContentPane();
			
			// Set the layout for main pannels			
			panel.setLayout(new GridLayout(0,2));
			panelBT.setLayout(new FlowLayout());
			panelPrCoff.setLayout(new FlowLayout());
			panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			//Calls methods to create GUI
			menu();
			bagelGUI();
			toppingsGUI();
			coffeeGUI();
			priceGUI();
			buttonsGUI();
			panelsGUI();
			
			// Sets Menubar
			setJMenuBar(menuBar);

			// Adds listener for all events
			quit.addActionListener(this);
			calculate.addActionListener(this);
			reset.addActionListener(this);
			
			for (int i=0; i< bagelChoice.length ; i++ )
			{
				bagelChoice[i].addItemListener(this);
			}
			for (int i=0;i< tCheckBox.length ; i++ )
			 {
				tCheckBox[i].addItemListener(this);
			 }
			for (int i=0; i <coffeeChoice.length ; i++ )
			{
				coffeeChoice[i].addItemListener(this);
			}
			
		}
	/** Method menu Creates Menu bar
	*/
	private static void menu()
	{
		menuBar.add(file);
		file.addSeparator();			
		file.add(quit);
	}
	
	/** Method bagelGUI creats the bagel option in the GUI
	*/
	private static void bagelGUI()
	{
		panelBagel.setBorder(BorderFactory.createTitledBorder("Pick a Bagel"));
		panelBagel.setLayout(new GridLayout(3,0));
			
		for (int i=0 ;i< bagelChoice.length ;i++ )
		{
			bagelChoice[i] = new JRadioButton(bagelType[i]);
			panelBagel.add(bagelChoice[i]);
			bagel.add(bagelChoice[i]);
		}
		bagelChoice[0].setSelected(true);
	}
	
	/** Method toppings Creates the topping selection in the GUI
	*/
	private static void toppingsGUI()
	{
			panelToppings.setBorder(BorderFactory.createTitledBorder("Pick your Toppings"));
			panelToppings.setLayout(new GridLayout(5,0));
			for (int i=0; i < tCheckBox.length ; i++ )
			{
				tCheckBox[i] = new JCheckBox (toppings[i]);
				panelToppings.add (tCheckBox[i]);
			}
	}		
	
	/** Method coffeeGUI creates the coffee option in the GUI
	*/
	private static void coffeeGUI()
	{
		panelCoffee.setBorder(BorderFactory.createTitledBorder("Want Coffee with That?"));
		panelCoffee.setLayout(new GridLayout (4,0));
			for (int i=0; i <coffeeChoice.length ; i++ )
			{
				coffeeChoice[i]= new JRadioButton (coffee[i]);
				panelCoffee.add(coffeeChoice[i]);
				coffeeGroup.add(coffeeChoice[i]);
			}
			coffeeChoice[0].setSelected(true);
			topAccess(false);
	}
	
	/** Method priceGUI creates the Price Section in the GUI
	*/
	private static void priceGUI()
	{
		panelPrice.setBorder(BorderFactory.createTitledBorder("Price"));
		panelPrice.setLayout(new GridLayout(3,1,18,2));
		panelPrice.add(subtotal);
		subtotal.setText("Subtotal");
		panelPrice.add(subtotal2);
		subtotal2.setText("$"+ subPrice);
		panelPrice.add(tax);
		tax.setText("Tax (%13)");
		panelPrice.add(tax2);
		tax2.setText ("$"+ taxPrice);
		panelPrice.add(total);
		total.setText("Total");
		panelPrice.add(total2);
		total2.setText("$"+ totalPrice);
	}
	
	/** Method panelsGUI adds the panels to the frame
	*/
	private static void panelsGUI()
	{
		panelBT.add(panelBagel);
		panelBT.add(panelToppings);
		panel.add(panelBT);
		panelPrCoff.add(panelCoffee);
		panelPrCoff.add(panelPrice);
		panel.add(panelPrCoff);
		bs.add(panel);
		bs.add(panelButtons, BorderLayout.SOUTH);
	}

	/** Method buttonsGUI adds the buttons to panelButtons
	*/
	private static void buttonsGUI()
	{
		panelButtons.add(calculate);
		panelButtons.add(reset);
	}
	
	/**Method calCost calculates the tax and the total cost of the order
	*/
	private static void calCost()
	{
		tax3 = subCost * PERTAX;
		total3 = subCost + tax3;
	}
	
	/** Method topAccess enables and disables the toppings selections
	@param boolean
	*/
	public static void topAccess(boolean v)
	{
		for (int i=0;i< tCheckBox.length ; i++ )
			 {
				tCheckBox[i].setEnabled(v);
			 }
	}
		
	/** Method itemStateChanged is abstract method overridden
		gives the actions to the Item Selection
	@param ItemEvent
	*/
	public void itemStateChanged(ItemEvent isc)
	{
		// NumberFormat used to round to 2 decimal places
		NumberFormat formatter =new DecimalFormat("#0.00");
		
		Object source = isc.getItemSelectable();
		
		// actions to the topping selection
		if (source == tCheckBox[0])
		{
			subCost+=.50;
			subtotal2.setText("$" + formatter.format(subCost));
		}
		if (source == tCheckBox[1])
		{
			subCost+=.25;
			
			subtotal2.setText("$" + formatter.format(subCost));
		}
		if (source == tCheckBox[2])
		{
			subCost+=.25;
			subtotal2.setText("$" + formatter.format(subCost));
		}
		
		if (source == tCheckBox[3])
		{
			subCost+=.75;
			
			subtotal2.setText("$" + formatter.format(subCost));
		}
		
		if (source == tCheckBox[4])
		{
			subCost+=.75;
			subtotal2.setText("$" + formatter.format(subCost));
		}
		
		// action to the bagel selection
		if (source==bagelChoice[0])
		{
			for (int i=0;i< tCheckBox.length ; i++ )
			 {
				tCheckBox[i].setSelected(false);
			 }
			 topAccess(false);
		}		
		if (source==bagelChoice[1])
		{
			subCost+= 1.25;
			subtotal2.setText("$" + formatter.format(subCost));
		}
		
		if(source==bagelChoice[2])
			subCost+=1.50;
			subtotal2.setText("$" + formatter.format(subCost));
		
		// action to the coffee selection
		if(source==coffeeChoice[0])
		{
		}
		
		if(source==coffeeChoice[1])
		{
			subCost+= 1.25;
			subtotal2.setText("$" + formatter.format(subCost));
		}
		
		if(source==coffeeChoice[2])
		{
			subCost+= 2.00;
			subtotal2.setText("$" + formatter.format(subCost));
		}
		
		if(source==coffeeChoice[3])
		{
			subCost+= 1.75;
			subtotal2.setText("$" + formatter.format(subCost));
		}
		
		// actions for items deselected
		if (isc.getStateChange()==ItemEvent.DESELECTED)
		{	
			//action for topping selection
			if (source == tCheckBox[0])
			{		
				subCost= subCost - 1.0;
				subtotal2.setText("$"+ formatter.format(subCost));
			}
			
			if (source == tCheckBox[1])
			{		
				subCost -= .50;
				subtotal2.setText("$"+ formatter.format(subCost));
			}
			
			if (source == tCheckBox[2])
			{		
				subCost -= .50;
				subtotal2.setText("$"+ formatter.format(subCost));
			}
			
			if (source == tCheckBox[3])
			{		
				subCost -= 1.50;
				subtotal2.setText("$"+formatter.format(subCost));
			}
			
			if (source == tCheckBox[4])
			{		
				subCost -= 1.50;
				subtotal2.setText("$"+ formatter.format(subCost));
			}
			
			// action for bagel selection
			if (source==bagelChoice[0])
			{
				topAccess(true);	
			}		
			
			if (source==bagelChoice[1])
			{
				subCost-=2.50;
				subtotal2.setText("$" + formatter.format(subCost));
			}
			
			if(source==bagelChoice[2])
			{	
				subCost-=3.00;
				subtotal2.setText("$" + formatter.format(subCost));
			}
		
			// action for coffee selection
			if(source==coffeeChoice[0])
			{
			}
		
			if(source==coffeeChoice[1])
			{
				subCost-= 2.50;
				subtotal2.setText("$" + formatter.format(subCost));
			}
		
			if(source==coffeeChoice[2])
			{
				subCost-= 4.00;
				subtotal2.setText("$" + formatter.format(subCost));
			}
		
			if(source==coffeeChoice[3])
			{
				subCost-= 3.50;
				subtotal2.setText("$" + formatter.format(subCost));
			}

		}
	}
	/** Method actionPerformed is abstract method overwritten
		gives the actions to the buttons
	@param ActionEvent
	*/
	public void actionPerformed(ActionEvent a)
	{
		
		
		if (quit == a.getSource())
		{
			System.exit(0);
		}
	
		if (reset== a.getSource())
		{
			NumberFormat formatter =new DecimalFormat("#0.00");
			for (int i=0;i< tCheckBox.length ; i++ )
			 {
				tCheckBox[i].setSelected(false);
								
			 }
				coffeeChoice[0].setSelected(true);
				bagelChoice[0].setSelected(true);
				topAccess(false);
				calCost();
				tax2.setText("$" + formatter.format(tax3));
				total2.setText("$" + formatter.format(total3));
		
		}

		
		if (calculate==a.getSource())
		{
			NumberFormat formatter =new DecimalFormat("#0.00");	
			calCost();
			tax2.setText("$" + formatter.format(tax3));
			total2.setText("$" + formatter.format(total3));
		}
		
	}

}

