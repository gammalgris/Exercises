package chapter_1_2.example_02.experimental1.network;


import java.util.List;


/**
 * Sketch/ idea:
 * 
 * 1) Define neurons.
 * 
 * 2) Every neuron is associated with one synapse (i.e. connect one neuron with one synapse).
 * 
 * 3) Every synapse gets a random threshold (or default threshold?). One threshold for all connected neurons?
 *    one threshold for each connected neuron?
 * 
 * 3) Of the set of neurons define X neurons as input neurons.
 * 
 * 4) Of the set of neurons define Y neurons as output neurons.
 * 
 * 5) There remains a subset Z of free neurons.
 * 
 * 5) The subsets X and Y and Z are disjunct.
 * 
 * 6) Connect the neurons from set X with the neurons in subset Y via the synapses in X.
 * 
 * 7) Send signals to the input neurons. Each signal is a set of particles. Each neuron has a threshold,
 *    i.e. a number of particles that must be exceeded in order to transmit a signal to the next neurons.
 *    When the threshold is reached the difference of the accumulated particles and threshold is transmitted
 *    to the next neurons. The accumulated particles are reset.
 *    
 * 8) Transmission Feedback
 *    A neuron gets feedback if and when the transmitted signal has been transmitted (i.e. supassed the threshold).
 *    
 * 9) Connect free neurons
 *    ?
 * 
 * 10) Disconnect euron
 *    ?
 *    
 * 11) Change threshold
 *    ?
 * 
 * ?
 * 
 */
public interface Group {

    List<Cell> cells();

    List<Synapse> synapses();

    List<Neuron> inputNeurons();

    List<Neuron> outputNeurons();

}
