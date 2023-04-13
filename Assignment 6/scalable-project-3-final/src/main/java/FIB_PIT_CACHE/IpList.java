package FIB_PIT_CACHE;

import java.util.List;

public class IpList {
	String pathName;
	Integer cost;
	String selfNodeIp; //source node's IP
	String selfNodePort; //source node's port which are available for connections

	public String getPathName() {
		return pathName;
	}
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public String getSelfNodeIp() {
		return selfNodeIp;
	}
	public void setSelfNodeIp(String selfNodeIp) {
		this.selfNodeIp = selfNodeIp;
	}
	public String getSelfNodePort() {
		return selfNodePort;
	}
	public void setSelfNodePort(String selfNodePort) {
		this.selfNodePort = selfNodePort;
	}
	@Override
	public String toString() {
		return "IpList [pathName=" + pathName + ", cost=" + cost + ", selfNodeIp=" + selfNodeIp + ", selfNodePort="
				+ selfNodePort + "]";
	}
	
}