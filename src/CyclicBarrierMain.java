import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Syniuk Valentyn
 */
public class CyclicBarrierMain {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3, new BarrierAction());

        new MyThreadCyclicBarrier("A", barrier);
        new MyThreadCyclicBarrier("B", barrier);
        new MyThreadCyclicBarrier("C", barrier);

        new MyThreadCyclicBarrier("X", barrier);
        new MyThreadCyclicBarrier("Y", barrier);
        new MyThreadCyclicBarrier("Z", barrier);
    }
}

class MyThreadCyclicBarrier implements Runnable {
    private String name;
    private CyclicBarrier barrier;

    MyThreadCyclicBarrier(String name, CyclicBarrier barrier) {
        this.name = name;
        this.barrier = barrier;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("thread: " + name);
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class BarrierAction implements Runnable {
    @Override
    public void run() {
        System.out.println("Barrier reached!");
    }
}
