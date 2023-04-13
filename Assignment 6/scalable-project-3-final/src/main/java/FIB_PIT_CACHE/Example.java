package FIB_PIT_CACHE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Example {
	public static void main(String[] args) throws IOException {
		System.out.println("=======================================================");
		System.out.println("Example 1 - Building a node");
		Aggregator agg = new Aggregator();
		System.out.println("Calling build node which internally calls advertise");
		agg.buildNode("192.168.0.1", "8080", "india", "ireland", "192.168.0.0", "8080");
		System.out.println("\n\n=======================================================");
		System.out.println("Example 2 - Getting All Paths");
		agg.getAllDestForName();
		System.out.println("\n\n=======================================================");
		System.out.println("Example 3 - Send a Message - advertise routing table");
		Sender send = new Sender();
		send.createMessage("advertise", "india", agg.getRoutingTable().toString(), agg);
		
		
		System.out.println("\n\n=======================================================");
		System.out.println("Example 3 - Simulating multiple nodes");
		Aggregator agg2 = new Aggregator();
		Map<String, IpList> routingTable = new HashMap<String,IpList>();
		
		System.out.println("Creating India Node");
		IpList firstIp = new IpList();
		firstIp.setPathName("India");
		firstIp.setCost(1);
		firstIp.setSelfNodeIp("192.168.0.0");
		firstIp.setSelfNodePort("8080");
		
		System.out.println("Creating Ireland Node");
		IpList secondIp = new IpList();
		secondIp.setPathName("Ireland");
		secondIp.setCost(0);
		secondIp.setSelfNodeIp("192.168.0.1");
		secondIp.setSelfNodePort("8080");
		
		routingTable.put(firstIp.getPathName(), firstIp);
		routingTable.put(secondIp.getPathName(), secondIp);
		
		System.out.println("Creating China Node and connecting it to Ireland");
		agg2.buildNode("192.168.0.1", "8080", "Ireland", "China", "192.168.0.3", "8080");
		
		System.out.println("\nGET Routing Table in From Ireland Node:\n");
		agg2.advertise(routingTable);
		
		System.out.println("\nGET All Current Paths:\n");
		agg2.getAllDestForName();
		
		System.out.println("Now adding Israel Node");
		IpList thirdIp = new IpList();
		thirdIp.setPathName("Israel");
		thirdIp.setCost(2);
		thirdIp.setSelfNodeIp("192.168.0.4");
		thirdIp.setSelfNodePort("8080");
		routingTable.put(thirdIp.getPathName(), thirdIp);
		
		System.out.println("\nGET All Current Paths:\n");
		agg2.getAllDestForName();
		
		System.out.println("\nGET routing Path from Google:\n");
		agg2.advertise(routingTable);
		agg2.getAllDestForName();
		
		
		System.out.println("\n\n=======================================================");
		System.out.println("Example 4 - Making use of Cache");
		agg2.getNameAddress("/Israel");
		agg2.addToCacheStore("/Israel","This");
		agg2.addToCacheStore("/India","is");
		agg2.addToCacheStore("/China","a");
		agg2.addToCacheStore("/Ireland","Cache Store");
		agg2.addToCacheStore("/Ireland","More can be added");
		
		System.out.println(agg2.getFromCacheStore("/Ireland"));
		
		System.out.println("\n\n=======================================================");
		System.out.println("Example 5 - Making use of PIT");
		System.out.println("Example 5 - Going from China(0) to India(2) through Ireland");
		agg2.addToCacheStore("/India","has more");
		agg2.getData("India");
		System.out.println("Printing PIT table: ");
		System.out.println(agg2.getPendingInterestTable());
	}
}