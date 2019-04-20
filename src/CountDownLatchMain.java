import java.util.concurrent.CountDownLatch;

/**
 * @author Syniuk Valentyn
 */
public class CountDownLatchMain {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        new MyThreadCountDownLatch(latch);
        latch.await();
    }
}

class MyThreadCountDownLatch implements Runnable {
    private CountDownLatch latch;

    MyThreadCountDownLatch(CountDownLatch latch) {
        this.latch = latch;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("step - " + i);
            latch.countDown();
        }
    }
}
