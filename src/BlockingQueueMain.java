import java.util.LinkedList;

/**
 * @author Syniuk Valentyn
 */
class BlockingQueueMain {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueue<>();
        queue.push(1);
        queue.push(2);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}

class BlockingQueue<T> {
    private final LinkedList<T> queue = new LinkedList<>();

    void push(final T value) {
        synchronized (queue) {
            queue.add(value);
            queue.notifyAll();
        }
    }

    T poll() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            return queue.remove();
        }
    }
}
