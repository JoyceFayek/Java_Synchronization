# Java_Synchronization
It is required to simulate a limited number of devices connected to a router’s Wi-Fi using Java threading and semaphore. Routers can be designed to limit the number of open connections. For example, a Router may wish to have only N connections at any point in time. 
As soon as N connections are made, the Router will not accept other incoming 
connections until an existing connection is released. Explain how semaphores can be used by a Router to limit the number of concurrent connections

➔The Wi-Fi number of connected devices is initially empty.

➔ If a client is logged in (print a message that a client has logged in) and if it can be served

➔ (means that it can reach the internet), then the client should perform the following activities:
◆ Connect
◆ Perform online activity
◆ Log out

➔ Note: these actions will be represented by printed messages, such that there is a random

➔ waiting time between the printed messages when a client connects, do some online

➔ activities and logged out.

➔ If a client arrives and all connections are occupied, it must wait until one of the currently

➔ available clients finish his service and leave.

➔ After a client finishes his service, he leave and one of the waiting clients (if exist) will connect to the internet.
