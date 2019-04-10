import java.io.Serializable;

public class OrderLine implements Serializable {
	
	private Item theItem;
	private int orderQuantity;
	
	public OrderLine (Item item, int quantity) {
		theItem = item;
		setOrderQuantity(quantity); 
		
	}

	public Item getTheItem() {
		return theItem;
	}

	public void setTheItem(Item theItem) {
		this.theItem = theItem;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String toString (){
		return  "Item description:\t\t" + theItem.getItemName() + "\n" +
				"Item ID: " + theItem.getItemId()+ "\n" +
				"Amount ordered:\t\t" + orderQuantity + "\n" +
				"Supplier:\t\t" + theItem.getSupplier();
	}
}
