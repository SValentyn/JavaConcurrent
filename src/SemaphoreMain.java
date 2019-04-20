import java.util.concurrent.Semaphore;

/**
 * @author Syniuk Valentyn
 */
public class SemaphoreMain {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        new IncThread("A", semaphore);
        new DecThread("B", semaphore);
    }
}

class SharedValue {
    static int count = 0;
}

class IncThread implements Runnable {
    private String name;
    private Semaphore semaphore;

    IncThread(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + name + " waiting for permission");
            semaphore.acquire();
            System.out.println("Thread " + name + " gets permission");
            for (int i = 1; i <= 5; i++) {
                SharedValue.count++;
                System.out.println(name + ": " + SharedValue.count);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + name + " frees permission");
        semaphore.release();
    }
}

class DecThread implements Runnable {
    private String name;
    private Semaphore semaphore;

    DecThread(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + name + " waiting for permission");
            semaphore.acquire();
            System.out.println("Thread " + name + " gets permission");
            for (int i = 1; i <= 5; i++) {
                SharedValue.count--;
                System.out.println(name + ": " + SharedValue.count);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + name + " frees permission");
        semaphore.release();
    }
}
