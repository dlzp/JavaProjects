package POS_System;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Receipt 
{
	//list to hold products
	private ArrayList <Products> productList = new ArrayList<Products>();
	// variables for the total tax and the total of the purchase 
	private BigDecimal tax = new BigDecimal(0.00);
	private BigDecimal total = new BigDecimal(0.00);
	
	//constructor
	public Receipt (File file)
	{
		getInfo(file);
		calculateTax();
		calculateTotal();
	}
	/**
	 * Method getInfo retrieves all the values from the text file
	 * @param File for the text file with the purchase
	 */
	private void getInfo(File f) 
		{
		 try
			{
				FileReader file = new FileReader(f);
				BufferedReader br = new BufferedReader(file);
				
				readFile(br);
						
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
					
		}
	
	/**
	 * Method readFile gets the values and assigns it to the variables
	 * @param File for the text file with the purchase
	 */
	 private void readFile(BufferedReader br)throws Exception
		{ 
		 String line;
		 if ((line=br.readLine())!=null)
			{
				StringTokenizer token = new StringTokenizer(line, ":");
				int quantity = Integer.parseInt(token.nextToken());
				String name = token.nextToken();
				String type = token.nextToken().toString();
				boolean imported = token.nextToken().equalsIgnoreCase("Imported")?true:false;
				BigDecimal price =new BigDecimal(token.nextToken());
				productList.add(new Products(quantity, name, type, imported, price));
		
				readFile(br);
			}
			
		}
	 // toString
	 public String toString()
	 {
		 StringBuilder sb = new StringBuilder();
		 for(Products product:productList)
		 {
			 sb.append(product.toString());
		 }
		 sb.append("\n");
		 sb.append("Tax: ");
		 sb.append(tax);
		 sb.append("\n");
		 sb.append("Total: ");
		 sb.append(total);
		 return sb.toString();
	 }
	 /**
	  * Method calculateTotal gets the cost from all the products and adds them together
	  */
	 private void calculateTotal()
	 {
		 for(Products product :productList)
		 {
			 total=total.add(product.getTotal());
		 }
		 
	 }
	 /**
	  * Method calculateTax gets the tax from all the products and adds them together
	  */
	 private void calculateTax()
	 {
		 for(Products product :productList)
		 {
			 tax=tax.add(product.getTaxAmount());
		 }
		 
	 }
	 
	 

}
