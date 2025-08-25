package chapter_1_2.example_02.experimental1.network;


import jmul.math.numbers.Number;
import jmul.math.numbers.NumberImpl;
import chapter_1_2.example_02.experimental1.network.signals.Feedback;
import chapter_1_2.example_02.experimental1.network.signals.Signal;

import java.util.HashMap;
import java.util.Map;

import jmul.misc.state.State;


public class NeuronImpl extends CellBase implements Neuron {

    private final Map<Neuron, Number> thresholds;
    
    private final Number creationDate;

    public NeuronImpl(Number thresholds) {

        super();

        this.thresholds = new HashMap<>();
        this.creationDate = new NumberImpl(System.currentTimeMillis());
    }

    @Override
    public void cloneAndReplaceOldCell() {
        // TODO Implement this method
    }

    @Override
    public void run() {
        // TODO Implement this method
    }

    @Override
    public Feedback processSignal(Signal signal) {

        
        // TODO Implement this method
        return null;
    }

    @Override
    public State state() {
        // TODO Implement this method
        return null;
    }

}
