package test.jmul.math;


import jmul.test.classification.UnitTest;


@UnitTest
public class NumberConversionTest {

    public static void main(String... args) {

        String numberString = "141,91666666";
        Integer.parseInt(numberString);

        numberString = "141.91666666";
        Integer.parseInt(numberString);
    }

}
