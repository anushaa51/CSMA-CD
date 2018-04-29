import java.util.Scanner;

interface ChannelConstants{ int FREE =0;  //Indicates Channel is free
    int INUSE = 1; //Indicates Channel is being used
 }

class CSMACD implements ChannelConstants {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        NewThread.ChannelStatus = FREE; //initially channel is free
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
        System.out.println("Transmission completed.");
    }
}
