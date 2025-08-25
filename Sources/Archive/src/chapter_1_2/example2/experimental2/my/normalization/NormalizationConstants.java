package chapter_1_2.example_02.experimental2.my.normalization;


import static chapter_1_2.example_02.experimental2.my.Constants.DEFAULT_NUMBER_BASE;

import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;


public class NormalizationConstants {

    // Interval to normalize
    public static final Number Nh = createNumber(DEFAULT_NUMBER_BASE, "1");
    public static final Number Nl = createNumber(DEFAULT_NUMBER_BASE, "-1");

    private NormalizationConstants() {

        throw new UnsupportedOperationException();
    }

}
