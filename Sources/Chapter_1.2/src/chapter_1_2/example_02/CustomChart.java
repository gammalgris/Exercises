package chapter_1_2.example_02;


import chapter_1_2.example_02.experimental0.TrainingResult;

import java.awt.Color;
import java.awt.Font;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler.LegendPosition;

import java.awt.Color;
import java.awt.Font;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.buffer.MemoryDataLoader;
import org.encog.ml.data.buffer.codec.CSVDataCODEC;
import org.encog.ml.data.buffer.codec.DataSetCODEC;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.util.csv.CSVFormat;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;


public class CustomChart extends XYChart {

    private String chartTrainFileName;
    
    public CustomChart() {

        super(900, 500);

        this.setTitle(getClass().getSimpleName());
        this.setXAxisTitle("y= f(x)");
        this.setYAxisTitle("y= f(x)");

        // Customize Chart
        this.getStyler()
            .setPlotBackgroundColor(Color.GRAY);
        this.getStyler().setPlotGridLinesColor(new Color(255, 255, 255));
        this.getStyler().setChartBackgroundColor(Color.WHITE);
        this.getStyler().setLegendBackgroundColor(Color.PINK);
        this.getStyler().setChartFontColor(Color.MAGENTA);
        this.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));
        this.getStyler().setChartTitleBoxVisible(true);
        this.getStyler().setChartTitleBoxBorderColor(Color.BLACK);
        this.getStyler().setPlotGridLinesVisible(true);
        this.getStyler().setAxisTickPadding(20);
        this.getStyler().setAxisTickMarkLength(15);
        this.getStyler().setPlotMargin(20);
        this.getStyler().setChartTitleVisible(false);
        this.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        this.getStyler().setLegendFont(new Font(Font.SERIF, Font.PLAIN, 18));
        this.getStyler().setLegendPosition(LegendPosition.InsideSE); // <- ERROR - import was missing
        this.getStyler().setLegendSeriesLineLength(12);
        this.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
        this.getStyler().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 11));
        this.getStyler().setDatePattern("yyyy-MM");
        this.getStyler().setDecimalPattern("#0.00");
        
        this.chartTrainFileName = "Sample2_XYLine_Train_Results_Chart";
    }

    public void updateTrainingResults(TrainingResult trainingResult) {

        XYSeries series1 = this.addSeries("Actual data", trainingResult.xData, trainingResult.yData1);
        XYSeries series2 = this.addSeries("Predict data", trainingResult.xData, trainingResult.yData2);

        series1.setLineColor(XChartSeriesColors.BLUE);
        series2.setMarkerColor(Color.ORANGE);
        series1.setLineStyle(SeriesLines.SOLID);
        series2.setLineStyle(SeriesLines.SOLID);

        try {
            // Save the chart image
            BitmapEncoder.saveBitmapWithDPI(this, chartTrainFileName, BitmapFormat.JPG,
                                            100); // <- ERROR - missing import
            System.out.println("Train chart file has been saved");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(3);
        }
    }

}
