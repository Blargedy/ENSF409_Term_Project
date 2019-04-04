import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Shop {
	
	private Inventory theInventory;

	private ArrayList <Supplier> supplierList;
	
//	public Shop (Inventory inventory, ArrayList <Supplier> suppliers) {
//		theInventory = inventory;
//		supplierList = suppliers;
//	}

	public Shop(){
		supplierList = new ArrayList<>();
		readSuppliers();
		theInventory = new Inventory(supplierList);
	}

	private void readSuppliers() {
		try {
			FileReader fr = new FileReader("suppliers.txt");
			BufferedReader br = new BufferedReader(fr);

			String line = "";
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(";");
				supplierList.add(new Supplier(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3]));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Inventory getTheInventory () {
		return theInventory;
	}

	public void setTheInventory (Inventory inventory) {
		theInventory = inventory;
	}

	public ArrayList<Supplier> getSupplierList (){
		return supplierList;
	}

	public void setSupplierList (ArrayList <Supplier> suppliers){
		supplierList = suppliers;
	}
	

	public String listAllItems() {
		return theInventory.toString();
		
	}
	public boolean decreaseItem (String name) {
		boolean status = false;
		Item item = getItem(name);
		if(item == null)
			return status;
		else {
			int currentQuantity = item.getItemQuantity();
			if (currentQuantity <= 0)
				return status;
			else{
				item.setItemQuantity(currentQuantity - 1);
				status = true;
			}
		}
		return status;
	}

	public void listAllSuppliers() {
		for (Supplier s: supplierList) {
			System.out.println(s);
		}
	}

	public Supplier searchForSupplier(String supplierName){
		for (Supplier i: supplierList) {
			if (i.getSupName() == supplierName)
				return i;
		}
		return null;
	}

	public Supplier searchForSupplier(int supplierId){
		for (Supplier i: supplierList) {
			if (i.getSupId() == supplierId)
				return i;
		}
		return null;
	}

	public Item getItem(String name) {
		Item theItem = theInventory.searchForItem(name);
		return theItem;
	}

	public Item getItem(int id) {
		return theInventory.searchForItem(id);
	}

	public int getItemQuantity(String name) {
		return theInventory.getItemQuantity(name);
	}
}
