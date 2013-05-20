package POS_System;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
	
	private Receipt receipt;
	
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

		start();
		
	}
	/**
	 * Method start starts the activity
	 */
	private void start()
	{
		filePicker();
		display();
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
	private  void filePicker()
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
			 receipt = new Receipt(file);
			 
		}
		 else
        {
            JOptionPane.showMessageDialog(null, "Open File dialog canceled");
        }
	}
	
	
	/**
	 * Method display sets the text of the jTextArea
	 */
	private void display()
	{
		purchases.append(receipt.toString());
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
			start();
		}
	}
	/**
	 * Method reset is used to reset all the values and text area
	 */
	private static void reset()
	{
		purchases.setText("");
	}
	

}
