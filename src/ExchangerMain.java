import java.util.concurrent.Exchanger;

/**
 * @author Syniuk Valentyn
 */
public class ExchangerMain {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new UseString(exchanger);
        new MakeString(exchanger);
    }
}

class UseString implements Runnable {
    private Exchanger<String> exchanger;
    private String str;

    UseString(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                str = exchanger.exchange("");
                System.out.println("Received: " + str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MakeString implements Runnable {
    private Exchanger<String> exchanger;
    private String str;

    MakeString(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        str = "";
        new Thread(this).start();
    }

    @Override
    public void run() {
        char ch = 'A';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                str += (char) ch++;
            }
            try {
                exchanger.exchange(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
