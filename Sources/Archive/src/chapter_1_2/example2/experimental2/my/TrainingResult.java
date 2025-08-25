package chapter_1_2.example_02.experimental2.my;


import java.util.ArrayList;
import java.util.List;


public class TrainingResult {

    private int returnCode;
    public final List<Number> xData;
    public final List<Number> yData1;
    public final List<Number> yData2;

    public TrainingResult() {

        super();

        this.returnCode = 0;
        this.xData = new ArrayList<Number>();
        this.yData1 = new ArrayList<Number>();
        this.yData2 = new ArrayList<Number>();
    }

    public void updateReturnCode(int returnCode) {

        this.returnCode = returnCode;
    }

    public int returnCode() {

        return this.returnCode;
    }

}
