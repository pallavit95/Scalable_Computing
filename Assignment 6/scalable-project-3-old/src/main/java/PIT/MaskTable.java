package PIT;

import java.util.List;

public class MaskTable {
	
	String sourceNodeIp; //source node's IP
	
	List<String> sourceNodePort; //source node's port which are available for connections
	
	String category;

	public String getSourceNodeIp() {
		return sourceNodeIp;
	}

	public void setSourceNodeIp(String sourceNodeIp) {
		this.sourceNodeIp = sourceNodeIp;
	}

	public List<String> getSourceNodePort() {
		return sourceNodePort;
	}

	public void setSourceNodePort(List<String> sourceNodePort) {
		this.sourceNodePort = sourceNodePort;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}
