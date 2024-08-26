//Cameron Bradshaw

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Waiter extends Thread {
    private volatile Semaphore nap;
    private volatile Semaphore servicing;

    public Waiter(Semaphore nap, Semaphore servicing) {
        this.nap = nap;
        this.servicing = servicing;
    }

    public void run() {
        int count = 0;
        Random rand = new Random();
        do{
            count++;
            if(!nap.tryAcquire()) {

                System.out.println("Waiter is going to sleep.");
                try {
                    nap.acquire();
                    System.out.println("Waiter is waking up.");
                }
                catch (InterruptedException e) {
                    System.out.println("Waiter is closing up shop.");
                    return;
                }
            }
            System.out.println("Waiter is servicing customer number " + count + ".");
            try {
                Thread.sleep((rand.nextInt(9) + 1) * 50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            servicing.release();
        } while(true);
    }
}
