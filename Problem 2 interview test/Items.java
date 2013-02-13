class Items 
{
	private String quantity;
	private String name;
	private String type;
	private String imported;
	private String price;
	private String tPrice;

	public Items(String quantity, String name, String price)
	{
		this.quantity = quantity;
		this.name = name;
		this.price = price;

	}

	public String getQunatity()
	{
		return quantity;
	}
	
	public String getName()
	{
		return name;
	}

	public String getPrice()
	{
		return price;
	}


}
