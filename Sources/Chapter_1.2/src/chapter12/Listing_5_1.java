package chapter12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Listing_5_1 {

    // Interval to normalize
    static double Nh = 1D;
    static double Nl = -1D;

    // First column
    static double minXPointDl = 0.00D;
    static double maxXPointDh = 5.00D;

    // Second column - target data
    static double minTargetValueDl = 0.00D;
    static double maxTargetValueDh = 5.00D;

    public static double normalize(double value, double Dh, double Dl) {

        double normalizedValue = (value - Dl) * (Nh - Nl) / (Dh - Dl) + Nl;

        return normalizedValue;
    }

    public static void main(String[] args) {

        // Config data

        // config for training
        //String inputFileName = "D:\\repositories\\NeuralNets\\Sources\\Chapter 1.2\\data\\training_data_set.csv";
        //String outputNormFileName = "D:\\repositories\\NeuralNets\\Sources\\Chapter 1.2\\data\\training_data_set_norm.csv";
        String inputFileName = "D:\\repositories\\NeuralNets\\Sources\\Chapter 1.2\\data\\test_data_set.csv";
        String outputNormFileName = "D:\\repositories\\NeuralNets\\Sources\\Chapter 1.2\\data\\test_data_set_norm.csv";

        BufferedReader br = null;
        PrintWriter out = null;

        String line = "";
        String csvSplitBy = ",";
        String strNormInputXPointValue;
        String strNormTargetXPointValue;
        String fullLine;
        double inputXPointValue;
        double targetXPointValue;
        double normInputXPointValue;
        double normTargetXPointValue;
        int i = -1;

        try {

            Files.deleteIfExists(Paths.get(outputNormFileName));

            br = new BufferedReader(new FileReader(inputFileName));
            out = new PrintWriter(new BufferedWriter(new FileWriter(outputNormFileName)));

            while ((line = br.readLine()) != null) {

                i++;
                if (i == 0) {

                    out.println(line);

                } else {

                    String[] workfields = line.split(csvSplitBy);

                    inputXPointValue = Double.parseDouble(workfields[0]);
                    targetXPointValue = Double.parseDouble(workfields[1]);

                    normInputXPointValue = normalize(inputXPointValue, maxXPointDh, minXPointDl);
                    normTargetXPointValue = normalize(targetXPointValue, maxTargetValueDh, minTargetValueDl);

                    strNormInputXPointValue = Double.toString(normInputXPointValue);
                    strNormTargetXPointValue = Double.toString(normTargetXPointValue);

                    fullLine = strNormInputXPointValue + "," + strNormTargetXPointValue;

                    out.println(fullLine);
                }


            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            System.exit(1);

        } catch (IOException e) {

            e.printStackTrace();
            System.exit(2);

        } finally {

            if (br != null) {
                try {
                    br.close();
                    out.close();
                } catch (IOException e1) {

                    e1.printStackTrace();
                    System.exit(3);
                }
            }
        }
    }

}
