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
import java.util.function.Consumer;
import java.util.stream.Stream;

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


/**
 * An evaluator for a neural network.
 *
 * @author Kristian Kutin
 */
public class NetworkEvaluator implements Evaluator<Network> {

    private static Number MIN_INDIVIDUAL = null;

    private static Number MAX_INDIVIDUAL = null;

    private static Number MIN = null;

    private static Number MAX = null;

    public static void showScoreRanges() {

        System.out.println("individual scores: " + MIN_INDIVIDUAL + " to " + MAX_INDIVIDUAL);
        System.out.println("scores: " + MIN + " to " + MAX);
    }

    private static void updateIndividual(Number score) {

        if (MIN_INDIVIDUAL == null) {

            MIN_INDIVIDUAL = score;
            
        } else {
            
            MIN_INDIVIDUAL = MIN_INDIVIDUAL.min(score);
        }
        
        if (MAX_INDIVIDUAL == null) {

            MAX_INDIVIDUAL = score; 
            
        } else {

            MAX_INDIVIDUAL = MAX_INDIVIDUAL.max(score);
        }
    }

    private static void update(Number score) {
        
        if (MIN == null) {
            
            MIN = score;

        } else {

            MIN = MIN.min(score);
        }
        
        if (MAX == null) {
            
            MAX = score;

        } else {

            MAX = MAX.max(score);
        }
        
    }

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

        final Number ZERO = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0");
        final Number ONE = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1");

        Number score = ZERO;

        /*trainingData.forEach(new Consumer<DataEntry>(){

            @Override
            public void accept(DataEntry t) {
                // TODO Implement this method
            }

        });*/

        for (DataEntry entry : this.trainingData) {

            Number individualScore = test(network, entry);
            
            score = score.add(individualScore);
        }

        // a high deviation will result in a lower score and vice versa
        Number reciprocal = ONE.divide(FunctionIdentifiers.RUSSIAN_DIVISION_FUNCTION, score);
        reciprocal = reciprocal.shiftRight();
        reciprocal = reciprocal.shiftRight();
        reciprocal = reciprocal.shiftRight();
        //update(reciprocal);

        int result = reciprocal.toInteger();

        return result;
    }

    private static Number test(Network network, DataEntry entry) {

        Number input = entry.input;
        Number expectedOutput = entry.expectedOutput;
        Number actualOutput = network.send(input);

        Number delta = expectedOutput.subtract(actualOutput);
        delta = delta.absoluteValue();

        Number absoluteExpectedValue = expectedOutput.absoluteValue();

        Number individualScore = delta.divide(FunctionIdentifiers.RUSSIAN_DIVISION_FUNCTION, absoluteExpectedValue);
        individualScore.shiftRight();
        individualScore.shiftRight();

        return individualScore;
    }

}
