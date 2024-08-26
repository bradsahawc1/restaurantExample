//Cameron Bradshaw

import java.util.concurrent.*;
import java.util.Random;
import java.util.Scanner;

public class Driver {
    private volatile static Semaphore door;
    private volatile static Semaphore nap;
    private volatile static Semaphore servicing;
    private static ThreadGroup rushhour = new ThreadGroup("rushhour");
    private static ThreadGroup slowtime = new ThreadGroup("slowtime");

    public static void main(String[] args) throws InterruptedException {
        door = new Semaphore(15, true);
        nap = new Semaphore(0, true);
        servicing = new Semaphore(0, true);
        Random rand = new Random();
        Scanner input = new Scanner(System.in);
        Customer[] customers = new Customer[100];
        Waiter waiter = new Waiter(nap, servicing);
        for (int i = 0; i <= ((customers.length / 2) - 1); i++) {
            customers[i] = new Customer(nap, servicing, door, rushhour);
        }
        for (int i = 0; i <= ((customers.length / 2) - 1); i++) {
            customers[i + 50] = new Customer(nap, servicing, door, slowtime);
        }

        System.out.println("Press enter to begin the rush hour simulation...");
        input.nextLine();
        waiter.start();
        Thread.sleep(1000);
        for (int i = 0; i <= ((customers.length / 2) - 1); i++) {
            customers[i].start();
        }
        for (int x = 0; x < 50; x++)
            customers[x].join( );

        System.out.println("Press enter to begin the slow time simulation...");
        input.nextLine();
        for (int i = 0; i <= ((customers.length / 2) - 1); i++) {
            customers[i + 50].start();
            Thread.sleep((rand.nextInt(9) + 1) * 50);
        }
        for (int x =50; x< 100; x++ )
            customers[x].join( );
        waiter.interrupt();
        Thread.sleep( 500 );
    }
}
