import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class Order implements Serializable {
	private Date today;
	private int orderId;
	private ArrayList <OrderLine> orderLines;
	
	public Order () {
		today = Calendar.getInstance().getTime();
		orderLines = new ArrayList<>();
	}
	
	public void addOrderLine (OrderLine ol) {
		orderLines.add(ol);
	}
	
	public ArrayList <OrderLine> getOrderLines (){
		return orderLines;
	}

	public void setOrderLines (ArrayList <OrderLine> lines){
		orderLines = lines;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String toString (){
		String order = "Order Date: " + today.toString() + "\n\n";
		String str = "";
		for (OrderLine ol: orderLines) {
			str += ol;
			str += "------------------------\n";
		}
		if (str == "")
			str = "here are currently no order lines";
		
		order += str;
		order += "\n";
		return order;
	}
}
