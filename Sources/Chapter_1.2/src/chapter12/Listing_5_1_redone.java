package chapter12;


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


public class Listing_5_1_redone {

    // First column
    final static Number minXPointDl;
    final static Number maxXPointDh;

    // Second column - target data
    final static Number minTargetValueDl;
    final static Number maxTargetValueDh;

    static {

        minXPointDl = new NumberImpl(0.00D);
        maxXPointDh = new NumberImpl(5.00D);
        minTargetValueDl = new NumberImpl(0.00D);
        maxTargetValueDh = new NumberImpl(5.00D);
    }

    public static void main(String[] args) {

        //String inputFileName = "D:\\repositories\\NeuralNets\\Sources\\Chapter 1.2\\data\\training_data_set.csv";
        //String outputNormFileName = "D:\\repositories\\NeuralNets\\Sources\\Chapter 1.2\\data\\training_data_set_norm.csv";
        String inputFileName = "D:\\repositories\\NeuralNets\\Sources\\Chapter 1.2\\data\\test_data_set.csv";
        String outputNormFileName = "D:\\repositories\\NeuralNets\\Sources\\Chapter 1.2\\data\\test_data_set_norm2.csv";

        CsvDocument inputDocument = CsvHelper.loadCSVDocument(inputFileName);
        Table<String> inputData = inputDocument.getContent();

        ModifiableTable<String> outputData = new ModifiableTableImpl<String>();
        outputData.addColumn();
        outputData.addColumn();

        for (int row = 0; row < inputData.rows(); row++) {

            Number inputXPointValue = new NumberImpl(inputData.getCell(0, row));
            Number targetXPointValue = new NumberImpl(inputData.getCell(1, row));

            Number normInputXPointValue = Normalizer.normalize(inputXPointValue, maxXPointDh, minXPointDl);
            Number normTargetXPointValue = Normalizer.normalize(targetXPointValue, maxTargetValueDh, minTargetValueDl);

            outputData.addRow();
            outputData.updateCell(0, row, normInputXPointValue.toString());
            outputData.updateCell(1, row, normTargetXPointValue.toString());
        }

        CsvDocument outputDocument = CsvHelper.createNewDocumentWithSameProperties(inputDocument, outputData);
        CsvHelper.saveCsvDocument(outputNormFileName, outputDocument);
    }

}


final class CsvHelper {

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

final class Normalizer {

    final static Number Nh;
    final static Number Nl;

    final static double _Nh;
    final static double _Nl;

    static {

        Nh = createNumber(10, "1");
        Nl = createNumber(10, "-1");
        
        _Nh = 1D;
        _Nl = -1D;
    }

    public static Number normalize(Number value, Number Dh, Number Dl) {

        // (value - Dl) * (Nh - Nl) / (Dh - Dl) + Nl;

        double _value = Double.parseDouble(value.toString());
        double _Dh = Double.parseDouble(Dh.toString());
        double _Dl = Double.parseDouble(Dl.toString());

        Number term1 = value.subtract(Dl);
        double _term1 = _value - _Dl;
                
        Number term2 = Nh.subtract(Nl);
        double _term2 = _Nh - _Nl;

        Number term3 = Dh.subtract(Dl);
        double _term3 = _Dh - _Dl;

        Number term4 = Nl;
        double _term4 = _Nl;

        Number term5 = term1.multiply(term2);
        double _term5 = _term1 * _term2;

        Number term6 = term5.divide(FunctionIdentifiers.RUSSIAN_DIVISION_FUNCTION, term3);
        double _term6 = _term5 / _term3;

        Number term7 = term6.add(term4);
        double _term7 = _term6 + _term4;

        Number result = term1.multiply(term2)
                             .divide(FunctionIdentifiers.RUSSIAN_DIVISION_FUNCTION, term3)
                             .add(term4);
        
        double _result = _term1 * _term2 / _term3 + _term4;

        return result;
    }

}
