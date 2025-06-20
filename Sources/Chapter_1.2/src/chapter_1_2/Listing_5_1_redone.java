package chapter_1_2;


import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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

import jmul.misc.table.ModifiableTable;
import jmul.misc.table.ModifiableTableImpl;
import jmul.misc.table.Table;


/**
 * A modified normalization example.
 *
 * @author Kristian Kutin
 */
public class Listing_5_1_redone {

    /**
     * A main method.
     *
     * @param args
     *        all command line parameters
     */
    public static void main(String[] args) {

        String[] inputFileNames = { ".\\data\\training_data_set.csv", ".\\data\\test_data_set.csv" };
        String[] outputNormFileName = { ".\\data\\training_data_set_norm.csv", ".\\data\\test_data_set_norm.csv" };

        int max = Math.min(inputFileNames.length, outputNormFileName.length);
        for (int a = 0; a < max; a++) {

            deleteFile(outputNormFileName[a]);
            normalizeData(inputFileNames[a], outputNormFileName[a]);
        }
    }

    /**
     * Dleetes the specified file.
     *
     * @param path
     *        a file path
     */
    private static void deleteFile(String path) {

        File file = new File(path);
        file.delete();
    }

    /**
     * Loads data from the specified input file, normalizes the data and saves the normalized data to the specified
     * output file.
     *
     * @param inputFileName
     *        an input file
     * @param outputNormFileName
     *        an output file
     */
    private static void normalizeData(String inputFileName, String outputNormFileName) {

        CsvDocument inputDocument = CsvHelper.loadCSVDocument(inputFileName);
        Table<String> inputData = inputDocument.getContent();

        ModifiableTable<String> outputData = new ModifiableTableImpl<String>();
        outputData.addColumn();
        outputData.addColumn();

        for (int row = 0; row < inputData.rows(); row++) {

            Number inputXPointValue = new NumberImpl(inputData.getCell(0, row));
            Number targetXPointValue = new NumberImpl(inputData.getCell(1, row));

            Number normInputXPointValue =
                Normalizer.normalize(inputXPointValue, ScalingConstants.maxXPointDh, ScalingConstants.minXPointDl);
            Number normTargetXPointValue =
                Normalizer.normalize(targetXPointValue, ScalingConstants.maxTargetValueDh,
                                     ScalingConstants.minTargetValueDl);

            outputData.addRow();
            outputData.updateCell(0, row, normInputXPointValue.toString());
            outputData.updateCell(1, row, normTargetXPointValue.toString());
        }

        CsvDocument outputDocument = CsvHelper.createNewDocumentWithSameProperties(inputDocument, outputData);
        CsvHelper.saveCsvDocument(outputNormFileName, outputDocument);
    }

}


/**
 * This class contains various constants for scaling test and training data.
 */
final class ScalingConstants {

    // First column
    public final static Number minXPointDl;
    public final static Number maxXPointDh;

    // Second column - target data
    public final static Number minTargetValueDl;
    public final static Number maxTargetValueDh;

    /*
     * The static initializer.
     */
    static {

        minXPointDl = new NumberImpl(0.00D);
        maxXPointDh = new NumberImpl(5.00D);
        minTargetValueDl = new NumberImpl(0.00D);
        maxTargetValueDh = new NumberImpl(5.00D);
    }

}


/**
 * This utility class contains utility operations for reading and writing CSV files.
 */
final class CsvHelper {

    /**
     * The default constructor.
     */
    private CsvHelper() {

        throw new UnsupportedOperationException();
    }

    /**
     * Loads the specified CV file into memory and returns a CSV document object.
     *
     * @param path
     *        a file path
     *
     * @return a CSV document object
     */
    public static CsvDocument loadCSVDocument(String path) {

        Charset charset = StandardCharsets.UTF_8;
        HeaderType headerType = HeaderType.FIRST_LINE_IS_HEADER;
        StructureType structureType = StructureType.RIGID;
        String columnSeparator = ",";
        String rowSeparator = "\r\n";

        CsvDocumentReader reader =
            new CsvDocumentReaderImpl(charset, headerType, structureType, columnSeparator, rowSeparator);

        try {

            return reader.readFrom(path);

        } catch (IOException e) {

            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new CSV document according to the specified parameters.
     *
     * @param document
     *        a CVS document whose properties are copied to the new CSV document
     * @param table
     *        a table with the CSV document content
     *
     * @return a new CSV document
     */
    public static CsvDocument createNewDocumentWithSameProperties(CsvDocument document, Table<String> table) {

        DocumentType documentType = document.getDocumentType();
        Charset charset = document.getStructure().getCharset();
        HeaderType headerType = document.getStructure().getHeaderType();
        StructureType structureType = document.getStructure().getStructureType();
        String columnSeparator = document.getStructure().getColumnSeparator();
        String rowSeparator = document.getStructure().getRowSeparator();

        Table<String> oldTable = document.getContent();
        for (int column = 0; column < oldTable.columns(); column++) {

            String columnName = oldTable.getColumnName(column);
            table.setColumnName(column, columnName);
        }

        CsvDocument newDocument =
            new CsvDocumentImpl(documentType, charset, headerType, structureType, columnSeparator, rowSeparator, table);


        return newDocument;
    }

    /**
     * Saves the specified CSV document at the specified file path.
     *
     * @param path
     *        a file path
     * @param document
     *        a CSV document
     */
    public static void saveCsvDocument(String path, CsvDocument document) {

        CsvDocumentWriter writer = new CsvDocumentWriterImpl();

        try {
            writer.writeTo(path, document);
        } catch (IOException e) {

            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}


/**
 * A utility class for normalizing training and test data.
 */
final class Normalizer {

    final static Number Nh;
    final static Number Nl;

    final static double _Nh;
    final static double _Nl;

    /*
     * The static initializer.
     */
    static {

        Nh = createNumber(10, "1");
        Nl = createNumber(10, "-1");

        _Nh = 1D;
        _Nl = -1D;
    }

    /**
     * The default constructor.
     */
    private Normalizer() {

        throw new UnsupportedOperationException();
    }

    /**
     * Normalizes the specified value according to the specified parameters.
     *
     * @param value
     *        a value
     * @param Dh
     *        the maximum number of a number range
     * @param Dl
     *        the minimum number of a number range
     *
     * @return a normalized number
     */
    public static Number normalize(Number value, Number Dh, Number Dl) {

        // (value - Dl) * (Nh - Nl) / (Dh - Dl) + Nl;

        Number term1 = value.subtract(Dl);
        Number term2 = Nh.subtract(Nl);
        Number term3 = Dh.subtract(Dl);
        Number term4 = Nl;

        Number result = term1.multiply(term2)
                             .divide(FunctionIdentifiers.RUSSIAN_DIVISION_FUNCTION, term3)
                             .add(term4);

        return result;
    }

}
