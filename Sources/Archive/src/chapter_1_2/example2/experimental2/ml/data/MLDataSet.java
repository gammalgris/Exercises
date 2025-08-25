package chapter_1_2.example_02.experimental2.ml.data;


import jmul.math.numbers.Number;

import org.encog.ml.data.MLData;


/**
 * An interface designed to abstract classes that store machine learning data.
 * This interface is designed to provide EngineDataSet objects. These can be
 * used to train machine learning methods using both supervised and unsupervised
 * training.
 *
 * Some implementations of this interface are memory based. That is they store
 * the entire contents of the dataset in memory.
 *
 * Other implementations of this interface are not memory based. These
 * implementations read in data as it is needed. This allows very large datasets
 * to be used. Typically the add methods are not supported on non-memory based
 * datasets.
 *
 * <i>Note:<br>
 * Changed the number types.</i>
 *
 * @author jheaton
 * @author Kristian Kutin
 */
public interface MLDataSet extends Iterable<MLDataPair> {

    /**
     * @return The size of the ideal data.
     */
    Number getIdealSize();

    /**
     * @return The size of the input data.
     */
    Number getInputSize();

    /**
     * @return True if this is a supervised training set.
     */
    boolean isSupervised();

    /**
     * Determine the total number of records in the set.
     *
     * @return The total number of records in the set.
     */
    Number getRecordCount();

    /**
     * Read an individual record, specified by index, in random order.
     *
     * @param index
     *            The index to read.
     * @param pair
     *            The pair that the record will be copied into.
     */
    Number getRecord(Number index, MLDataPair pair);

    /**
     * Opens an additional instance of this dataset.
     *
     * @return The new instance.
     */
    MLDataSet openAdditional();

    /**
     * Add a object to the dataset. This is used with unsupervised training, as
     * no ideal output is provided. Note: not all implemenations support the add
     * methods.
     *
     * @param data1
     *            The data item to be added.
     */
    void add(MLData data1);

    /**
     * Add a set of input and ideal data to the dataset. This is used with
     * supervised training, as ideal output is provided. Note: not all
     * implementations support the add methods.
     *
     * @param inputData
     *            Input data.
     * @param idealData
     *            Ideal data.
     */
    void add(MLData inputData, MLData idealData);

    /**
     * Add a an object to the dataset. This is used with unsupervised training,
     * as no ideal output is provided. Note: not all implementations support the
     * add methods.
     *
     * @param inputData
     *            A MLDataPair object that contains both input and ideal data.
     */
    void add(MLDataPair inputData);

    /**
     * Close this datasource and release any resources obtained by it, including
     * any iterators created.
     */
    void close();

    Number size();

    MLDataPair get(Number index);

}
