package PIT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aggregator {
	Map<String, ArrayList<String>> routingTable = new HashMap<String,ArrayList<String>>();
	
	public void addName(String ipSourceName, String ipDestinationName) {
		
		//if routingTable has the ip soruce, then just add this path
		if(routingTable.containsKey(ipSourceName)) {
			ArrayList<String> destList = routingTable.get(ipSourceName);
			if(destList.contains(ipDestinationName)) {
				//do nothing
				System.out.println("Path already exists");
			}
			else {
				destList.add(ipDestinationName);
			}
			routingTable.put(ipSourceName, destList);
		}
		//else just create a new key value pair
		else {
			ArrayList<String> destList = new ArrayList<String>();
			destList.add(ipDestinationName);
			routingTable.put(ipSourceName, destList);
		}
		
	}
	
	public ArrayList<String> getAllDestForName(String ipSourceName) {
		if(routingTable.containsKey(ipSourceName)) {
			return routingTable.get(ipSourceName);
		}else {
			System.out.println("This name is not registered. Please Add name first.");
		}
		return null;
	}
	
	public List<String> getNameAddress(String path, MaskTableUtil maskUtil) {
		List<String> returnedIpsWithPort = new ArrayList<String>();
		String[] splitPath = path.split("/");
		String lastNode = splitPath[splitPath.length-2];
		System.out.println("this node has following APIS : "+splitPath[splitPath.length-1]);
		System.out.println(lastNode);
		if(routingTable.containsKey(lastNode)) {
			System.out.println("--"+maskUtil.getMaskMap());
			MaskTable ipMask = maskUtil.getMaskMap().get(lastNode);
			System.out.println(ipMask);
			for(String port : ipMask.getSourceNodePort()) {
				returnedIpsWithPort.add(ipMask.getSourceNodeIp()+":"+port);
			}
			return returnedIpsWithPort;
		}else {
			System.out.println("Unkown Node");
		}
		return null;	
	}
}
