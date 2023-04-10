package PIT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaskTableUtil {
	Map<String, MaskTable> maskMap = new HashMap<String, MaskTable>();
	
	public void addIpMask(String name, String ip, List<String> ports, String category) {
		MaskTable maskObj = new MaskTable();
		maskObj.setCategory(category);
		maskObj.setSourceNodeIp(ip);
		maskObj.setSourceNodePort(ports);
		
		if(maskMap.containsKey(name)) {
			System.out.println("Overwriting earlier values: "+maskMap.get(name));
			maskMap.put(name, maskObj);
		}else {
			System.out.println("Adding new values");
			maskMap.put(name, maskObj);
		}
	}

	public Map<String, MaskTable> getMaskMap() {
		return maskMap;
	}
	
}
