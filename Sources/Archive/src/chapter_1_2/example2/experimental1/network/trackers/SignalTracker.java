package chapter_1_2.example_02.experimental1.network.trackers;


import chapter_1_2.example_02.experimental1.network.Neuron;
import chapter_1_2.example_02.experimental1.network.signals.Signal;

import jmul.math.numbers.Number;


public interface SignalTracker {

    SignalTracker addSignal(Signal signal);

    Number frequency(Neuron neuron);

    Number peakAmplitude(Neuron neuron);

    Number amplitude(Neuron neuron);

    Number particles(Neuron neuron);

}
