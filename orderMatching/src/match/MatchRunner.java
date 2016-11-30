package match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class MatchRunner {

	public static final int TOLERANCE = 100;
	public static final int NEWORDERS = 10;
	public static final int PENDINGORDERS = 50;
	public static final String BUY = "buy";
	public static final String SELL = "sell";
	
	private static ArrayList<Order> buyQueList = new ArrayList<>();
	private static ArrayList<Order> sellQueList = new ArrayList<>();
	
	public static void prioritize(List<Order> list) {
		Collections.sort(list, new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				if(o1.getScrip().equals(o2.getScrip())) {
					if( (o1.getTime() - o2.getTime()) <= TOLERANCE ) {
						if(o1.getPrice() == o2.getPrice()) {
							return o2.getQuantity() - o1.getQuantity();
						}
					}
				}
				return 0;
			}
		});		
	}
	
	public static void main(String[] args) {
		// build db, one time operaton
		DataBuilder.buildPendingOrders(PENDINGORDERS);
		
		// build new orders
		for(int i=0; i<NEWORDERS; i++) {
			Order o = DataBuilder.createNewOrder();
			if(o.getSide().equalsIgnoreCase(BUY)) {
				buyQueList.add(o);
			} else {
				sellQueList.add(o);
			}
		}
		
		// prioritize for buy 
		prioritize(buyQueList);
		prioritize(sellQueList);
		
		// convert to Queue
		Queue buyQue = new LinkedList<Order>(buyQueList);
		Queue sellQue = new LinkedList<Order>(sellQueList);
		
		// match using threads each buy against sell in the pending order list
		
		Matcher m1 = new Matcher(buyQue, true);
		Matcher m2 = new Matcher(sellQue, false);
		
		// set ques according to thread side
		m1.setSellQue(sellQue);
		m2.setBuyQue(buyQue);
		
		// debug
		System.out.println(m1.getDbList());
		System.out.println(m2.getDbList());

		// start matching	
		m1.start();
		m2.start();
		
	}

}
