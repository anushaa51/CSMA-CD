import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

class NewThread implements Runnable, ChannelConstants {
    String StationNumber;
    Thread t;
    static int distance;
    static int ChannelStatus; //Indicates if channel is being used
    int FrameNumber,MaxFrameNumber;
    private AtomicBoolean CheckIfSuccessfulTransmission;
    static int tfr=50; //Transmission time
    private int NumberOfAttempts;
    NewThread(String threadname, int MaxFrameNumber) {
        StationNumber = threadname;
        t = new Thread(this, StationNumber);
        FrameNumber = 1;
        this.MaxFrameNumber=MaxFrameNumber;
        CheckIfSuccessfulTransmission = new AtomicBoolean();
        t.start();

    }


    public void run() {

        while (!CheckIfSuccessfulTransmission.get()) {
            NumberOfAttempts++;
            while(FrameNumber <= MaxFrameNumber) {
                if (NumberOfAttempts < 15) { //15 is the maximum number of attempts
                    try {
                        if (ChannelStatus == INUSE) {
                            System.out.println(StationNumber + " is using I-Persistent sensing, channel is busy");
                        }
                        else {
                            System.out.println(StationNumber + " is transmitting frame number : " + FrameNumber);
                            if (ChannelStatus == FREE && distance == 0) {
                                ChannelStatus = INUSE;//set channel to in use
                                for (; distance < 50000; distance++); //50000 is taken as distance between any 2 stations

                                System.out.println(StationNumber + " frame " + FrameNumber + " Successful");
                                CheckIfSuccessfulTransmission.set(true);
                                FrameNumber++;
                                distance = 0; //reset distance for next frame's transmission
                                ChannelStatus = FREE;
                            }
                            else {
                                CheckIfSuccessfulTransmission.set(false);
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
                        System.out.println(StationNumber + "Main Interrupted");
                    }
                }
                else {
                    CheckIfSuccessfulTransmission.set(true);
                    System.out.println("Too many attempts for frame " + FrameNumber+ "of " +
                            StationNumber + ". Transmission stopped");
                }

            }
        }
    }
}