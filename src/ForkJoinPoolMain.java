import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author Syniuk Valentyn
 */
public class ForkJoinPoolMain {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        double[] array = new double[100_000];
        for (int i = 0; i < array.length; i++) array[i] = (double) i;

        SqrtTransform task = new SqrtTransform(array, 0, array.length);
        pool.invoke(task);

        for (int i = 0; i < 10; i++) System.out.printf("%.4f ", array[i]);
    }
}

class SqrtTransform extends RecursiveAction {
    private final int thresholdValue = 1000;
    private double[] array;
    private int start, end;

    SqrtTransform(double[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override // perform parallel computing
    protected void compute() {
        if ((end - start) < thresholdValue) {
            for (int i = start; i < end; i++) {
                array[i] = Math.sqrt(array[i]);
            }
        } else { // split data into smaller parts
            int middle = (start + end) / 2;
            invokeAll(new SqrtTransform(array, start, middle),
                    new SqrtTransform(array, middle, end));
        }
    }
}
