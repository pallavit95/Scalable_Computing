# scalable-project-3
Repo that hold the source code for our ICN based off NDN.

As you can see this repo has a server/node class. This is for a non-p2p network where communication is done via a server (centralised architecture)

The BasicPeer class, is a SIMPLE example of a P2P network. Whereby a chat application is made with no central point of connection. Communication is achieved by assigning a port number to the destination socket. This will need to be updated (see comments for more info)

For P2P network, the node class is going to have to be updated to act as a server (receiving messages) and a node (sending messages). It also must be assigned random port numbers and IPs
