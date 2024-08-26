//Cameron Bradshaw

import java.util.concurrent.Semaphore;

public class Customer extends Thread{
    private static int custCount = 0;
    static Semaphore countmutex;
    private volatile Semaphore nap;
    private volatile Semaphore servicing;
    private volatile Semaphore door;
    private ThreadGroup list;

    public Customer(Semaphore nap, Semaphore servicing, Semaphore door, ThreadGroup list) {
        super(list, "customer");
        this.nap = nap;
        this.servicing = servicing;
        this.door = door;
        this.list = list;
        countmutex = new Semaphore(1, true);
    }

    public void run() {
        System.out.println("A customer is trying to enter the restaurant.");
        try {
            door.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        nap.release();

        try {
            countmutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        custCount++;
        int val = custCount;
        countmutex.release();
        System.out.println("Customer " + val + " has entered and been seated.");

        try {
            servicing.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Customer " + val + " has been served.");

        door.release();
        System.out.println("Customer " + val + " is leaving.");
    }
}
