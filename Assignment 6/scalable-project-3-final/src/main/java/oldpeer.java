// Fundamental peer-to-peer functionality done by Harry Doyle
// Wang Yuze added connecting new nodes and API/sensor calls
// Harry and Pallavit attempted to implement the PIT/FIB functionality but failed to do so due to reasons mentioned in the report
// And discussed with the group as a whole for understanding and addition of other functionality in here as this is the main class required
import API.WeatherAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.*;


public class oldpeer extends Thread{

    // Name, IP, port of device
    String hostname;
    String ip;
    String multi_ip;
    String near_ip;
    int port;
    List<String> list = new ArrayList<>();
    int num=0;

    //Multicast socket for sending messages to all neighbours
    MulticastSocket initialSocket;
    MulticastSocket multicastSocket;
    // Group of neighbours
    InetAddress outGroup; // outbound message group

    InetAddress initialGroup;

    Map<String,String> map = new HashMap<>();

    int gotit = 0;

    String tem = "";


    PrintWriter out;
    // out is the output stream, this will send messages to server
    BufferedReader in;
    // in is the input stream will handle incoming messages

    public String getTem(int num){
        WeatherAPI api = new WeatherAPI();
        tem = api.queryWeatherVianumber(num);
        return tem;
    }
    // Private Vars
    private static Scanner scanner = new Scanner(System.in);

    private String lastMessage = "";
    // store lastMessage as "cache"
    // TODO: Implement an actual caching system - can be as simple as an array of last 20 messages or so

    //listening message from older nodes
    public void listen() throws IOException {
        // Get message
        byte[] buf = new byte[1000];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);
        this.multicastSocket.receive(recv);
        String tempMessage =  new String(buf, 0, recv.getLength());
        String[] newStr = tempMessage.split(" ");
        if(newStr[0].equals("want")){
            System.out.println(tempMessage + " " + newStr[2]);
            if(newStr[0].equals("want")&&newStr[2].equals(this.hostname)){
                //here askingIP d1 temperature
                System.out.println(getTem(Integer.parseInt(newStr[3])));
                this.forward_send("here "+ newStr[1]+ " "+ newStr[2] + newStr[3] +" " +getTem(Integer.parseInt(newStr[3])));
            }
            this.send(tempMessage);
        }
        else if(newStr[0].equals("im")&&map.get(newStr[3]) == null&&!newStr[1].equals(this.multi_ip)){
            System.out.println(newStr[1]);
            this.outGroup = InetAddress.getByName(newStr[1]);
            this.multicastSocket.joinGroup(this.outGroup);
            map.put(newStr[3],newStr[1]);
            list.add(newStr[1]);
            num=num+1;
        }
        else if(newStr[0].equals("here")&& !newStr[1].equals(this.hostname)){
            this.forward_send(tempMessage);
        }
        else if(newStr[0].equals("here")&& newStr[1].equals(this.hostname)){
            gotit = 1;
        }
        DatagramPacket packet = new DatagramPacket(tempMessage.getBytes(), tempMessage.length(), outGroup, port);
        this.multicastSocket.send(packet);
    }
    //tell the older node i am upcoming one in the begining
    public void initial_send() throws IOException {
        String initial = "im " + this.multi_ip + " have " +this.hostname;
        DatagramPacket packet = new DatagramPacket(initial.getBytes(), initial.length(), initialGroup, port);
        this.initialSocket.send(packet);
    }
    //send message to older nodes
    public void forward_send(String message) throws IOException {
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), initialGroup, port);
        this.initialSocket.send(packet);
        System.out.println(message);
    }


    //recieving message from younger nodes
    public void recieve() throws IOException {
        byte[] buf = new byte[1000];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);
        this.initialSocket.receive(recv);
        String tempMessage =  new String(buf, 0, recv.getLength());
        String[] newStr = tempMessage.split(" ");
        if(newStr[0].equals("im")&&map.get(newStr[3]) == null&&!newStr[1].equals(this.multi_ip)){
            System.out.println("recieve"+tempMessage);
            map.put(newStr[3],newStr[1]);
            list.add(newStr[1]);
            num=num+1;
        }
        else if(newStr[0].equals("here")&& !newStr[1].equals(this.hostname)){
            this.forward_send(tempMessage);
        }
        DatagramPacket packet = new DatagramPacket(tempMessage.getBytes(), tempMessage.length(), outGroup, port);
        this.initialSocket.send(packet);
    }
    //send message to younger nodes
    public void send(String message) throws IOException {
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), outGroup, port);
        this.multicastSocket.send(packet);
    }




    public oldpeer(String name, String ip, String port, String multi_ip, String near_ip, String near_port, String near_name) throws IOException {
        // Set basic variables
        this.hostname = name;
        this.ip = ip;
        this.port = Integer.parseInt(port);
        this.multi_ip = multi_ip;
        this.near_ip =near_ip;

        // Set multicast variables
        this.outGroup = InetAddress.getByName(this.multi_ip);
        this.initialGroup = InetAddress.getByName(this.near_ip);

        this.multicastSocket = new MulticastSocket(this.port);
        this.initialSocket = new MulticastSocket(this.port);

        this.multicastSocket.joinGroup(this.outGroup);
        this.initialSocket.joinGroup(this.initialGroup);


        // incoming message group, can be multiple
        System.out.println("Enter the connection groups IPs end with \"exit\"");
        while (true){
            String inp = scanner.nextLine();
            System.out.println(inp);
            if (inp.equals("exit")) {
                break;
            }

            InetAddress group_ip = InetAddress.getByName(inp);
            this.initialSocket.joinGroup(group_ip);
            System.out.println(group_ip);

        }
    }

}
