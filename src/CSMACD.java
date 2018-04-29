//This project uses I-persistent method for CSMA

import java.util.Random;
interface ChannelConstants{ int FREE =0;  //Indicates Channel is free
                 int INUSE = 1; //Indicates Channel is being used
}
class NewThread implements Runnable, ChannelConstants {
    String StationNumber; // name of thread
    Thread t;
    static int distance;
    static int ChannelStatus;
    private boolean CheckIfSuccessfulTransmission;
    static int tfr=50; //Transmission time
    private int NumberOfAttempts;
    NewThread(String threadname) {
        StationNumber = threadname;
        t = new Thread(this, StationNumber);
        CheckIfSuccessfulTransmission = false;
        t.start();
    }


    public void run() {
        while (!this.CheckIfSuccessfulTransmission) {
            this.NumberOfAttempts++;
            if (this.NumberOfAttempts < 15) { //15 is maximum number of attempts
                try {
                    System.out.println(StationNumber + " is transmitting");
                    if (ChannelStatus==FREE && distance==0) {
                        for(;distance<50;distance++); //50 is taken as distance between any 2 stations
                        ChannelStatus = INUSE;//set channel to in use
                        System.out.println(StationNumber + " Successful");
                        CheckIfSuccessfulTransmission = true;
                        distance=0;
                    }
                    else {
                        CheckIfSuccessfulTransmission = false;
                        ChannelStatus = FREE;
                        Random rand = new Random();
                        System.out.println("Collision for " + StationNumber);
                        try {
                            int R = (int)(Math.pow(2,NumberOfAttempts-1));
                            int BackOffTime = R*tfr;
                            Thread.sleep(BackOffTime );

                        }
                        catch (InterruptedException e) {
                            System.out.println("Interrupted");
                        }
                    }
                    Thread.sleep(1000);


                } catch (InterruptedException e) {
                    System.out.println(StationNumber + "Interrupted");
                }
            } else {
                System.out.println("Too many attempts for frame of" + StationNumber + " transmission stopped");
            }
        }
    }
}
    class CSMACD implements ChannelConstants {
        public static void main(String args[]) {
            NewThread.ChannelStatus = FREE;
            NewThread ob1 = new NewThread("Host One"); // start threads
            NewThread ob2 = new NewThread("Host Two");
            NewThread ob3 = new NewThread("Host Three");
            NewThread ob4 = new NewThread("Host four");


            try {
// wait for stations to complete transmission
                ob1.t.join();
                ob2.t.join();
                ob3.t.join();
                ob4.t.join();

            } catch (InterruptedException e) {
                System.out.println("Main Thread Interrupted");
            }
            System.out.println("Transmission done");
        }
}