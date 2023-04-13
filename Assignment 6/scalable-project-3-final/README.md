# scalable-project-3
This repo contains the source code of our P2P network.

The implemented ICN follows a basic NDN architecture, whereby each node is given a name in the Java run time argument along with an IP group and port number. With the following syntax
- To compile
    - javac oldmain.java oldpeer.java
- To run
    - java oldmain <node_name> <local_ip> <port> <multi_group_ip> <inner_group_ip> <inner_group_node_port> <inner_group_node_name or 0 for no connecting node>

This program was implemented in Java using only natively support Java packages, as such there are no requirements need to get this to run. A such, the presented demonstration should run by running the running.sh file.


Our GIT repo can be found @ https://github.com/doyleh4/scalable-project-3 (feel free to reach out to Harry to get access)
*Note: We are not sure if the git insight/contribution menu is accurately reflecting how we distributed workload, due to how we handled conflicts, by simply copy and pasting previous classes into new folders and then making changes within this new folder for differnet verison of the code*  
