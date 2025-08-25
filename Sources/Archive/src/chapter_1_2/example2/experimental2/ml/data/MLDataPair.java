package chapter_1_2.example_02.experimental2.ml.data;


import jmul.math.numbers.Number;
import org.encog.ml.data.MLData;
import org.encog.util.kmeans.CentroidFactory;


/**
 * Training data is stored in two ways, depending on if the data is for
 * supervised, or unsupervised training.
 *
 * For unsupervised training just an input value is provided, and the ideal
 * output values are null.
 *
 * For supervised training both input and the expected ideal outputs are
 * provided.
 *
 * This interface abstracts classes that provide a holder for both of these two
 * data items.
 *
 * <i>Note:<br>
 * Changed the number types.</i>
 *
 * @author jheaton
 * @author Kristian Kutin
 */
public interface MLDataPair extends CentroidFactory<org.encog.ml.data.MLDataPair> {

    /**
     * @return The ideal data that the machine learning method should produce 
     * for the specified input.
     */
    Number[] getIdealArray();

    /**
     * @return The input that the neural network
     */
    Number[] getInputArray();

    /**
     * Set the ideal data, the desired output.
     * 
     * @param data
     *            The ideal data.
     */
    void setIdealArray(Number[] data);

    /**
     * Set the input.
     * 
     * @param data
     *            The input.
     */
    void setInputArray(Number[] data);

    /**
     * @return True if this training pair is supervised. That is, it has both
     *         input and ideal data.
     */
    boolean isSupervised();
    
    /**
     * @return The ideal data that the neural network should produce for the
     *         specified input.
     */
    MLData getIdeal();

    /**
     * @return The input that the neural network
     */
    MLData getInput();
    
    /**
     * Get the significance, 1.0 is neutral.
     * @return The significance.
     */
    Number getSignificance();
    
    /**
     * Set the significance, 1.0 is neutral.
     * @param s The significance.
     */
    void setSignificance(Number s);

}
