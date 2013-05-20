package POS_System;

import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


/**
 * @author David Pinheiro
 * @version 1.0 02/8/2013
 */

class SalesTax 
{ 
	// tax values
	private final BigDecimal SALES_TAX = new BigDecimal(".10");
	private final BigDecimal IMPORT_TAX = new BigDecimal(".05");
	// total variables
	private BigDecimal taxTotal = new BigDecimal("0.00");
	private BigDecimal totalCost = new BigDecimal("0.00");
	private BigDecimal taxMulitiplyer, subTotal;
	
	//constructor
	public SalesTax(int quantity, BigDecimal price, boolean exempt, boolean imported)
	{
		taxMulitiplyer = taxPercent(exempt, imported);
		subTotal = subTotalCalculation(quantity, price);
		taxTotal = taxAmount(price, taxMulitiplyer); 
		totalCost= totalCalculations(quantity, price, taxTotal);
	}
	/**
	 * Method getTaxTotal returns the value of taxTotal
	 * @return the value of the total amount of tax to pay
	 */
	public  BigDecimal getTaxTotal()
	{
		return taxTotal;
	}
	
	/**
	 * Method getTotal returns the value of total
	 * @return the value of the total amount to pay
	 */
	public  BigDecimal getTotal()
	{
		return totalCost;
	}
	
	/**
	 * Method totalCalculations calculates the total cost after tax and quantity of the product
	 * @param int value for the quantity of the product purchased 
	 * @param BigDecimal value for the shelf price of the product 
	 * @param BigDecimal value for the tax of the product purchased
	 * @return BigDecimal value for the costs of the product after taxes
	 */
	private BigDecimal totalCalculations(int quantity, BigDecimal price, BigDecimal tax  )
	{
		// calculates the tax and total of the purchase
		BigDecimal subTotal = price.multiply(new BigDecimal(quantity));
		BigDecimal total = subTotal.add(tax);
		return total ;
		
	}
	/**
	 * Method subTotal multiplies the quantity by the price of the product 
	 * @param int value of the quantity to be purchased
	 * @param BigDecimal value for the shelf price of the product
	 * @return BigDecimal value of subTotal of the product
	 */
	private BigDecimal subTotalCalculation(int quantity, BigDecimal price)
	{
		BigDecimal subTotal = price.multiply(taxMulitiplyer);
		return subTotal;
	}
	/**
	 * Method taxAmount multiplies the sub total by the tax percent  
	 * @param BigDecimal value of the sub total for the product
	 * @param BigDecimal value for the tax percent of the product
	 * @return BigDecimal value of the amount of tax to pay for the product
	 */
	private  BigDecimal taxAmount(BigDecimal subTotal, BigDecimal taxMulitiplyer )
	{
		BigDecimal tax = subTotal.multiply(taxMulitiplyer);
		return round(tax);
	}
	/**
	 * Method round rounds up the tax to the nearest .05
	 * @param BigDecimal value of the tax to be rounded
	 * @return BigDecimal value of the rounded tax amount
	 */
	private BigDecimal round (BigDecimal num)
	{
		BigDecimal round =new BigDecimal(Math.ceil(num.doubleValue() * 20) / 20);
		DecimalFormat df = new DecimalFormat("#.00");
		return new BigDecimal(df.format(round));
	}

	/**
	 * Method taxPercent checks if the product is imported and if its exempted and calculates the 
	 * tax percentage owing   
	 * @param boolean import
	 * @param boolean exempt
	 * @return BigDecimal value of the tax percent to pay
	 */
	private  BigDecimal taxPercent(boolean exempted , boolean imported )
	{
		BigDecimal taxValue = new BigDecimal(0.00);
		taxValue = (imported)?taxValue.add(IMPORT_TAX):taxValue.add(new BigDecimal(0.00));
		taxValue = (exempted)?taxValue.add(new BigDecimal(0.00)):taxValue.add(SALES_TAX);
		return taxValue;
	}
	
}
