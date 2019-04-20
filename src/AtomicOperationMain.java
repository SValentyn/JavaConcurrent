import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Syniuk Valentyn
 */
public class AtomicOperationMain {
    public static void main(String[] args) {
        new MyThreadAtomicOperation("A");
        new MyThreadAtomicOperation("B");
    }
}

class SharedValue {
    static AtomicInteger atomicInteger = new AtomicInteger(0);
}

class MyThreadAtomicOperation implements Runnable {
    private String name;

    MyThreadAtomicOperation(String name) {
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++)
            System.out.println("Thread " + name + " -> received: " +
                    SharedValue.atomicInteger.getAndSet(i));
    }
}
