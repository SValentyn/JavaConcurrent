import java.util.concurrent.Phaser;

/**
 * @author Syniuk Valentyn
 */
public class PhaserMain {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);
        int currentPhase;

        new MyThreadPhaser(phaser, "A");
        new MyThreadPhaser(phaser, "B");
        new MyThreadPhaser(phaser, "C");

        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase " + ++currentPhase + " completed!");

        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase " + ++currentPhase + " completed!");

        currentPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase " + ++currentPhase + " completed!");

        phaser.arriveAndDeregister();
        if (phaser.isTerminated()) {
            System.out.println("The phase synchronizer is completed");
        }
    }
}

class MyThreadPhaser implements Runnable {
    private Phaser phaser;
    private String name;

    MyThreadPhaser(Phaser phaser, String name) {
        this.phaser = phaser;
        this.name = name;
        phaser.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + name + " -> begins the 1st phase");
            phaser.arriveAndAwaitAdvance();
            Thread.sleep(1000);

            System.out.println("Thread " + name + " -> begins the 2st phase");
            phaser.arriveAndAwaitAdvance();
            Thread.sleep(1000);

            System.out.println("Thread " + name + " -> begins the 3st phase");
            phaser.arriveAndAwaitAdvance();
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e);
        }
    }
}
