# CSMA-CD
Java implementation of CSMA/CD protocol using I-Persistent sensing.

**CSMA :** <br>
Carrier Sense Multiple Access/Collision Detection(CSMA) is a Media Access Control(MAC) Protocol used to minimize the probability of collision in the Data Link layer.

**CSMA/CD :** <br>
CSMA/Collision Detection is equipped to handle collisions. Frames that have not been successfully transferred are resent upto K times, where K is the maximum number of attempts.
There are three types of sensing used in CSMA, this project uses I-Persistent method.
Upon collision, the algorithm waits for a specific Backoff time before retransmission.<br>
*Backoff time is calculated as, TBf = R * Transmission time* 
<br>
*Random number R = 0 to 2^K-1*

