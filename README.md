# HTTP/1.1 Web-Server
## Computer Networking Exercise 3
**Exercises:** Thomas Dreibholz  
**Java-Adjustments:** Iwer Petersen & Kristoffer Schaaf  
  
A basic RFC based webserver implemented in java.

### Arguments:

> **-h or --host**      → Host (e.g. example.com)  
> **-p or --port**      → Port (e.g. 80)  
> **-f or --file**      → File (e.g. /index.html)  
> **-r or --range**     → Content-Range ***(Optional)***   
> **-s or --slow**      → Slow Motion ***(Optional)***  

#### Content-Range
> You can either pass a start value, from which byte to pass the stream or you can pass two values (e.g. 10-120) 
> to specify a start and an end value.  
> 
> **Start only:** -r 10  
> **Start and end:** -r 10-120

#### Slow Motion
> Slow Motion slices the HTTP-Get request into a defined amount of bytes and sends them in separate blocks.  
> Here you ***always*** need to pass two value. The amount of bytes that will be sent and the time interval in ms.  
>
> **4 bytes every 500ms** → -s 4 -s 500  