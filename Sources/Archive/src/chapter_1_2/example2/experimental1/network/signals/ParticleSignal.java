package chapter_1_2.example_02.experimental1.network.signals;


import jmul.math.numbers.Number;

import chapter_1_2.example_02.experimental1.network.Neuron;


public class ParticleSignal implements Signal {

    private final Neuron origin;

    private final Number particles;

    public ParticleSignal(Neuron origin, Number particles) {

        super();

        this.origin = checkOrigin(origin);
        this.particles = checkParticles(particles);
    }

    private static Neuron checkOrigin(Neuron origin) {

        if (origin == null) {

            throw new IllegalArgumentException("No origin (null) was specified!");
        }

        return origin;
    }

    private static Number checkParticles(Number particles) {

        if (particles == null) {

            throw new IllegalArgumentException("No number of particles (null) was specified!");
        }

        return particles;
    }

    @Override
    public Neuron origin() {

        return origin;
    }

    public Number particles() {

        return particles;
    }

    @Override
    public String toString() {

        return String.format("%s particels from neuron [%d]", particles, origin.hashCode());
    }

}
