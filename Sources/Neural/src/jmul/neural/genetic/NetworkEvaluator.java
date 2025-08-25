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


import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import jmul.concurrent.threads.ThreadHelper;

import jmul.data.DataEntry;
import jmul.data.DataEntryWithResult;
import jmul.data.TrainingData;
import jmul.data.TrainingDataWithResults;

import jmul.genetic.Evaluator;

import jmul.math.functions.repository.FunctionIdentifiers;
import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.GlobalSettings;
import jmul.neural.neurons.Network;
import jmul.neural.neurons.Synapse;
import jmul.neural.signals.Signal;
import static jmul.neural.signals.SignalHelper.createSignal;


/**
 * An evaluator for a neural network.
 *
 * @author Kristian Kutin
 */
public class NetworkEvaluator implements Evaluator<Network> {

    /**
     * A set of training data.
     */
    private final TrainingData trainingData;

    /**
     * Creates a new instance of the evaluator with the specified parameters.
     *
     * @param trainingData
     *        a set of training data
     */
    public NetworkEvaluator(TrainingData trainingData) {

        super();

        if (trainingData == null) {

            throw new IllegalArgumentException("No training data (null) was specified!");
        }

        this.trainingData = trainingData;
    }

    /**
     * Calculates a score for the specified network.
     *
     * @param network
     *        a neural network
     *
     * @return a score
     */
    @Override
    public int calculateScore(Network network) {

        Queue<DataEntryWithResult> queue = new LinkedList<>();
        Stream<DataEntryWithResult> stream = queue.stream();

        for (DataEntry entry : this.trainingData) {

            Number input = entry.input;
            Number expectedOutput = entry.expectedOutput;

            Synapse inputSynapse = network.inputSynapse();
            Synapse outputSynapse = network.outputSynapse();

            Signal inputSignal = createSignal(input);
            inputSynapse.transmitSignal(inputSignal);

            while (!outputSynapse.hasStoredSignals()) {

                ThreadHelper.sleep(GlobalSettings.DEFAULT_SLEEP_TIME);
            }

            Signal outputSignal = outputSynapse.receiveSignal();
            Number actualOutput = outputSignal.value();

            DataEntryWithResult entryWithResult = new DataEntryWithResult(input, expectedOutput, actualOutput);
            queue.add(entryWithResult);
        }

        TrainingDataWithResults trainingDataWithResults = new TrainingDataWithResults(stream);

        final Number ONE = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1");
        Number averageDeviation = trainingDataWithResults.averageDeviation();

        // a high deviation will result in a lower score and vice versa
        Number reciprocal = ONE.divide(FunctionIdentifiers.RUSSIAN_DIVISION_FUNCTION, averageDeviation);
        reciprocal = averageDeviation.shiftRight();
        reciprocal = averageDeviation.shiftRight();

        int result = reciprocal.toInteger();

        return result;
    }

}
