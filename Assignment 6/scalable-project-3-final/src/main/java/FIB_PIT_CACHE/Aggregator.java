package FIB_PIT_CACHE;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aggregator {
	Map<String, IpList> routingTable = new HashMap<String, IpList>();
	Map<String, String> pendingInterestTable = new HashMap<String, String>();
	Map<String,ArrayList<String>> cacheStore = new HashMap<String, ArrayList<String>>();

	public void addName(String ipSourceName, IpList ipList) {
		routingTable.put(ipSourceName, ipList);
	}

	public ArrayList<IpList> getAllDestForName() {
		ArrayList<IpList> ipList = new ArrayList<IpList>();
		for (Map.Entry<String, IpList> entry : routingTable.entrySet()) {
			System.out.println(entry.getKey()+","+entry.getValue());
			ipList.add(entry.getValue());
		}
		return ipList;
	}

	public String getNameAddress(String path) {
		String[] splitPath = path.split("/");
		for (String ipName : splitPath) {
			System.out.println(ipName);
			if(ipName!=null && !ipName.equalsIgnoreCase("") && routingTable.containsKey(ipName)) {
				System.out.println("Found Node");
				System.out.println(routingTable.get(ipName).getSelfNodeIp() + ":" + routingTable.get(ipName).getSelfNodePort());
				return routingTable.get(ipName).getSelfNodeIp();
			} else {
				//System.out.println("Not found!");
			}
		}
		
		return null;
	}
	
	private void forwardToOtherNodes(Map<String, IpList> routingTableRecieved, IpList ipObj, MulticastSocket socket) throws IOException {
		System.out.println("Advertising to node: " + ipObj.getSelfNodeIp() + ":" + ipObj.getSelfNodePort() + " /" + ipObj.getPathName() 
									+ " COST = " + ipObj.getCost());
		//(ip|port|name)
		if (ipObj.getSelfNodeIp()!=null || ipObj.getSelfNodePort()!=null || ipObj.getPathName()!=null){
			for (Map.Entry<String, IpList> entry : routingTableRecieved.entrySet()) {
				String sendString = "UPDATE "+entry.getValue().getSelfNodeIp() + " " + entry.getValue().getSelfNodePort() + " " 
						+ entry.getValue().getPathName()+ " " + entry.getValue().getCost();
				byte[] buff = sendString.getBytes();
				System.out.println(new String(buff));
				DatagramPacket packet = new DatagramPacket(buff, buff.length, InetAddress.getByName(entry.getValue().getSelfNodeIp())
						, Integer.parseInt(entry.getValue().getSelfNodePort()));
				socket.send(packet);
			}
		}
	}

	public void advertise(Map<String, IpList> routingTableRecieved) {
		for (Map.Entry<String, IpList> entry : routingTableRecieved.entrySet()) {
			if (routingTable.containsKey(entry.getKey())) {
				continue;
			} else {
				IpList ipToBeAdded = entry.getValue();
				ipToBeAdded.setCost(entry.getValue().getCost() + 1);
				routingTable.put(entry.getKey(), ipToBeAdded);
			}
		}

		for (Map.Entry<String, IpList> entry : routingTableRecieved.entrySet()) {
			if (entry.getValue().getCost() == 1)
				forwardToOtherNodes(entry.getValue());
		}

	}

	private void forwardToOtherNodes(IpList ipObj) {
		
		System.out.println("Advertising to node: " + ipObj.getSelfNodeIp() + " , /" + ipObj.getPathName());
	}

	public void buildNode(String connectToNodeIp, String connectToNodePort, String connectToPath, String myNodeName,
			String myIp, String myPort) {
		// Called after this connects to the other source for the first time

		IpList firstIp = new IpList();
		firstIp.setPathName(myNodeName);
		firstIp.setCost(0);
		firstIp.setSelfNodeIp(myIp);
		firstIp.setSelfNodePort(myPort);

		IpList secondIp = new IpList();
		secondIp.setPathName(connectToPath);
		secondIp.setCost(1);
		secondIp.setSelfNodeIp(connectToNodeIp);
		secondIp.setSelfNodePort(connectToNodePort);

		routingTable.put(myNodeName, firstIp);
		routingTable.put(connectToPath, secondIp);

		advertise(routingTable);
	}

	public Map<String, IpList> getRoutingTable() {
		return routingTable;
	}

	@Override
	public String toString() {
		return "routingTable=" + routingTable ;
	}
	
	public void addToCacheStore(String name, String data) {
		if(cacheStore.containsKey(name)) {
			ArrayList<String> dataStore = cacheStore.get(name);
			dataStore.add(data);
			cacheStore.put(name, dataStore);
			System.out.println("data added!");
		}else {
			ArrayList<String> dataStore = new ArrayList<String>();
			dataStore.add(data);
			cacheStore.put(name, dataStore);
		}
		
	}
	
	public List<String> getFromCacheStore(String name) {
		if(cacheStore.containsKey(name)) {
			System.out.println("Found data! ");
			System.out.println(cacheStore.get(name));
			return cacheStore.get(name);
		}else {
			System.out.println("Not Found");
			return null;
		}
	}
	
	public ArrayList<String> getNextHops(){
		ArrayList<String> nextHops = new ArrayList<String>();
		for (Map.Entry<String, IpList> entry : routingTable.entrySet()) {
			if(entry.getValue().getCost()==1) {
				System.out.println(entry.getValue().getPathName());
			}	
		}
		return nextHops;
	}
	
	public void getData(String name) {
		if(routingTable.containsKey(name)) {
			pendingInterestTable.put(name, "getting generic data..");
			
			System.out.println("Final destination is: ");
			getNameAddress(name);
			
			System.out.println("Next hop is: ");
			getNextHops();
			
			getFromCacheStore("/"+name);
		}
	}

	public Map<String, String> getPendingInterestTable() {
		return pendingInterestTable;
	}
	
}