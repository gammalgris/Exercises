package chapter_1_2.example_02.experimental1.network.hash;


public class HashCounter {

    private static HashCounter counter;

    static {

        counter = new HashCounter();
    }

    private int nextHashCode;

    public HashCounter() {

        super();

        nextHashCode = 0;
    }

    public int next() {

        int hashCode = nextHashCode;
        nextHashCode++;

        return hashCode;
    }

    public static int nextHashCode() {

        int hashCode;
        synchronized (counter) {

            hashCode = counter.next();
        }

        return hashCode;
    }

}
