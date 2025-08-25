package chapter_1_2.example_02.experimental2.ml.data;


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

import jmul.misc.table.Table;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;


public final class DataHelper {

    private DataHelper() {

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

    /**
     * Convert an external file format, such as CSV, to an Encog memory training
     * set.
     * 
     * @return The binary file to create.
     */
    public final MLDataSet loadTRainingexternal2Memory() {
            this.status.report(0, 0, "Importing to memory");

            if (this.result == null) {
                    this.result = new BasicMLDataSet();
            }

            final double[] input = new double[this.codec.getInputSize()];
            final double[] ideal = new double[this.codec.getIdealSize()];
            final double[] significance = new double[1];

            this.codec.prepareRead();

            int currentRecord = 0;
            int lastUpdate = 0;

            while (this.codec.read(input, ideal, significance)) {
                    MLData a = null, b = null;

                    a = new BasicMLData(input);

                    if (this.codec.getIdealSize() > 0) {
                            b = new BasicMLData(ideal);
                    }

                    final org.encog.ml.data.MLDataPair pair = new BasicMLDataPair(a, b);
                    pair.setSignificance(significance[0]);
                    this.result.add(pair);

                    currentRecord++;
                    lastUpdate++;
                    if (lastUpdate >= 10000) {
                            lastUpdate = 0;
                            this.status.report(0, currentRecord, "Importing...");
                    }
            }

            this.codec.close();
            this.status.report(0, 0, "Done importing to memory");
            return this.result;
    }

}
