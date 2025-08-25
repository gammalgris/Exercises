/*
 * SPDX-License-Identifier: GPL-3.0
 *
 *
 * (J)ava (M)iscellaneous (U)tilities (L)ibrary
 *
 * JMUL is a central repository for utilities which are used in my
 * other public and private repositories.
 *
 * Copyright (C) 2025  Kristian Kutin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * e-mail: kristian.kutin@arcor.de
 */

/*
 * This section contains meta informations.
 *
 * $Id$
 */

package jmul.neural.neurons;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jmul.math.numbers.Number;

import jmul.neural.GlobalSettings;
import jmul.functions.Function;
import jmul.neural.functions.ActivationFunctions;
import static jmul.neural.neurons.NetworkHelper.linkInputNeuron;
import static jmul.neural.neurons.NetworkHelper.linkNeurons;
import static jmul.neural.neurons.NetworkHelper.linkOutputNeuron;
import static jmul.neural.neurons.NetworkHelper.linkOutputSynapse;
import static jmul.neural.neurons.NetworkHelper.setActivationFunction;
import static jmul.neural.neurons.NetworkHelper.setBias;
import static jmul.neural.neurons.NetworkHelper.setWeight;
import jmul.neural.signals.Signal;
import jmul.neural.signals.SignalImpl;
import jmul.neural.signals.SignalListener;
import jmul.neural.signals.SignalSource;

import static jmul.string.Constants.NEW_LINE;
import static jmul.string.Constants.TABULATOR;


/**
 * An implementation of a flat neural network.
 *
 * @author Kristian Kutin
 */
public class FlatNetworkImpl implements Network, SignalListener {

    private final static SignalSource OUTSIDE;

    /**
     * The index for the frist network layer.
     */
    private final static int FIRST_LAYER_INDEX;

    /*
     * The static initializer.
     */
    static {

        FIRST_LAYER_INDEX = 0;
        OUTSIDE = new SignalSource() {
        };
    }

    /**
     * The configuration for this network.
     */
    private int[] configuration;

    /**
     * All neurons by network layers.
     */
    private List<List<Neuron>> networkLayers;

    /**
     * All synapses.
     */
    private List<Synapse> synapses;

    /**
     * A synapse which transmits a signal to the input layer.
     */
    private Synapse inputSynapse;

    /**
     * A synapse which receives a signal from the output layer.
     */
    private Synapse outputSynapse;

    private Signal lastSignal;

    /**
     * Creates a new network according ot the specified layout.
     *
     * @param neuronsPerLayer
     *        the number of neurons per layer
     */
    public FlatNetworkImpl(int... neuronsPerLayer) {

        super();

        if (neuronsPerLayer == null) {

            throw new IllegalArgumentException("No network layout (null) was specified!");
        }

        if (neuronsPerLayer.length < 2) {

            throw new IllegalArgumentException("At least two layers (input layer and output layer) are needed!");
        }

        this.configuration = neuronsPerLayer;
        this.networkLayers = new ArrayList<>();
        this.synapses = new ArrayList<>();
        this.inputSynapse = null;
        this.outputSynapse = null;

        initializeNeurons(neuronsPerLayer);
        checkInputLayer();
        checkOutputLayer();
        initializeSynapses();
        initializeInputSynapse();
        initializeOutputSynapse();
    }

    /**
     * Returns the configuration for this flat neural network.
     *
     * @return a configuration (i.e. neurons per layer)
     */
    public int[] configuration() {

        return configuration;
    }

    /**
     * Initializes all neurons with a random bias.
     *
     * @param neuronsPerLayer
     *        the number of neurons per layer
     */
    private void initializeNeurons(int... neuronsPerLayer) {

        for (int layer = FIRST_LAYER_INDEX; layer < neuronsPerLayer.length; layer++) {

            int neuronCount = neuronsPerLayer[layer];

            List<Neuron> neurons = new ArrayList<>();

            for (int count = 0; count < neuronCount; count++) {

                Neuron neuron = new NeuronImpl();
                Number randomBias = NetworkHelper.randomBias();
                setBias(neuron, randomBias);

                Function activationFunction = ActivationFunctions.randomActivationFunction();
                setActivationFunction(neuron, activationFunction);

                neurons.add(neuron);
            }

            networkLayers.add(neurons);
        }
    }

    /**
     * Checks the input layer.
     */
    private void checkInputLayer() {

        if (firstLayer().size() != 1) {

            throw new IllegalArgumentException("The input layer should only have one neuron!");
        }
    }

    /**
     * Checks the output layer.
     */
    private void checkOutputLayer() {

        if (lastLayer().size() != 1) {

            throw new IllegalArgumentException("The output layer should only have one neuron!");
        }
    }

    /**
     * Initializes all synapses and connects all neurons accross layers.
     */
    private void initializeSynapses() {

        int layerIndex = FIRST_LAYER_INDEX;
        int nextLayerIndex = 1;
        int lastLayerIndex = networkLayers.size() - 1;

        while (layerIndex < lastLayerIndex) {

            for (Neuron neuronFrom : networkLayers.get(layerIndex)) {

                for (Neuron neuronTo : networkLayers.get(nextLayerIndex)) {

                    Synapse synapse = new SynapseImpl();
                    Number randomWeight = NetworkHelper.randomWeight();
                    setWeight(synapse, randomWeight);

                    linkNeurons(neuronFrom, synapse, neuronTo);
                    synapses.add(synapse);
                }
            }

            layerIndex++;
            nextLayerIndex++;
        }
    }

    /**
     * Initializes the input synapse.
     */
    private void initializeInputSynapse() {

        Synapse synapse = new SynapseImpl();
        setWeight(synapse, GlobalSettings.DEFAULT_NUMBER_BASE, "1");

        Neuron neuron = firstLayer().get(0);
        linkInputNeuron(synapse, neuron);
        synapses.add(0, synapse);

        this.inputSynapse = synapse;
    }

    /**
     * Initializes the output synapse.
     */
    private void initializeOutputSynapse() {

        Synapse synapse = new SynapseImpl();
        setWeight(synapse, GlobalSettings.DEFAULT_NUMBER_BASE, "1");

        Neuron neuron = lastLayer().get(0);
        linkOutputNeuron(neuron, synapse);
        synapses.add(synapse);

        this.outputSynapse = synapse;
        linkOutputSynapse(outputSynapse, this);
    }

    /**
     * Returns the number of layers of this neural network.
     *
     * @return the number of layers
     */
    @Override
    public int layers() {

        return this.networkLayers.size();
    }

    /**
     * Returns the neuron count for the specified layer.
     *
     * @param layer
     *        a layer of the neural network
     *
     * @return the neuron count in the specified layer
     */
    @Override
    public int neuronCount(int layer) {

        return this.networkLayers
                   .get(layer)
                   .size();
    }

    /**
     * Returns the toal neuron count in this neural network.
     *
     * @return the total neuron count
     */
    @Override
    public int neuronCount() {

        int sum = 0;

        for (List<Neuron> neurons : networkLayers) {

            sum += neurons.size();
        }

        return sum;
    }

    /**
     * Returns the input synapse.
     *
     * @return a synapse
     */
    @Override
    public Synapse inputSynapse() {

        return inputSynapse;
    }

    /**
     * Returns the output synapse.
     *
     * @return a synapse
     */
    @Override
    public Synapse outputSynapse() {

        return outputSynapse;
    }

    /**
     * Returns all neurons of the first layer (i.e. input layer).
     *
     * @return all neurons of the first layer
     */
    @Override
    public List<Neuron> firstLayer() {

        return Collections.unmodifiableList(networkLayers.get(FIRST_LAYER_INDEX));
    }

    /**
     * Returns all neurons of the last layer (i.e. output layer).
     *
     * @return all neurons of the last layer
     */
    @Override
    public List<Neuron> lastLayer() {

        int lastIndex = layers() - 1;

        return Collections.unmodifiableList(networkLayers.get(lastIndex));
    }

    /**
     * Returns all list of all neurons accross all layers.
     *
     * @return all neurons accross all layers
     */
    @Override
    public List<Neuron> neurons() {

        List<Neuron> allNeurons = new ArrayList<>();

        for (int layerIndex = FIRST_LAYER_INDEX; layerIndex < layers(); layerIndex++) {

            List<Neuron> neuronsInLayer = networkLayers.get(layerIndex);

            for (Neuron neuron : neuronsInLayer) {

                allNeurons.add(neuron);
            }
        }

        return Collections.unmodifiableList(allNeurons);
    }

    /**
     * Returns a list of all synapses.
     *
     * @return all synapses
     */
    @Override
    public List<Synapse> synapses() {

        return Collections.unmodifiableList(synapses);
    }

    /**
     * Sends a signal with specified input into the neural network and waits for the output signal.
     * This operation should only be called on an activated neural network.
     *
     * @param input
     *        a number
     *
     * @return the corresponding output
     */
    @Override
    public Number send(Number input) {

        Signal inputSignal = new SignalImpl(OUTSIDE, input);

        inputSynapse.sendSignal(inputSignal);
        Number output = lastSignal.value();

        return output;
    }

    @Override
    public void sendSignal(Signal signal) {

        this.lastSignal = signal;
    }

    /**
     * Returns the signal flow type for this network.
     *
     * @return a signal flow type
     */
    @Override
    public SignalFlowType signalFLowType() {

        return SignalFlowTypes.FEEDFORWARD;
    }

    @Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();

        for (int layer = 0; layer < layers(); layer++) {

            buffer.append("neuron layer #");
            buffer.append((layer + 1));
            buffer.append(NEW_LINE);

            List<Neuron> neuronsInLayer = networkLayers.get(layer);
            for (Neuron neuron : neuronsInLayer) {

                buffer.append(neuron);
                buffer.append(TABULATOR);
                buffer.append("bias=");
                buffer.append(neuron.bias());
                buffer.append(TABULATOR);
                buffer.append("activation function=");
                buffer.append(neuron.activationFunction());
                buffer.append(NEW_LINE);
            }
        }

        buffer.append(NEW_LINE);

        buffer.append("synapses");
        buffer.append(NEW_LINE);

        for (Synapse synapse : synapses()) {

            buffer.append(synapse);
            buffer.append(TABULATOR);
            buffer.append("weight=");
            buffer.append(synapse.weight());
            buffer.append(NEW_LINE);
        }

        return buffer.toString();
    }

}
