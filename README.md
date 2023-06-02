# UNIX HTTP/1.1 Web-Server
## Computer Networking Exercise 3
  
A basic RFC based webserver implemented in java.  
Tested only on UNIX machines.

This application uses a WebServer and a GetClient. They both must be started separately.  
The WebServer uses concurrency and can handle multiple requests at a time.  
I just needs to be started once, then it keeps running.  
The WebClient will only be executed once per call.

### Starting the WebServer:
> src/main/java/de/hawhamburg/ti/inf/rnp/webServer/WebServerMain.java

***Important:*** The WebServer needs to know the absolute path of the machine it's running on.  
It can be adjusted inside the DirectoryUtils class. (This will be fixed in the future)
> src/main/java/de/hawhamburg/ti/inf/rnp/webServer/src/utils/DirectoryUtils.java

**Arguments for WebServer:**
> **-p or --port**      → Port (default: 80)  
> **-t or --threads**   → Amount of fixed Threads (default: 10)  
> **-l or --logFile**   → Path on where to save the logFile (e.g. /Users/log.txt)  


### Starting the WebClient:
> src/main/java/de/hawhamburg/ti/inf/rnp/get/GetMain.java

**Arguments for GetClient:**
> **-h or --host**      → Host (e.g. example.com)  
> **-p or --port**      → Port (default: 80)  
> **-f or --file**      → File (e.g. /index.html)  
> **-r or --range**     → Content-Range ***(Optional)***   
> **-s or --slow**      → Slow Motion ***(Optional)***  

**Content-Range:**
> You can either pass a start value, from which byte to download the response content or you can pass two values (e.g. 10-120) 
> to specify a start and an end value.  
> 
> **Start only:** -r 10  
> **Start and end:** -r 10-120

**Slow Motion:**
> Slow Motion slices the HTTP-Get request into a defined amount of bytes and sends them in separate blocks.  
> Here you ***always*** need to pass two value. The amount of bytes that will be sent and the time interval in ms.  
>
> **4 bytes every 500ms** → -s 4 -s 500  
