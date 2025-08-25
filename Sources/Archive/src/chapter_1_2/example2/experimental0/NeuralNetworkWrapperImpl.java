package chapter_1_2.example_02.experimental0;


import static chapter_1_2.example_02.experimental0.NormalizationConstants.Nh;
import static chapter_1_2.example_02.experimental0.NormalizationConstants.Nl;
import static chapter_1_2.example_02.experimental0.ScalingConstants.maxTargetValueDh;
import static chapter_1_2.example_02.experimental0.ScalingConstants.maxXPointDh;
import static chapter_1_2.example_02.experimental0.ScalingConstants.minTargetValueDl;
import static chapter_1_2.example_02.experimental0.ScalingConstants.minXPointDl;

import java.io.File;

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

    private final int numberOfInputNeurons;

    private final int numberOfOutputNeurons;

    private final int intNumberOfRecordsInTrainFile;

    public NeuralNetworkWrapperImpl() {

        super();

        this.networkFileName = ".\\data\\Sample2_Saved_Network_File.csv";
        this.chartTrainFileName = "Sample2_XYLine_Train_Results_Chart";

        this.numberOfInputNeurons = 1;
        this.numberOfOutputNeurons = 1;

        this.intNumberOfRecordsInTrainFile = 10;

        File file2 = new File(networkFileName);

        if (file2.exists()) {
            file2.delete();
        }
    }

    public TrainingResult trainValidateSaveNetwork(String trainFileName) {

        File file1 = new File(chartTrainFileName);
        if (file1.exists()) {
            file1.delete();
        }

        // Load the training CSV file in memory
        MLDataSet trainingSet =
            loadCSV2Memory(trainFileName, numberOfInputNeurons, numberOfOutputNeurons, true, CSVFormat.ENGLISH, false);
        // create a neural network
        BasicNetwork network = new BasicNetwork();

        // Input layer
        network.addLayer(new BasicLayer(null, true, 1));

        // Hidden layer
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 5));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 5));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 5));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 5));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 5));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 5));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 5));

        // Output layer
        network.addLayer(new BasicLayer(new ActivationTANH(), false, 1));

        network.getStructure().finalizeStructure();
        network.reset();

        // Train the neural network
        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

        int epoch = 1;
        TrainingResult result = new TrainingResult();

        do {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;

            if (epoch >= 500 && network.calculateError(trainingSet) > 0.000000031) {

                result.updateReturnCode(1);
                System.out.println("Try again");
                return result;
            }
        } while (network.calculateError(trainingSet) > 0.00000003);

        // Save the network file
        EncogDirectoryPersistence.saveObject(new File(networkFileName), network);

        System.out.println("Neural Network Results:");

        double sumNormDifferencePerc = 0.00;
        double averNormDifferencePerc = 0.00;
        double maxNormDifferencePerc = 0.00;

        int m = -1;
        double xPointer = -1.00;

        for (MLDataPair pair : trainingSet) {
            m++;
            xPointer = -1.00;

            // if (m==0)
            // continue;

            final MLData output = network.compute(pair.getInput());

            MLData inputData = pair.getInput();
            MLData actualData = pair.getIdeal();
            MLData predictData = network.compute(inputData);

            // Calculate and print the results
            double normInputXPointValue = inputData.getData(0);
            double normTargetXPointValue = actualData.getData(0);
            double normPredictXPointValue = predictData.getData(0);

            double denormInputXPointValue =
                ((minXPointDl - maxXPointDh) * normInputXPointValue - Nh * minXPointDl + maxXPointDh * Nl) / (Nl - Nh);
            double denormTargetXPointValue =
                ((minTargetValueDl - maxTargetValueDh) * normTargetXPointValue - Nh * minTargetValueDl +
                 maxTargetValueDh * Nl) / (Nl - Nh);
            double denormPredictXPointValue =
                ((minTargetValueDl - maxTargetValueDh) * normPredictXPointValue - Nh * minTargetValueDl +
                 maxTargetValueDh * Nl) / (Nl - Nh);

            double valueDifference =
                Math.abs(((denormTargetXPointValue - denormPredictXPointValue) / denormTargetXPointValue) * 1000.00);

            System.out.println("xPoint = " + denormTargetXPointValue + "  deormPredictXPointValue = " +
                               denormPredictXPointValue + "  valueDoofference = " + valueDifference);

            sumNormDifferencePerc = sumNormDifferencePerc + valueDifference;

            if (valueDifference > maxNormDifferencePerc)
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

}
