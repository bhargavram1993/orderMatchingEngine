package match;

import javax.persistence.*;

@Entity
@Table(name="orders")
public class Order {
	
	public Order() { }
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "time")
	private long time;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "broker")
	private String broker;
	
	@Column(name = "side")
	private String side;
	
	@Column(name = "scrip")
	private String scrip;
	
	@Column(name = "type")
	private String type = "GFD";
	
	@Column(name = "status")
	private String status = "pending";
	
	public Order(double price, long time, int quantity, String broker,
			String side, String scrip) {
		super();
		this.price = price;
		this.time = time;
		this.quantity = quantity;
		this.broker = broker;
		this.side = side;
		this.scrip = scrip;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getScrip() {
		return scrip;
	}

	public void setScrip(String scrip) {
		this.scrip = scrip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", price=" + price + ", time=" + time
				+ ", quantity=" + quantity + ", broker=" + broker + ", side="
				+ side + ", scrip=" + scrip + ", type=" + type + ", status="
				+ status + "]";
	}
	
}
