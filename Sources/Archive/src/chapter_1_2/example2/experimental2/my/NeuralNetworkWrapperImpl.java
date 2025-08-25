package chapter_1_2.example_02.experimental2.my;


import static chapter_1_2.example_02.experimental2.my.normalization.NormalizationConstants.Nh;
import static chapter_1_2.example_02.experimental2.my.normalization.NormalizationConstants.Nl;
import static chapter_1_2.example_02.experimental2.my.scaling.ScalingConstants.maxTargetValueDh;
import static chapter_1_2.example_02.experimental2.my.scaling.ScalingConstants.maxXPointDh;
import static chapter_1_2.example_02.experimental2.my.scaling.ScalingConstants.minTargetValueDl;
import static chapter_1_2.example_02.experimental2.my.scaling.ScalingConstants.minXPointDl;
import static chapter_1_2.example_02.experimental2.my.Constants.DEFAULT_NUMBER_BASE;
import static jmul.math.numbers.NumberHelper.createNumber;

import chapter_1_2.example_02.experimental2.my.normalization.Denormalizer;
import chapter_1_2.example_02.experimental2.my.normalization.DenormalizerImpl;

import java.io.File;

import java.io.IOException;

import java.nio.charset.Charset;

import java.nio.charset.StandardCharsets;

import jmul.csv.reader.CsvDocumentReader;

import jmul.csv.reader.CsvDocumentReaderImpl;

import jmul.document.csv.CsvDocument;
import jmul.document.csv.structure.HeaderType;

import jmul.document.csv.structure.StructureType;

import jmul.math.functions.repository.FunctionIdentifier;
import jmul.math.functions.repository.FunctionIdentifiers;
import jmul.math.numbers.Number;
import jmul.math.numbers.NumberImpl;

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


public class NeuralNetworkWrapperImpl implements NeuralNetworkWrapper {

    private final String networkFileName;

    private final String chartTrainFileName;

    private final Number numberOfInputNeurons;

    private final Number numberOfOutputNeurons;

    private final Number intNumberOfRecordsInTrainFile;

    public NeuralNetworkWrapperImpl() {

        super();

        this.networkFileName = ".\\data\\Sample2_Saved_Network_File.csv";
        this.chartTrainFileName = "Sample2_XYLine_Train_Results_Chart";

        this.numberOfInputNeurons = createNumber(DEFAULT_NUMBER_BASE, "1");
        this.numberOfOutputNeurons = createNumber(DEFAULT_NUMBER_BASE, "1");

        this.intNumberOfRecordsInTrainFile = createNumber(DEFAULT_NUMBER_BASE, "10");

        File file2 = new File(networkFileName);

        if (file2.exists()) {
            file2.delete();
        }
    }

    @Override
    public TrainingResult trainValidateSaveNetwork(String trainFileName) {

        File file1 = new File(chartTrainFileName);
        if (file1.exists()) {
            file1.delete();
        }

        final Number FIVE = createNumber(DEFAULT_NUMBER_BASE, "5");
        final Number ONE = createNumber(DEFAULT_NUMBER_BASE, "1");
        final Number ZERO = createNumber(DEFAULT_NUMBER_BASE, "0");
        final Number MINUS_ONE = createNumber(DEFAULT_NUMBER_BASE, "-1");

        // Load the training CSV file in memory
        MLDataSet trainingSet =
            loadCSV2Memory(trainFileName, numberOfInputNeurons, numberOfOutputNeurons, true, CSVFormat.ENGLISH, false);
        // create a neural network
        BasicNetwork network = new BasicNetwork();

        // Input layer
        network.addLayer(new BasicLayer(null, true, ONE));

        // Hidden layer
        network.addLayer(new BasicLayer(new ActivationTANH(), true, FIVE));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, FIVE));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, FIVE));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, FIVE));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, FIVE));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, FIVE));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, FIVE));

        // Output layer
        network.addLayer(new BasicLayer(new ActivationTANH(), false, ONE));

        network.getStructure().finalizeStructure();
        network.reset();

        // Train the neural network
        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

        Number epoch = ONE;
        TrainingResult result = new TrainingResult();

        Number FIVE_HUNDRED = createNumber(DEFAULT_NUMBER_BASE, "500");
        Number TRAINING_ERROR = createNumber(DEFAULT_NUMBER_BASE, "0.000000031");
        Number TRAINING_ERROR_2 = createNumber(DEFAULT_NUMBER_BASE, "0.00000003");
        do {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch = epoch.inc();

            if (epoch.isGreaterOrEqual(FIVE_HUNDRED) && network.calculateError(trainingSet) > TRAINING_ERROR) {

                result.updateReturnCode(1);
                System.out.println("Try again");
                return result;
            }
        } while (network.calculateError(trainingSet) > TRAINING_ERROR_2);

        // Save the network file
        EncogDirectoryPersistence.saveObject(new File(networkFileName), network);

        System.out.println("Neural Network Results:");

        Number sumNormDifferencePerc = ZERO;
        Number averNormDifferencePerc = ZERO;
        Number maxNormDifferencePerc = ZERO;

        Number m = MINUS_ONE;
        Number xPointer;

        for (MLDataPair pair : trainingSet) {
            m = m.inc();
            xPointer = MINUS_ONE;

            // if (m==0)
            // continue;

            final MLData output = network.compute(pair.getInput());

            MLData inputData = pair.getInput();
            MLData actualData = pair.getIdeal();
            MLData predictData = network.compute(inputData);

            // Calculate and print the results
            Number normInputXPointValue = inputData.getData(0);
            Number normTargetXPointValue = actualData.getData(0);
            Number normPredictXPointValue = predictData.getData(0);

            Denormalizer denormalizer = new DenormalizerImpl(minXPointDl, maxXPointDh, Nl, Nh);
            Denormalizer denormalizer2 = new DenormalizerImpl(minTargetValueDl, maxTargetValueDh, Nl, Nh);

            Number denormInputXPointValue = denormalizer.denormalize(normInputXPointValue);
            Number denormTargetXPointValue = denormalizer2.denormalize(normTargetXPointValue);
            Number denormPredictXPointValue = denormalizer2.denormalize(normPredictXPointValue);

            final Number THOUSAND = createNumber(DEFAULT_NUMBER_BASE, "1000");
            Number valueDifference = denormTargetXPointValue.subtract(denormPredictXPointValue);
            valueDifference = valueDifference.divide(FunctionIdentifiers.RUSSIAN_DIVISION_FUNCTION , denormTargetXPointValue);
            valueDifference = valueDifference.multiply(THOUSAND);
            valueDifference = valueDifference.absoluteValue();

            System.out.println("xPoint = " + denormTargetXPointValue + "  deormPredictXPointValue = " +
                               denormPredictXPointValue + "  valueDoofference = " + valueDifference);

            sumNormDifferencePerc = sumNormDifferencePerc.add(valueDifference);

            if (valueDifference.isGreater(maxNormDifferencePerc))
                maxNormDifferencePerc = valueDifference;

            result.xData.add(denormInputXPointValue);
            result.yData1.add(denormTargetXPointValue);
            result.yData2.add(denormPredictXPointValue);
        } // End for pair loop

        // Finally, save this trained network

        EncogDirectoryPersistence.saveObject(new File(networkFileName), network);
        System.out.println("Train network has been saved");

        averNormDifferencePerc = sumNormDifferencePerc / intNumberOfRecordsInTrainFile;

        System.out.println(" ");
        System.out.println("maxErrorDifferencePerc = " + maxNormDifferencePerc + "  averErrorDifferencePerc = " +
                           averNormDifferencePerc);

        Encog.getInstance().shutdown();

        return result;
    }

    private static MLDataSet loadCSV2Memory(String filename, int input, int ideal, boolean headers, CSVFormat format,
                                            boolean significance) {
        DataSetCODEC codec = new CSVDataCODEC(new File(filename), format, headers, input, ideal, significance);
        MemoryDataLoader load = new MemoryDataLoader(codec);
        MLDataSet dataset = load.external2Memory();
        return dataset;
    }

    private static MLDataSet loadCsvFile(String filename) {
        
        Charset charset = StandardCharsets.UTF_8;
        HeaderType headerType = HeaderType.FIRST_LINE_IS_HEADER;
        StructureType structureType = StructureType.RIGID;
        String columnSeparator = ",";
        String rowSeparator = "\r\n";
        CsvDocumentReader reader = new CsvDocumentReaderImpl(charset, headerType, structureType, columnSeparator, rowSeparator);

        try {

            CsvDocument document = reader.readFrom(filename);

        } catch (IOException e) {
        }
    }
}
