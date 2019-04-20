import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Syniuk Valentyn
 */
public class ReentrantLockMain {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        new MyThreadReentrantLock(lock, "A");
        new MyThreadReentrantLock(lock, "B");
    }
}

class SharedValue {
    static int value = 0;
}

class MyThreadReentrantLock implements Runnable {
    private ReentrantLock lock;
    private String name;

    MyThreadReentrantLock(ReentrantLock lock, String name) {
        this.lock = lock;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + name + " waiting for lock");
            lock.lock();
            System.out.println("Thread " + name + " locked!");
            SharedValue.value++;
            System.out.println("Thread " + name + ": " + SharedValue.value);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Thread " + name + " unlocked!");
            lock.unlock();
        }
    }
}
