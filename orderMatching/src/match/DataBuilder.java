package match;

import java.util.ArrayList;
import java.util.Random;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
public class DataBuilder {
	/**
	 * File: DataBuilder.java
	 * 
	 * @author Bhargav Uppalapati.
	 */
	/**
	 * DataBuilder.java contains random number generator for entries in to order table.
	 */
	public static boolean saved = false;
	
	// test data
	static String[] scrips = {"GOOG", "MS", "FOX", "CNN", "BBC", "AZN"};
	static	String[] sides = {"buy","sell"};
	static	double[] prices = {20.35,32.34,20.33, 30.32, 22.45};
	static	String[] brokers = {"ms","ct","gl","bp","cn"};
	static int[] quantity = {100,50,200,150,250,75};

	// Array list to store Pending orders.
	public static ArrayList<Order> pendingOrdersList = new ArrayList<>();
	
	public static String getScrip() {
		return scrips[getRandomNumber(scrips.length)];
	}
	
	public static String getSide() {
		return sides[getRandomNumber(sides.length)];
	}
	
	public static double getPrice() {
		return prices[getRandomNumber(prices.length)];
	}
	
	public static String getBroker() {
		return brokers[getRandomNumber(brokers.length)];
	}
	
	public static int getQuantity() {
		return quantity[getRandomNumber(quantity.length)];
	}
	
	public static int getRandomNumber(int limit) {
		Random r = new Random();
		return r.nextInt(limit);
	}
	
	public static Order createNewOrder() {
		return new Order(getPrice(),System.currentTimeMillis(),getQuantity(),getBroker(),getSide(),getScrip());
	}
	
	public static void buildPendingOrders(int numOrders) {
		if(!saved) {
			// scrip, side, price, time, broker, status
			for(int i=0; i < numOrders; i++) {
				Order o = new Order(getPrice(),System.currentTimeMillis(),getQuantity(),getBroker(),getSide(),getScrip());
				//System.out.println(o.toString());
				pendingOrdersList.add(o);
				try {
					Thread.sleep(100);
				} catch(InterruptedException ie) {
					System.out.println(ie.getMessage());
				}
			}
			// debug
			System.out.println(pendingOrdersList);
			saveToDB();
			saved = true;
		}
	}
	
	private static void saveToDB() {
		// save via hibernate to MySQL DB
		SessionFactory factory = getSessionFactory();
		Transaction tx = null;
		Session ssn = null;
		for(int i=0; i<pendingOrdersList.size(); i++) {
			try {
				ssn = factory.openSession();
				tx = ssn.beginTransaction();
				Order o = pendingOrdersList.get(i);
				ssn.save(o);
				tx.commit();
			} catch(HibernateException he) {
				tx.rollback();
			} finally {
				ssn.close();
			}
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return new Configuration().configure().addAnnotatedClass(Order.class).buildSessionFactory();
	}
		
}
