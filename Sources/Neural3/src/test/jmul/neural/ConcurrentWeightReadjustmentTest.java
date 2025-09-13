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

package test.jmul.neural;


import jmul.data.DataEntry;
import jmul.data.TrainingData;

import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.metainfo.annotations.Modified;

import jmul.neural.GlobalSettings;
import jmul.neural.neurons.FlatNetworkImpl;
import jmul.neural.neurons.Network;
import jmul.neural.neurons.NetworkHelper;

import jmul.neural.training.ConcurrentSynapseTrainer;
import jmul.neural.training.NetworkTrainer;
import jmul.neural.training.NeuronTrainer;
import jmul.neural.training.SynapseTrainer;

import jmul.test.classification.ManualTest;


@ManualTest
public class ConcurrentWeightReadjustmentTest {

    private static final int[] NETWORK_CONFIGURATION;

    private static final TrainingData TRAINING_DATA;

    static {

        NETWORK_CONFIGURATION = new int[] { 1, 4, 4, 1 };

        TRAINING_DATA =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0.15"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0.0225")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0.25"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0.0625")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0.5"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0.25")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0.75"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0.5625")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1.25"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1.5625")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1.5"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2.25")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1.75"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3.0625")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "4")));
    }

    public static void main(String... args) {

        Network network = new FlatNetworkImpl(NETWORK_CONFIGURATION);

        trainNetwork(network, TRAINING_DATA);

        System.out.println("done.");
    }

    /**
     * Trains the specified network (i.e. rearranges the weights).
     *
     * @param network
     *        a network
     * @param trainingData
     *        a set of training data
     *
     * @return the mean squared error
     */
    public static Number trainNetwork(@Modified Network network, TrainingData trainingData) {

        NetworkTrainer synapseTrainer = new ConcurrentSynapseTrainer();
        NetworkTrainer neuronTrainer = new NeuronTrainer();

        System.out.println(network);
        System.out.println("--------------------------------------------------------------------------------");

        synapseTrainer.trainNetwork(network, trainingData);
        neuronTrainer.trainNetwork(network, trainingData);

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(network);
        System.out.println("--------------------------------------------------------------------------------");

        for (DataEntry entry : trainingData) {

            Number actualOutput = network.send(entry.input);

            String summary = String.format("%s -> %s expected %s", entry.input, actualOutput, entry.expectedOutput);
            System.out.println(summary);
        }

        //TODO repeat until error is small

        return null;
    }

}
