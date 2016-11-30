package match;

import java.util.List;
import java.util.Queue;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Matcher extends Thread {
	
	private boolean BUY = true;
	private Queue<Order> que;
	private List<Order> dbList;
	private Queue<Order> buyQue;
	private Queue<Order> sellQue;
	
	public Matcher(Queue que, boolean buy) {
		this.que = que;
		this.BUY = buy;
		dbList = getDBList();
	}
	
	public void run() {
		if(BUY) {
			match(sellQue);
		} else {
			match(buyQue);
		}
	}
	
	public synchronized void match(Queue<Order> buyOrSellQue) {
		// match item from que against items from list
		// if match, remove from que, update table row, build map<row_id, list(order_id)>
		for(Order o : que) {
			int count = 0;
			String scrip = o.getScrip();
			double price = o.getPrice();
			for(Order ord : buyOrSellQue) {
				if(ord.getScrip().equalsIgnoreCase(scrip) && ord.getPrice() == price) {
					// match found
					System.out.println(BUY==true?"buy "+count++ : "sell "+count);
				}
			}
		}
	}
	
	private List<Order> getDBList() {
		List<Order> list = null;
		SessionFactory factory = DataBuilder.getSessionFactory();
		String hql = "from Order where side=:which";
		try {
			Session ssn = factory.openSession();
			Transaction tx = ssn.beginTransaction();
			Query query = ssn.createQuery(hql);
			if(BUY) {
				query.setString("which", "buy");
			} else {
				query.setString("which", "sell");
			}
			list = query.list();
		} catch(HibernateException he) {
			System.out.println(he.getMessage());
		}
		return list;
	}

	public boolean isBUY() {
		return BUY;
	}

	public void setBUY(boolean bUY) {
		BUY = bUY;
	}

	public Queue<Order> getQue() {
		return que;
	}

	public void setQue(Queue<Order> que) {
		this.que = que;
	}

	public List<Order> getDbList() {
		return dbList;
	}

	public void setDbList(List<Order> dbList) {
		this.dbList = dbList;
	}

	public Queue<Order> getBuyQue() {
		return buyQue;
	}

	public void setBuyQue(Queue<Order> buyQue) {
		this.buyQue = buyQue;
	}

	public Queue<Order> getSellQue() {
		return sellQue;
	}

	public void setSellQue(Queue<Order> sellQue) {
		this.sellQue = sellQue;
	}
	
}
