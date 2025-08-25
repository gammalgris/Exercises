package chapter_1_2.example_02.experimental2.my.scaling;


import static chapter_1_2.example_02.experimental2.my.Constants.DEFAULT_NUMBER_BASE;

import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;


public class ScalingConstants {

    // First column
    public static final Number minXPointDl = createNumber(DEFAULT_NUMBER_BASE, "0");
    public static final Number maxXPointDh = createNumber(DEFAULT_NUMBER_BASE, "5");

    // Second column - target data
    public static final Number minTargetValueDl = createNumber(DEFAULT_NUMBER_BASE, "0");
    public static final Number maxTargetValueDh = createNumber(DEFAULT_NUMBER_BASE, "5");
    
    private ScalingConstants() {

        throw new UnsupportedOperationException();
    }

}
