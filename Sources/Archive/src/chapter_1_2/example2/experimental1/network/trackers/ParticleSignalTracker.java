package chapter_1_2.example_02.experimental1.network.trackers;


import chapter_1_2.example_02.experimental1.network.Neuron;
import chapter_1_2.example_02.experimental1.network.signals.ParticleSignal;
import chapter_1_2.example_02.experimental1.network.signals.Signal;

import java.util.HashMap;
import java.util.Map;

import jmul.math.Math;
import jmul.math.numbers.Number;
import jmul.math.numbers.NumberImpl;


public class ParticleSignalTracker implements SignalTracker {

    private static final Number ZERO = new NumberImpl(10, "0");

    private Map<Neuron, Summary> trackers;

    public ParticleSignalTracker() {

        super();

        trackers = new HashMap<>();
    }

    private Summary initialSummary() {


        Number particles = ZERO;

        long now = System.currentTimeMillis();
        Number penultimateSignal = new NumberImpl(now);
        Number lastSignal = new NumberImpl(now);

        Number peakAmplitude = ZERO;
        Number amplitude = ZERO;

        return new Summary(particles, penultimateSignal, lastSignal, peakAmplitude, amplitude);
    }

    @Override
    public SignalTracker addSignal(Signal signal) {

        if (signal instanceof ParticleSignal) {

            ParticleSignal particleSignal = (ParticleSignal) signal;
            Neuron origin = signal.origin();

            Summary summary;
            synchronized (this) {

                summary = trackers.get(origin);
            }

            if (summary == null) {

                summary = initialSummary();
            }

            summary = summary.updateSummary(particleSignal);

            synchronized (this) {

                trackers.put(origin, summary);
            }
        }

        return this;
    }

    @Override
    public Number frequency(Neuron neuron) {

        Summary summary;
        synchronized (this) {

            summary = trackers.get(neuron);
        }

        if (summary == null) {

            return ZERO;
        }

        Number period = summary.lastSignal.subtract(summary.penultimateSignal);

        if (period.isZero()) {

            return ZERO;
        }

        return period.reciprocal().evaluate();
    }


    @Override
    public Number amplitude(Neuron neuron) {

        Summary summary;
        synchronized (this) {

            summary = trackers.get(neuron);
        }

        if (summary == null) {

            return ZERO;
        }

        return summary.amplitude;
    }

    @Override
    public Number particles(Neuron neuron) {

        Summary summary;
        synchronized (this) {

            summary = trackers.get(neuron);
        }

        if (summary == null) {

            return ZERO;
        }

        return summary.particles;
    }

    @Override
    public Number peakAmplitude(Neuron neuron) {

        Summary summary;
        synchronized (this) {

            summary = trackers.get(neuron);
        }

        if (summary == null) {

            return ZERO;
        }

        return summary.peakAmplitude;
    }

}

class Summary {

    public final Number particles;

    public final Number penultimateSignal;

    public final Number lastSignal;

    public final Number peakAmplitude;

    public final Number amplitude;

    public Summary(Number particles, Number penultimateSignal, Number lastSignal, Number peakAmplitude,
                   Number amplitude) {

        super();

        this.particles = checkNumber(particles);
        this.penultimateSignal = checkNumber(penultimateSignal);
        this.lastSignal = checkNumber(lastSignal);
        this.peakAmplitude = checkNumber(peakAmplitude);
        this.amplitude = checkNumber(amplitude);
    }

    private Number checkNumber(Number number) {

        if (number == null) {

            throw new IllegalArgumentException("No number (null) was specified!");
        }

        return number;
    }

    public Summary updateSummary(ParticleSignal signal) {

        Number newParticles = particles.add(signal.particles());
        Number newPenultimateSignal = lastSignal;
        Number newLastSignal = new NumberImpl(System.currentTimeMillis());
        Number newPeakAmplitude = Math.max(peakAmplitude, signal.particles());
        Number newAmplitude = signal.particles();

        return new Summary(newParticles, newPenultimateSignal, newLastSignal, newPeakAmplitude, newAmplitude);
    }

}
