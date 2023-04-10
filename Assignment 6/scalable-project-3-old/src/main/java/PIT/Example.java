package PIT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Example {
	public static void main(String[] args) throws IOException {
		Aggregator agg = new Aggregator();
		/*
		 Aggregator has routingTable that stores path and their connections.
		 A path(masked ip) may have multiple destinations or connections.
		 */
		
		agg.addName("node1", "google");
		
		System.out.println(agg.getAllDestForName("node1"));
		System.out.println(agg.getAllDestForName("google"));
		
		
		agg.addName("google", "gmail");
		agg.addName("google", "youtube");
		
		System.out.println(agg.getAllDestForName("google"));
		
		agg.addName("youtube", "private-video");
		agg.addName("youtube", "public-video");
		
		System.out.println(agg.getAllDestForName("youtube"));
		
		
		MaskTableUtil maskUtil = new MaskTableUtil();
		
		maskUtil.addIpMask("youtube", "127.0.0.4", new ArrayList<String>(Arrays.asList("80","90")), "producer");
		
		System.out.println(agg.getNameAddress("node1/google/youtube/video1", maskUtil));
	}
}
