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

package jmul.neural.training;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import jmul.concurrent.threads.ThreadHelper;

import jmul.data.DataEntry;
import jmul.data.TrainingData;

import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.metainfo.annotations.Modified;

import jmul.neural.GlobalSettings;
import jmul.neural.neurons.Layers;
import jmul.neural.neurons.Network;
import jmul.neural.neurons.NetworkHelper;
import jmul.neural.neurons.Synapse;

public class ConcurrentSynapseTrainer implements NetworkTrainer {

    public ConcurrentSynapseTrainer() {

        super();
    }

    @Override
    public Number trainNetwork(@Modified Network network, TrainingData trainingData) {

        Map<Integer, Number> newWeights = new HashMap<>();
        List<ConcurrentSynapseTrainerThread> threads = new ArrayList<>();

        System.out.println("DEBUG::initialize threads");
        List<Synapse> synapseList = network.synapses();
        for (int index = 0; index < synapseList.size(); index++) {

            Synapse synapse = synapseList.get(index);
            if (!(synapse.layer() == Layers.HIDDEN_LAYER)) {

                continue;
            }

            Network clonedNetwork = NetworkHelper.clone(network);
            Synapse clonedSynapse = clonedNetwork.synapses().get(index);

            ConcurrentSynapseTrainerThread thread =
                new ConcurrentSynapseTrainerThread(clonedNetwork, clonedSynapse, index, trainingData);
            String name = "training thread #" + index;
            thread.setName(name);

            threads.add(thread);
        }

        for (ConcurrentSynapseTrainerThread thread : threads) {

            thread.start();
        }

        while (true) {

            boolean done = true;
            for (ConcurrentSynapseTrainerThread thread : threads) {

                done = done && !thread.isAlive();
            }

            if (done) {

                break;
            }

            ThreadHelper.sleep(500L);

            System.out.print(".");
        }

        System.out.println("training done");

        for (ConcurrentSynapseTrainerThread thread : threads) {

            newWeights.put(thread.index(), thread.newWeight());
        }

        for (Map.Entry<Integer, Number> entry : newWeights.entrySet()) {

            int index = entry.getKey();
            Number newWeight = entry.getValue();

            Synapse synapse = network.synapses().get(index);
            NetworkHelper.setWeight(synapse, newWeight);
        }

        System.out.println("network updated");

        //TODO mean squared error
        return null;
    }

}


interface ConcurrentTrainer extends Runnable {

    int index();

    Number newWeight();

}

class ConcurrentSynapseTrainerThread extends Thread implements ConcurrentTrainer {

    private final Network network;

    private final Synapse synapse;

    private final int index;

    private TrainingData trainingData;

    private Number newWeight;

    public ConcurrentSynapseTrainerThread(@Modified Network network, @Modified Synapse synapse, int index,
                                          TrainingData trainingData) {

        super();

        this.network = network;
        this.synapse = synapse;
        this.index = index;
        this.trainingData = trainingData;
        this.newWeight = null;
    }

    @Override
    public int index() {

        return index;
    }

    @Override
    public Number newWeight() {

        return newWeight;
    }

    @Override
    public void run() {

        this.newWeight = trainSynapse(network, synapse, trainingData);
    }

    private static Number trainSynapse(@Modified Network network, @Modified Synapse synapse,
                                       TrainingData trainingData) {

        List<Number> bestWeights = new ArrayList<>();
        Number oldWeight = synapse.weight();

        for (DataEntry entry : trainingData) {

            //System.out.println("\tDEBUG::train on data point " + entry);

            SortedMap<Number, Number> weightsAndDeviations = new TreeMap<>();

            Number weight = oldWeight;
            NetworkHelper.setWeight(synapse, oldWeight);

            Number actualOutput;
            Number deviation;

            // #1 data point
            actualOutput = network.send(entry.input);
            deviation = entry.expectedOutput.subtract(actualOutput);
            weightsAndDeviations.put(weight, deviation);
            //System.out.print("\t.");

            Number step = deviation.shiftLeft();

            // try to find a better weight for the current synapse. Try in both directions from the data point.
            for (int a = 0; a < 10; a++) {

                weight = weight.add(step);
                NetworkHelper.setWeight(synapse, weight);

                actualOutput = network.send(entry.input);
                deviation = entry.expectedOutput.subtract(actualOutput);
                weightsAndDeviations.put(weight, deviation);

                //System.out.print(".");
            }

            weight = oldWeight;
            for (int a = 0; a < 10; a++) {

                weight = weight.subtract(step);
                NetworkHelper.setWeight(synapse, weight);

                actualOutput = network.send(entry.input);
                deviation = entry.expectedOutput.subtract(actualOutput);
                weightsAndDeviations.put(weight, deviation);

                //System.out.print(".");
            }

            Number newWeight = findWeightWithSmallestDeviation(weightsAndDeviations);
            bestWeights.add(newWeight);
        }

        Number averageWeight = averageWeight(bestWeights);

        return averageWeight;
    }

    private static Number findWeightWithSmallestDeviation(SortedMap<Number, Number> weightsAndDeviations) {

        Number weight = null;
        Number deviation = null;
        Number absoluteDeviation = null;

        for (Map.Entry<Number, Number> entry : weightsAndDeviations.entrySet()) {

            if (weight == null) {

                weight = entry.getKey();
                deviation = entry.getValue();
                absoluteDeviation = deviation.absoluteValue();

            } else {

                Number nextWeight = entry.getKey();
                Number nextDeviation = entry.getValue();
                Number nextAbsoluteDeviation = deviation.absoluteValue();

                if (nextAbsoluteDeviation.isLesser(absoluteDeviation)) {

                    weight = nextWeight;
                    deviation = nextDeviation;
                    absoluteDeviation = nextAbsoluteDeviation;
                }
            }
        }

        return weight;
    }

    private static Number averageWeight(List<Number> weights) {

        final Number ZERO = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0");
        Number counter = ZERO;
        Number sum = ZERO;

        for (Number weight : weights) {

            sum = sum.add(weight);
            counter = counter.inc();
        }

        Number average = sum.divide(counter);

        return average;
    }

}
