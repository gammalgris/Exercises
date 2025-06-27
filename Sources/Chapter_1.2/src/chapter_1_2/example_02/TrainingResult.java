package chapter_1_2.example_02;


import java.util.ArrayList;
import java.util.List;


public class TrainingResult {

    private int returnCode;
    public final List<Double> xData;
    public final List<Double> yData1;
    public final List<Double> yData2;

    public TrainingResult() {

        super();

        this.returnCode = 0;
        this.xData = new ArrayList<Double>();
        this.yData1 = new ArrayList<Double>();
        this.yData2 = new ArrayList<Double>();
    }

    public void updateReturnCode(int returnCode) {

        this.returnCode = returnCode;
    }

    public int returnCode() {

        return this.returnCode;
    }

}
