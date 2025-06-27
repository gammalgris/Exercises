package chapter_1_2.example_02;


import java.awt.Color;
import java.awt.Font;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jmul.csv.reader.CsvDocumentReader;
import jmul.csv.reader.CsvDocumentReaderImpl;
import jmul.csv.writer.CsvDocumentWriter;
import jmul.csv.writer.CsvDocumentWriterImpl;

import jmul.document.csv.CsvDocument;
import jmul.document.csv.CsvDocumentImpl;
import jmul.document.csv.structure.HeaderType;
import jmul.document.csv.structure.StructureType;
import jmul.document.type.DocumentType;

import jmul.math.functions.repository.FunctionIdentifiers;
import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.math.numbers.NumberImpl;

import jmul.misc.table.Table;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.buffer.MemoryDataLoader;
import org.encog.ml.data.buffer.codec.CSVDataCODEC;
import org.encog.ml.data.buffer.codec.DataSetCODEC;
import org.encog.ml.data.buffer.codec.NeuralDataSetCODEC;
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
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;


/**
 * A modified example.
 *
 * @author Kristian Kutin
 */
public class Listing_5_2_redone {

    public static void main(String... args) {

        CustomChart chart = new CustomChart();
        SwingWrapper<XYChart> swingWrapper = new SwingWrapper<XYChart>(chart);
        
        String trainFileName = ".\\data\\training_data_set_norm.csv";;
        NeuralNetworkWrapper nnw = new NeuralNetworkWrapperImpl();
        TrainingResult trainingResult;

        do {
            trainingResult = nnw.trainValidateSaveNetwork(trainFileName);

        } while (trainingResult.returnCode() > 0);

        chart.updateTrainingResults(trainingResult);
        swingWrapper.displayChart();
    }

}
