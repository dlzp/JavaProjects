package Puzzle;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.Random;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Puzzle extends JFrame implements ActionListener
{
	private JPanel centerPanel;
	private JButton button;
	private JLabel label;
	private Image source;
	private Image image;
	int[][] pos;
	int width,height;
	
	public Puzzle()
	{
		// position of the image parts
		pos = new int[][] {
				{0,1,2},
				{3,4,5},
				{6,7,8},
				{9,10,11}
				};
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(4,4,0,0));
		
		retireveImage();
				
		//adds a invisible component that takes up the same amount of space
		add(Box.createRigidArea(new Dimension(0,5)), BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		
		createPieces();
		
		frameDefaults();
		//scramble();
		
		
				
		}
	private void scramble()
	{
		Random randomNum = new Random();
		for (int i = 0; i < 11; i++) 
		{
			button = (JButton)centerPanel.getComponent(i);
			centerPanel.add(button,randomNum.nextInt(10));
		}
		
	}
	
	private void retireveImage()
	{
		// gets the image
		ImageIcon picture = new ImageIcon(Puzzle.class.getResource("Sid.jpg"));
		source = picture.getImage();
		width = picture.getIconWidth();
		height = picture.getIconHeight();
	}

	private void frameDefaults()
	{
		setSize(width, height);
		setTitle("Puzzle");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		//gets source of button click
		JButton button =(JButton) e.getSource();
		Dimension size = button.getSize();
		
		// gets the x,y coordinates of the button that was hit and an empty label
		int labelX = label.getX();
		int labelY = label.getY();
		int buttonX = button.getX();
		int buttonY = button.getY();
		
		// gets the index of the button from the array we created above
		int buttonPosX = buttonX/size.width;
		int buttonPosY = buttonY/size.height;
		int buttonIndex = pos[buttonPosY][buttonPosX];
		
		
		//possible positions around label
		boolean buttAboveBlank= (labelX==buttonX && (labelY-buttonY)==size.height);
		boolean buttBelowBlank= (labelX==buttonX && (labelY-buttonY)==-size.height);
		boolean buttLeftBlank= (labelY==buttonY && (labelX-buttonX)==size.width);
		boolean buttRightBlank= (labelY==buttonY && (labelX-buttonX)==-size.width);
		
		//above blank space
		if (buttAboveBlank)
		{
			swap(buttonIndex,buttonIndex+3,button);
		}
		//below blank space
		if (buttBelowBlank)
		{
			swap(buttonIndex,buttonIndex-3,button);
		}
		
		// left of the blank space
		if (buttLeftBlank)
		{
			swap(buttonIndex,buttonIndex+1,button);
		}
		// right of the blank space
		if (buttRightBlank)
		{
			swap(buttonIndex,buttonIndex-1,button);
		}
		
		
	}
	private void swap(int buttonIndex, int labelIndex, JButton button )
	{
		centerPanel.remove(buttonIndex);
		centerPanel.add(button,labelIndex);
		centerPanel.add(label,buttonIndex);
		centerPanel.validate();
	}
	
	private void createPieces()
	{
		// this creates the 11 buttons
				for (int i=0; i<4; i++)
				{
					for (int j = 0; j<3; j++)
					{
						//places the label (blank square) in the right most corner
						if (j== 2&&i==3)
						{
							label = new JLabel("");
							centerPanel.add(label);
						}
						else
						{
							// adds all buttons
							button = new JButton();
							button.addActionListener(this);
							centerPanel.add(button);
							
							
							// this crops the image 
							image = createImage( new FilteredImageSource(source.getSource(),
									new CropImageFilter(j*width/3, i*height/4, (width/3)+1,
											height/4)));
							//places cropped image as button 
							button.setIcon(new ImageIcon(image));
						}
					}
					
				}
	}
	
	public static void main (String[] args)
	{
		new Puzzle();
	}
	
}
