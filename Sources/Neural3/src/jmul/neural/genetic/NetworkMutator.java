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

package jmul.neural.genetic;


import java.util.ArrayList;
import java.util.List;

import jmul.functions.Function;
import jmul.functions.FunctionHelper;

import jmul.genetic.Mutator;

import jmul.math.numbers.Number;

import static jmul.neural.genetic.TypesOfChange.MUTATE_NEURON_ACTIVATION_FUNCTION;
import static jmul.neural.genetic.TypesOfChange.MUTATE_NEURON_BIAS;
import static jmul.neural.genetic.TypesOfChange.MUTATE_SYNAPSE_WEIGHT;
import jmul.neural.neurons.Network;
import jmul.neural.neurons.NetworkHelper;
import jmul.neural.neurons.Neuron;
import jmul.neural.neurons.Synapse;


public class NetworkMutator implements Mutator<Network> {

    private final int maxNumberOfChanges;

    public NetworkMutator(int maxNumberOfChanges) {

        super();

        if (maxNumberOfChanges < 1) {

            throw new IllegalArgumentException("No valid maximum number for changes (x < 1) was specified!");
        }

        this.maxNumberOfChanges = maxNumberOfChanges;
    }

    private List<TypeOfChange> randomChanges() {

        List<TypeOfChange> changes = new ArrayList<>();

        for (int n = 0; n < maxNumberOfChanges; n++) {

            TypeOfChange change = TypesOfChange.randomTypeOfChange();
            changes.add(change);
        }

        return changes;
    }

    @Override
    public Network mutate(Network network) {

        if (network == null) {

            throw new IllegalArgumentException("No neural network (null) was specified!");
        }

        Network clonedNetwork = NetworkHelper.clone(network);

        List<TypeOfChange> allChanges = randomChanges();

        for (TypeOfChange change : allChanges) {

            mutateNetwork(clonedNetwork, change);
        }

        return clonedNetwork;
    }

    private void mutateNetwork(Network network, TypeOfChange typeOfChange) {

        if (typeOfChange == null) {

            throw new IllegalArgumentException("No type of change (null) was specified!");
        }

        if (MUTATE_NEURON_ACTIVATION_FUNCTION.equals(typeOfChange)) {

            mutateActivationFunction(network);

        } else if (MUTATE_NEURON_BIAS.equals(typeOfChange)) {

            mutateBias(network);

        } else if (MUTATE_SYNAPSE_WEIGHT.equals(typeOfChange)) {

            mutateWeight(network);

        } else {

            throw new IllegalArgumentException("An unknown type of change was specified!");
        }
    }

    /**
     * The activation function of a random neruon is mutated.
     *
     * @param network
     *        a neural network
     */
    private void mutateActivationFunction(Network network) {

        List<Neuron> allNeurons = network.neurons();

        int min = 0;
        int max = allNeurons.size();

        int randomIndex = (int) (Math.random() * ((double) max - (double) min) + (double) min);

        Neuron neuron = allNeurons.get(randomIndex);

        Function newActivationFunction = FunctionHelper.randomActivationFunction();
        NetworkHelper.setActivationFunction(neuron, newActivationFunction);
    }

    /**
     * The bias of a random neuron is mutated.
     *
     * @param network
     *        a neural network
     */
    private void mutateBias(Network network) {

        List<Neuron> allNeurons = network.neurons();

        int min = 0;
        int max = allNeurons.size();

        int randomIndex = (int) (Math.random() * ((double) max - (double) min) + (double) min);

        Neuron neuron = allNeurons.get(randomIndex);

        Number newBias = NetworkHelper.randomBias();
        NetworkHelper.setBias(neuron, newBias);
    }

    /**
     * The weight of a random synapse is mutated.
     *
     * @param network
     *        a neural network
     */
    private void mutateWeight(Network network) {

        List<Synapse> allSynapses = network.synapses();

        int min = 0;
        int max = allSynapses.size();

        int randomIndex = (int) (Math.random() * ((double) max - (double) min) + (double) min);

        Synapse synapse = allSynapses.get(randomIndex);

        Number newWeight = NetworkHelper.randomBias();
        NetworkHelper.setWeight(synapse, newWeight);
    }

}
