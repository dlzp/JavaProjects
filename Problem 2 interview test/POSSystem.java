import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Component.*;
import java.text.*;
import java.io.*;
import java.util.*;
import java.math.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * ThoughtWorks Test Code Problem Two: Sales Tax
 * POSSystem.java
 * Provides the GUI for processing receipt details
 *
 * @author David Pinheiro
 * @version 1.0 02/8/2013
 */

 
public class POSSystem extends JFrame implements ActionListener
{
	//constants
	//frame layout
	private static final int FRAME_WIDTH = 280;
	private static final int FRAME_HEIGHT = 320;
	private static final int X_LOCATION = 425;
	private static final int Y_LOCATION = 100;
	//string constants
	private static final String TITLE ="POS System";
	private static final String lTax ="Sales Tax";
	private static final String lTotal ="Total";
	private static final String[] inputOptions = { "Input 1","Input 2", "Input 3" };
	//reset value
	private static final BigDecimal resetTotals = new BigDecimal("0.00");
		
	//menu bar
	private static JMenuBar menuBar = new JMenuBar();
	private static JMenu file = new JMenu ("File");
	private static JMenuItem quit = new JMenuItem ("Quit");

	//container
	private static Container pos;
	//panels
	private static JPanel panelIn = new JPanel();
	private static JPanel panelOut = new JPanel();
	private static JPanel panel = new JPanel();
	private static JPanel panelBut = new JPanel();
	
	//output area
	private static JTextArea purchases = new JTextArea();
	
	//open Button
	private static JButton open = new JButton("Open");

	// variables for gathering information	
	private static String amount, name, type, imported, sPrice;
	private static BigDecimal taxedPrice;
	
	/**
	 *public constructor used to instantiate the GUI class
	 */
	public POSSystem()
	{
		// creates Frame
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle(TITLE);
		setVisible(true);
		setLocation(X_LOCATION, Y_LOCATION);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//gets content pane 
		pos= this.getContentPane();
		
		//sets layout for panels
		panel.setLayout(new BorderLayout());
		panelOut.setLayout(new GridLayout());
		panel.add(panelIn, BorderLayout.NORTH);
		panel.add(panelOut, BorderLayout.CENTER);
		
		//adds all panels to content pane
		pos.add(panel);

		//calls methods to fill the menu and panels
		menu();
		inputSelGUI();
		receptGUI();
		

		// Sets Menubar
		setJMenuBar(menuBar);

		// Adds listener for all events
		quit.addActionListener(this);
		open.addActionListener(this);

		filePicker();
		
	}
	
	/**
	 *Method menu creates menu bar
	 */
	private static void menu()
	{
		menuBar.add(file);
		file.addSeparator();
		file.add(quit);
	}

	/**
	 * Method inputSelGUI creates the input options in the GUI
	 */
	private static void inputSelGUI()
	{
		panelIn.setBorder(BorderFactory.createTitledBorder("Chose Your Input Test Case"));
		panelIn.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelIn.add(open);
		
		

	}
	
	/**
	 * Method receptGUI creates the output display in the GUI
	 */
	private static void receptGUI()
	{
		panelOut.setBorder(BorderFactory.createTitledBorder("Output"));
		panelOut.setLayout(new GridLayout(1,0));
		purchases.setEditable(false);
		purchases.setCursor(null);
		purchases.setOpaque(false);
		purchases.setFocusable(false);
		panelOut.add(purchases);

	}
	/**
	 * Method filePicker opens a fileChooser GUI to select the text file 
	 */
	private static void filePicker()
	{
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		File file;
		
		// Text file filter
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (.txt)", "txt");
		chooser.setFileFilter(filter);
		int status = chooser.showOpenDialog(null);
		
		// checks is a file was selected
		if (status == JFileChooser.APPROVE_OPTION) 
		{
			 file = chooser.getSelectedFile();
			 getInfo(file);
		}
		 else
        {
            JOptionPane.showMessageDialog(null, "Open File dialog canceled");
        }
	}
	/**
	 * Method getInfo retrieves all the values from the text file
	 * @param string value for the name of the file
	 */
	private static void getInfo(File f) 
	{
	
		try
		{
			FileReader file = new FileReader(f);
			
			BufferedReader br = new BufferedReader(file);

			String line;
						
			while ((line = br.readLine()) !=null)
			{
				StringTokenizer token = new StringTokenizer(line, ":");
				amount = token.nextToken();
				name = token.nextToken();
				type = token.nextToken().toString();
				imported = token.nextToken();
				sPrice = token.nextToken();
				
				// calls the SalesTax class and calculations method
				taxedPrice = SalesTax.calculations(amount,sPrice,type,imported);
				display(amount, name, taxedPrice);
				
			}
			
			br.close();
			
		}
		catch (IOException e)
		{
			System.out.println("IO Exception " + e);
		}
		catch(Exception e)
		{
			System.out.println("Execption " +e );
		}
	
		// retrieves the tax total and the total from the salesTax class
		BigDecimal taxTotal = SalesTax.getTaxTotal();
		BigDecimal total = SalesTax.getTotal();
		
		display(taxTotal,total);
	
	}
	/**
	 * Method display sets the text of the jTextArea
	 * @param String value for the amount of the product
	 * @param String value for the name of the product
	 * @param BigDecimal value for the price after tax of the product
	 */
	private static void display(String amount, String name, BigDecimal price)
	{
		purchases.append(amount+" "+ name+": $"+ price.toString()+"\n");
	}
	
	/**
	 * Method display is a overloaded method it appends the text of the jTextArea to input the tax total and total
	 * @param BigDecimal value for the total amount of tax to pay
	 * @param BigDecimal value for the total amount to pay
	 */
	private static void display(BigDecimal tax, BigDecimal fPrice)
	{
		purchases.append("\n"+lTax+": $"+tax+ "\n"+lTotal+": $"+ fPrice);
	}

	/**
	 * Method actionPerformed is abstract method overwritten gives the actions to the menu bar
	 * @param ActionEvent used to identify the source of the event
	 */
	public void actionPerformed(ActionEvent a)
	{
		if (quit == a.getSource())
		{
			System.exit(0);
		}
		if(open==a.getSource())
		{
			reset();
			filePicker();
		}
	}
	/**
	 * Method reset is used to reset all the values and text area
	 */
	private static void reset()
	{
		purchases.setText("");
		SalesTax.setTaxTotal(resetTotals);
		SalesTax.setTotal(resetTotals);
	}
	

}
