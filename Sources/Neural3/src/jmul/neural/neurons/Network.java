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

import jmul.math.numbers.Number;


/**
 * This interface describes a neural network.
 */
public interface Network {

    /**
     * Returns the number of layers of this neural network.
     *
     * @return the number of layers
     */
    int layers();

    /**
     * Returns the neuron count for the specified layer.
     *
     * @param layer
     *        a layer of the neural network
     *
     * @return the neuron count in the specified layer
     */
    int neuronCount(int layer);

    /**
     * Returns the toal neuron count in this neural network.
     *
     * @return the total neuron count
     */
    int neuronCount();

    /**
     * Returns the input synapse.
     *
     * @return a synapse
     */
    Synapse inputSynapse();

    /**
     * Returns the output synapse.
     *
     * @return a synapse
     */
    Synapse outputSynapse();

    /**
     * Returns all neurons of the first layer (i.e. input layer).
     *
     * @return all neurons of the first layer
     */
    List<Neuron> firstLayer();

    /**
     * Returns all neurons of the last layer (i.e. output layer).
     *
     * @return all neurons of the last layer
     */
    List<Neuron> lastLayer();

    /**
     * Returns a list of all neurons accross all layers.
     *
     * @return all neurons accross all layers
     */
    List<Neuron> neurons();

    /**
     * Returns a list of all synapses.
     *
     * @return all synapses
     */
    List<Synapse> synapses();

    /**
     * Sends a signal with specified input into the neural network and waits for the output signal.
     * This operation should only be called on an activated neural network.
     *
     * @param input
     *        a number
     *
     * @return the corresponding output
     */
    Number send(Number input);

    /**
     * Returns the signal flow type for this network.
     *
     * @return a signal flow type
     */
    SignalFlowType signalFLowType();

}
