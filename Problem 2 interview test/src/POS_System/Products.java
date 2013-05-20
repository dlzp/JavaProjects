package POS_System;

import java.math.BigDecimal;

public class Products 
{
	private int quantity;
	private String name;
	private String type;
	private boolean imported;
	private boolean taxExempted;
	private BigDecimal price;
	private BigDecimal taxAmount;
	private BigDecimal total;
	
	// constructor
	public Products(int quantity, String name, String type, boolean imported,BigDecimal price) 
	{
		this.quantity=quantity;
		this.name = name;
		this.type = type;
		this.imported = imported;
		this.price = price;
		taxExempted = checkType();
		SalesTax calculations = new SalesTax(quantity, price, taxExempted,imported);
		taxAmount = calculations.getTaxTotal() ;
		total = calculations.getTotal();
	}
	
	/**
	 * Method getTaxAmount returns the value of  total tax owing
	 * @return the value of the total amount of tax to pay
	 */
	public BigDecimal getTaxAmount() 
	{
		return taxAmount;
	}
	/**
	 * Method getTotal returns the value of total owing
	 * @return the value of the total owing
	 */
	public BigDecimal getTotal()
	{
		return total;
	}
	
	/**
	 * Method checkType returns if the product is exempt from the sales taxes
	 * @return boolean value of exempt
	 */
	private boolean checkType()
	{		
		boolean book = type.equalsIgnoreCase("Book");
		boolean food = type.equalsIgnoreCase("Food");
		boolean medical = type.equalsIgnoreCase("Medical");
		
		return (book||food||medical);
	}
	
	// toString of the product
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(quantity);
		sb.append(" ");
		sb.append(name);
		sb.append(": ");
		sb.append(total);
		sb.append("\n");
		return sb.toString();
	}
	

}
