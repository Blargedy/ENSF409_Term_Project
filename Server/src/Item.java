/**
 * @author Mohammed Moshirpour
 * @author Bernard Kleiner
 */

public class Item {
	
	private int itemId;
	private String itemName;
	private int itemQuantity;
	private double itemPrice;
	private boolean alreadyOrdered;
	private Supplier supplier;
	private static final int ORDERQUANTITY = 40;
	private static final int MINIMUM_NUMBER = 20;
	
	
	public Item (int id, String name, int quanitiy, double price, Supplier supplier) {
		this.itemId = id;
		this.itemName = name;
		this.itemQuantity = quanitiy;
		this.itemPrice = price;
		this.supplier = supplier;
		this.alreadyOrdered = false;
	}

//	public boolean decreaseItemQuantity () {
//		if (itemQuantity > 0) {
//			itemQuantity--;
//		    return true;
//		}
//		else
//			return false;
//	}

	public OrderLine placeOrder (){
		OrderLine ol;
		if (getItemQuantity() < MINIMUM_NUMBER && alreadyOrdered == false){
			ol = new OrderLine (this, ORDERQUANTITY);
			alreadyOrdered = true;
			return ol;
		}
	    return null;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public void setSupplier(Supplier sup) {
		supplier = sup;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public boolean getAlreadyOrdered() {
		return this.alreadyOrdered;
	}

	public void setAlreadyOrdered(boolean alreadyOrdered) {
		this.alreadyOrdered = alreadyOrdered;
	}

	public String toString () {
		return "Item ID: " + itemId + ", Item Name: " + itemName + ", Item Quantity: " +
				itemQuantity + "\n";
	}
}
