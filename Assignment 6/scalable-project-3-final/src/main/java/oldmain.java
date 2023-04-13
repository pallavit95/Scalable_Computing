// Entire main function done by Wang Yuze
import java.io.IOException;
import java.util.Scanner;

public class oldmain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        for(int i=0;i<args.length;i++){
            System.out.print(args[i]+"  ");
        }
        oldpeer peer = new oldpeer(args[0],args[1],args[2],args[3],args[4],args[5],args[6]);

        //send message to older nodes that i am still alive
        Thread threadA = new Thread(() -> {
            try {
                while (true) {
                    peer.initial_send();
                    Thread.sleep(20000);
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        //keep recieving message from younger nodes
        Thread threadB = new Thread(() -> {
            try {
                while (true) {
                    peer.recieve();
                    Thread.sleep(5000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        //tell younger nodes that i want something
        Thread threadC = new Thread(() -> {
            while (true) {
                String inp = scanner.nextLine();
                String[] newStr = inp.split(" ");
                //want hostname d 1
                if(newStr[0].equals("want")){
                    try {
                        while(true) {
                            peer.send("want " + peer.hostname + " "+newStr[1]+" "+newStr[2]);
                            System.out.println("thats what i want");
                            Thread.sleep(10000);
                            if(peer.gotit==1){
                                System.out.println("received");
                                peer.gotit=0;
                                break;
                            }
                        }
                        System.out.println("received");

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                inp = "";
            }
        });
        //keep listening message from older nodes
        Thread threadD = new Thread(() -> {
            while (true) {
                try {
                    peer.listen();
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadB.start();
        threadA.start();
        threadC.start();
        threadD.start();
    }
}