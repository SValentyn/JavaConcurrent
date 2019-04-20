import java.util.concurrent.*;

/**
 * @author Syniuk Valentyn
 */
public class ExecutorServiceMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Future<Integer> result_1 = executorService.submit(new FindFactorial(5));
        Future<Double> result_2 = executorService.submit(new FindHypotenuse(3, 4));

        System.out.println("First result: " + result_1.get(10, TimeUnit.MILLISECONDS));
        System.out.println("Second result: " + result_2.get(10, TimeUnit.MILLISECONDS));

        executorService.shutdown();
    }
}

class FindFactorial implements Callable<Integer> {
    private int value;

    FindFactorial(int value) {
        this.value = value;
    }

    @Override
    public Integer call() {
        int result = 1;
        for (int i = 2; i <= value; i++) result *= i;
        return result;
    }
}

class FindHypotenuse implements Callable<Double> {
    private double a, b;

    FindHypotenuse(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Double call() {
        return Math.sqrt((a * a) + (b * b));
    }
}
