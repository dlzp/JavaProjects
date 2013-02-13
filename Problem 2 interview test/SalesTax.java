import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


/**
 * ThoughtWorks Test Code Problem Two: Sales Tax
 * SalesTax.java
 * Provides the calculations for processing recept details
 *
 * @author David Pinheiro
 * @version 1.0 02/8/2013
 */

class SalesTax 
{ 
	// tax values
	private static final BigDecimal SALES_TAX = new BigDecimal(".10");
	private static final BigDecimal IMPORT_TAX = new BigDecimal(".05");
	// total variables
	private static BigDecimal taxTotal = new BigDecimal("0.00");
	private static BigDecimal total = new BigDecimal("0.00");
	
	/**
	 * Method getTaxTotal returns the value of taxTotal
	 * @return the value of the total amount of tax to pay
	 */
	public static BigDecimal getTaxTotal()
	{
		return taxTotal;
	}
	
	/**
	 * Method getTotal returns the value of total
	 * @return the value of the total amount to pay
	 */
	public static BigDecimal getTotal()
	{
		return total;
	}
	
	/**
	 * Method setTaxTotal sets the value of taxTotal
	 * @param BigDecimal value for which taxTotal will be set to 
	 */
	public static void setTaxTotal(BigDecimal tax)
	{
		 taxTotal=tax;
	}
	
	/**
	 * Method setTotal sets the value of total
	 * @param BigDecimal value for which total will be set to 
	 */
	public static void setTotal(BigDecimal tot)
	{
		total= tot;
	}
	
	/**
	 * Method calculations calculates the cost of the product
	 * @param String value for the amount of the product purchased 
	 * @param String value for the shelf price of the product 
	 * @param String value for the type of the product purchased
	 * @param String value identifying if the product is imported
	 * @return BigDecimal value for the costs of the product after taxes
	 */
	public static BigDecimal calculations(String amount, String sPrice, String type, String imported  )
	{
		BigDecimal quant = new BigDecimal(amount);
		BigDecimal price = new BigDecimal(sPrice).multiply(quant);;
		BigDecimal finPrice = new BigDecimal ("0.00"); 
		BigDecimal tax= new BigDecimal("0.00");
		BigDecimal tTax;

		int sCode;
		// identifies what taxes to apply to the cost of the product
		sCode = statusCode(type,imported);
		
		switch (sCode)
		{
		case 1: tax =price.multiply(SALES_TAX);
				break;
		case 2: tax = ((price.multiply(SALES_TAX)).add(price.multiply(IMPORT_TAX)));
				break;
		case 3: tax = price.multiply(IMPORT_TAX);
				break;
		
		case 4: break;
		
		}
		// calculates the tax and total of the purchase
		tTax = new BigDecimal(round(tax)); 
		taxTotal = taxTotal.add(tTax);
		finPrice = price.add(tTax);
		total = total.add(finPrice);
		return finPrice;
		
	}
	/**
	* Method round rounds up the tax to the nearest .05
	* @param BigDecimal value of the tax to be rounded
	* @return BigDecimal value of the rounded tax amount
	*/
	private static String round (BigDecimal num)
	{
		BigDecimal round =new BigDecimal(Math.ceil(num.doubleValue() * 20) / 20);
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(round);
	}

	private static int statusCode(String type, String imported)
	{
		int sCode;
						
		if (type.equalsIgnoreCase("Book") || type.equalsIgnoreCase("Food")|| type.equalsIgnoreCase("Medical"))
			{								
				if(imported.equalsIgnoreCase("Imported"))
				{
					sCode = 3;
				}
				else
				{
					sCode = 4;
				}
				
			}
			else if (imported.equalsIgnoreCase("Imported"))
			{
				sCode = 2;
				
			}
			
			else
			{
				sCode=1;
				
				
			}
		return sCode;

	}
	
}
