package FIB_PIT_CACHE;

public class Sender {
	
	
	public void createMessage(String functionName, String name, String data, Aggregator agg) {
		
		String ip = agg.getNameAddress(name);
		
		String message = functionName + "|" + name + "|" + data;
		
		sendTo(ip, "8080", message, name);
		
		
	}
	
	public void sendTo(String sendIp, String sendPort, String Message, String name) {
		System.out.println("Message sent to node " + name + "is : " + Message);
	}
}