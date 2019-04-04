import java.util.ArrayList;

/**
 * @author Mohammed Moshirpour
 * @author Bernard Kleiner
 */

public class Inventory {
	private ArrayList <Item> itemList;
	private Order myOrder;
	
	public Inventory (ArrayList <Item> itemList) {
		this.itemList = itemList;
		myOrder = new Order ();
	}

	public ArrayList <Item> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList <Item> itemList) {
		this.itemList = itemList;
	}

	public void placeOrder (Item theItem){
		OrderLine ol = theItem.placeOrder();
		if (ol !=null){
			myOrder.addOrderLine(ol);
		}
	}

	public int getItemQuantity (String name){
		Item theItem = searchForItem (name);
		if (theItem == null)
			return -1;
		else
			return theItem.getItemQuantity();
	}

	public boolean deleteItem(String name) {
		for (Item i : itemList) {
			if (i.getItemName().equals(name)) {
				itemList.remove(i);
				return true;
			}
		}
		return false;
	}

	public boolean deleteItem(int id) {
		for (Item i : itemList) {
			if (i.getItemId() == id) {
				itemList.remove(i);
				return true;
			}
		}
		return false;
	}

	public void addItem(Item item){
		itemList.add(item);
	}

	public void addItem(int id, String name, int quantity, double price, Supplier supplier){
		Item item = new Item(id, name, quantity, price, supplier);
	}

	public Item searchForItem (String name) {
		for (Item i: itemList) {
			if (i.getItemName().equals(name))
				return i;
		}
		return null;
	}

	public Item searchForItem(int id) {
		for (Item i: itemList) {
			if (i.getItemId() == id)
				return i;
		}
		return null;
	}

	public Order getMyOrder() {
		return myOrder;
	}

	public String toString () {
		String str = "";
		for (Item i: itemList) {
			str += i;
		}
		return str;
	}
}
