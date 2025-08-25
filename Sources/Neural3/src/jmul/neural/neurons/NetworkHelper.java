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


import java.util.List;

import jmul.functions.Function;

import jmul.math.Math;
import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.GlobalSettings;
import jmul.functions.Function;
import jmul.neural.signals.SignalListener;


/**
 * A utility class for connecting and initializing neuron networks.
 *
 * @author Kristian Kutin
 */
public final class NetworkHelper {

    /**
     * The default constructor.
     */
    private NetworkHelper() {

        throw new UnsupportedOperationException();
    }

    /**
     * Sets the weight in the specified snypase.
     *
     * @param synapse
     *        a synapse
     * @param base
     *        a number base
     * @param numberString
     *        a number string
     */
    public static void setWeight(Synapse synapse, int base, String numberString) {

        if (numberString == null) {

            throw new IllegalArgumentException("No number string (null) was specified!");
        }

        Number weight = createNumber(base, numberString);

        setWeight(synapse, weight);
    }

    /**
     * Sets the weight in the specified snypase.
     *
     * @param synapse
     *        a synapse
     * @param weight
     *        a weight
     */
    public static void setWeight(Synapse synapse, Number weight) {

        if (synapse == null) {

            throw new IllegalArgumentException("No synapse (null) was specified!");
        }

        if (weight == null) {

            throw new IllegalArgumentException("No weight (null) was specified!");
        }

        if (synapse instanceof SynapseImpl) {

            SynapseImpl synapse2 = (SynapseImpl) synapse;
            synapse2.setWeight(weight);

            return;
        }

        throw new IllegalArgumentException("The actual synapse type is unknown!");
    }

    /**
     * Sets the bias in the specified neuron.
     *
     * @param neuron
     *        a neuron
     * @param base
     *        a number base
     * @param numberString
     *        a number string
     */
    public static void setBias(Neuron neuron, int base, String numberString) {

        if (numberString == null) {

            throw new IllegalArgumentException("No number string (null) was specified!");
        }

        Number bias = createNumber(base, numberString);

        setBias(neuron, bias);
    }

    /**
     * Sets the bias in the specified neuron.
     *
     * @param neuron
     *        a neuron
     * @param bias
     *        a bias
     */
    public static void setBias(Neuron neuron, Number bias) {

        if (neuron == null) {

            throw new IllegalArgumentException("No neuron (null) was specified!");
        }

        if (bias == null) {

            throw new IllegalArgumentException("No bias (null) was specified!");
        }

        if (neuron instanceof NeuronImpl) {

            NeuronImpl neuron2 = (NeuronImpl) neuron;
            neuron2.setBias(bias);

            return;
        }

        throw new IllegalArgumentException("The actual neuron type is unknown!");
    }

    /**
     * Sets the activation functuion in the specified neuron.
     *
     * @param neuron
     *        a neuron
     * @param activationFunction
     *        an activation function
     */
    public static void setActivationFunction(Neuron neuron, Function activationFunction) {

        if (neuron == null) {

            throw new IllegalArgumentException("No neuron (null) was specified!");
        }

        if (activationFunction == null) {

            throw new IllegalArgumentException("No activation function (null) was specified!");
        }

        if (neuron instanceof NeuronImpl) {

            NeuronImpl neuron2 = (NeuronImpl) neuron;
            neuron2.setActivationFunction(activationFunction);

            return;
        }

        throw new IllegalArgumentException("The actual neuron type is unknown!");
    }

    /**
     * Links the specified neurons with the specified synapse.
     *
     * @param neuronFrom
     *        a neuron
     * @param synapse
     *        a synapse
     * @param neuronTo
     *        a neuron
     */
    public static void linkNeurons(Neuron neuronFrom, Synapse synapse, Neuron neuronTo) {

        if (neuronFrom == null) {

            throw new IllegalArgumentException("No neuron (null) was specified!");
        }

        if (synapse == null) {

            throw new IllegalArgumentException("No synapse (null) was specified!");
        }

        if (neuronTo == null) {

            throw new IllegalArgumentException("No neuron (null) was specified!");
        }

        if ((neuronFrom instanceof NeuronImpl) && (synapse instanceof SynapseImpl) &&
            (neuronTo instanceof NeuronImpl)) {

            NeuronImpl neuronFrom2 = (NeuronImpl) neuronFrom;
            SynapseImpl synapse2 = (SynapseImpl) synapse;
            NeuronImpl neuronTo2 = (NeuronImpl) neuronTo;

            neuronFrom2.addListener(synapse2);
            synapse2.addListener(neuronTo2);
            neuronTo2.addSignalSource(synapse2);

            return;
        }

        throw new IllegalArgumentException("The actual neuron type is unknown!");
    }

    /**
     * Links the specified neuron with the specified synapse.
     *
     * @param synapse
     *        a synapse
     * @param neuronTo
     *        a neuron
     */
    public static void linkInputNeuron(Synapse synapse, Neuron neuronTo) {

        if (synapse == null) {

            throw new IllegalArgumentException("No synapse (null) was specified!");
        }

        if (neuronTo == null) {

            throw new IllegalArgumentException("No neuron (null) was specified!");
        }

        if (synapse instanceof SynapseImpl) {

            SynapseImpl synapse2 = (SynapseImpl) synapse;
            NeuronImpl neuronTo2 = (NeuronImpl) neuronTo;

            synapse2.addListener(neuronTo2);
            neuronTo2.addSignalSource(synapse2);

            return;
        }

        throw new IllegalArgumentException("Opps! Something went wrong!");
    }

    /**
     * Links the specified neurons with the specified synapse.
     *
     * @param neuronFrom
     *        a neuron
     * @param synapse
     *        a synapse
     */
    public static void linkOutputNeuron(Neuron neuronFrom, Synapse synapse) {

        if (neuronFrom == null) {

            throw new IllegalArgumentException("No neuron (null) was specified!");
        }

        if (synapse == null) {

            throw new IllegalArgumentException("No synapse (null) was specified!");
        }

        if (neuronFrom instanceof NeuronImpl) {

            NeuronImpl neuronFrom2 = (NeuronImpl) neuronFrom;

            neuronFrom2.addListener(synapse);

            return;
        }

        throw new IllegalArgumentException("Opps! Something went wrong!");
    }

    /**
     *
     * @param synapse
     * @param listener
     */
    public static void linkOutputSynapse(Synapse synapse, SignalListener listener) {

        if (synapse == null) {

            throw new IllegalArgumentException("No synapse (null) was specified!");
        }

        if (listener == null) {

            throw new IllegalArgumentException("No listener (null) was specified!");
        }

        if (synapse instanceof SynapseImpl) {

            SynapseImpl synapse2 = (SynapseImpl) synapse;

            synapse2.addListener(listener);

            return;
        }

        throw new IllegalArgumentException("Opps! Something went wrong!");
    }

    /**
     * Returns a random weight (i.e. number).
     *
     * @return a weight
     */
    public static Number randomWeight() {

        Number digits = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "10");
        Number randomNumber = Math.random(GlobalSettings.DEFAULT_NUMBER_BASE, digits);

        return randomNumber;
    }

    /**
     * Returns a random bias (i.e. number).
     *
     * @return a bias
     */
    public static Number randomBias() {

        Number digits = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "10");
        Number randomNumber = Math.random(GlobalSettings.DEFAULT_NUMBER_BASE, digits);

        return randomNumber;
    }

    /**
     * Clones the specified neural network.
     *
     * @param network
     *        a neural network
     *
     * @return a clone
     */
    public static Network clone(Network network) {

        if (network instanceof FlatNetworkImpl) {

            FlatNetworkImpl network2 = (FlatNetworkImpl) network;
            return clone(network2);
        }

        throw new IllegalArgumentException("An unknown neural network type was specified!");
    }

    /**
     * Clones the specified neural network.
     *
     * @param network
     *        a neural network
     *
     * @return a clone
     */
    private static FlatNetworkImpl clone(FlatNetworkImpl network) {

        FlatNetworkImpl clonedNetwork = new FlatNetworkImpl(network.configuration());

        {
            List<Neuron> allOriginals = network.neurons();
            List<Neuron> allClones = clonedNetwork.neurons();

            int max = allOriginals.size();

            for (int index = 0; index < max; index++) {

                Neuron original = allOriginals.get(index);
                Neuron clone = allClones.get(index);

                Number bias = original.bias();
                setBias(clone, bias);

                Function activationFunction = original.activationFunction();
                setActivationFunction(clone, activationFunction);
            }
        }

        {
            List<Synapse> allOriginals = network.synapses();
            List<Synapse> allClones = clonedNetwork.synapses();

            int max = allOriginals.size();

            for (int index = 0; index < max; index++) {

                Synapse original = allOriginals.get(index);
                Synapse clone = allClones.get(index);

                Number weight = original.weight();
                setWeight(clone, weight);
            }
        }

        return clonedNetwork;
    }

}
