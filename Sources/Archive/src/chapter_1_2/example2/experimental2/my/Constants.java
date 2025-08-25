package chapter_1_2.example_02.experimental2.my;


public class Constants {

    public static final int DEFAULT_NUMBER_BASE;
    
    static {
        
        DEFAULT_NUMBER_BASE = 10;
    }

    private Constants() {

        throw new UnsupportedOperationException();
    }
}
