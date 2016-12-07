/**
 * Created by hepingtao on 12/7/16.
 */
public class HelloThread extends Thread {
    public void run() {
        System.out.println("Hello from a thread");
    }

    public static void main(String args[])
    {
        (new HelloThread()).start();
    }
}
