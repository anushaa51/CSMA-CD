/*Class to check which station's frame the current transmission is colliding with.

 */
public class CheckThreads implements ChannelConstants {
    public static int checking(String StationName)
    {   int stat;
        switch (StationName)
        {
            case("Host 1") : stat = 1;
                                break;
            case ("Host 2") : stat = 2;
                                break;
            case("Host 3") : stat = 3;
                                break;
            case ("Host 4") : stat = 4;
                                break;
            default : stat = 0;

        }
        return(stat);
    }
}