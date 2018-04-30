import java.util.Scanner;

interface ChannelConstants{ int FREE = 0;  //Indicates Channel is free
    int INUSE = 1; //Indicates Channel is being used
 }

class CSMACD implements ChannelConstants {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        NewThread.ChannelStatus = FREE; //initially channel is free
        System.out.println("Enter number of stations");
        int n = sc.nextInt();
        NewThread ArrayOfObjects[] = new NewThread[n+1];
        int FrameArray[] = new int[n+1];
        for(int i = 1;i<=n;i++)
        {
            System.out.println("Enter number of frames for Host " + i);
            FrameArray[i] = sc.nextInt();

        }

        for(int i = 1;i<=n;i++)

            ArrayOfObjects[i] = new NewThread("Host "+ Integer.toString(i),FrameArray[i]);


        try {
// wait for stations to complete transmission
           for(int i=1;i<=n;i++)
                ArrayOfObjects[i].t.join();
        }
        catch (InterruptedException e) {
            System.out.println("Main Thread Interrupted");
        }
        System.out.println("Transmission completed.");
    }
}
