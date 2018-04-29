//This project uses I-persistent method for CSMA



import java.util.Scanner;
import java.util.Random;
interface ChannelConstants{ int FREE =0;  //Indicates Channel is free
                 int INUSE = 1; //Indicates Channel is being used
}
class NewThread implements Runnable, ChannelConstants {
    String StationNumber; // name of thread
    Thread t;
    static int distance;
    static int ChannelStatus;
    int FrameNumber,MaxFrameNumber;
    private boolean CheckIfSuccessfulTransmission;
    static int tfr=50; //Transmission time
    private int NumberOfAttempts;
    NewThread(String threadname, int MaxFrameNumber) {
        StationNumber = threadname;
        t = new Thread(this, StationNumber);
        FrameNumber = 1;
        this.MaxFrameNumber=MaxFrameNumber;
        CheckIfSuccessfulTransmission = false;
        t.start();

    }


    public void run() {

            while (!this.CheckIfSuccessfulTransmission) {
                this.NumberOfAttempts++;
                for (; this.FrameNumber <= MaxFrameNumber;) {
                    if (this.NumberOfAttempts < 15) { //15 is maximum number of attempts
                        try {
                            if (ChannelStatus == INUSE) {
                                System.out.println(this.StationNumber + " is using I-Peristent, channel is busy");
                            } else {
                                System.out.println(StationNumber + " is transmitting frame number : " + FrameNumber);
                                if (ChannelStatus == FREE && distance == 0) {
                                    ChannelStatus = INUSE;//set channel to in use
                                    for (; distance < 50000; distance++)
                                        ; //50000 is taken as distance between any 2 stations

                                    System.out.println(StationNumber + " frame " + FrameNumber + " Successful");
                                    CheckIfSuccessfulTransmission = true;
                                    this.FrameNumber++;
                                    distance = 0;
                                    ChannelStatus = FREE;
                                } else {
                                    CheckIfSuccessfulTransmission = false;
                                    ChannelStatus = FREE;
                                    Random rand = new Random();
                                    System.out.println("Collision for frame " + FrameNumber + " of " + StationNumber);
                                    try {
                                        int R = (int) (Math.pow(2, NumberOfAttempts - 1));
                                        int BackOffTime = R * tfr;
                                        Thread.sleep(BackOffTime);

                                    } catch (InterruptedException e) {
                                        System.out.println("Interrupted");
                                    }
                                }
                                Thread.sleep(1000);


                            }
                        } catch (InterruptedException e) {
                            System.out.println(StationNumber + "Interrupted");
                        }
                    } else {
                        this.CheckIfSuccessfulTransmission = true;
                        System.out.println("Too many attempts for frame of " + StationNumber + " transmission stopped");
                    }

                }
            }
    }
}
    class CSMACD implements ChannelConstants {
        public static void main(String args[]) {
            Scanner sc = new Scanner(System.in);
            NewThread.ChannelStatus = FREE;
            System.out.println("Enter number of frames for Host One");
            int frame1 = sc.nextInt();
            System.out.println("Enter number of frames for Host Two");
            int frame2 = sc.nextInt();
            System.out.println("Enter number of frames for Host Three");
            int frame3 = sc.nextInt();
            System.out.println("Enter number of frames for Host Four");
            int frame4 = sc.nextInt();
            NewThread ob1 = new NewThread("Host One",frame1);
            NewThread ob2 = new NewThread("Host Two",frame2);
            NewThread ob3 = new NewThread("Host Three",frame3);
            NewThread ob4 = new NewThread("Host four",frame4);


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