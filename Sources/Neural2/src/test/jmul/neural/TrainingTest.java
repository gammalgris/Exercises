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


import java.util.ArrayList;
import java.util.List;

import jmul.data.DataEntry;
import jmul.data.DataEntryWithResult;
import jmul.data.TrainingData;

import jmul.genetic.Evaluator;
import jmul.genetic.Mutator;
import jmul.genetic.Population;
import jmul.genetic.PopulationImpl;

import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.GlobalSettings;
import jmul.neural.genetic.NetworkEvaluator;
import jmul.neural.genetic.NetworkMutator;
import jmul.neural.neurons.FlatNetworkImpl;
import jmul.neural.neurons.Network;

import jmul.test.classification.ManualTest;


/**
 * TODO Test is brittle and may block
 */
@ManualTest
public class TrainingTest {

    private static final int MAX_POPULATION_SIZE;

    private static final int MAX_BEST_INDIVIDUALS;

    private static final int MAX_EPOCHS;
    
    private static final int MAX_GENERATIONS_PER_EPOCH;

    private static final int NUMBER_OF_CHANGES;

    private static TrainingData TRAINING_DATA;

    private static final int[] NETWORK_CONFIGURATION;

    /*
     * The static initializer.
     */
    static {

        MAX_POPULATION_SIZE = 8;
        MAX_BEST_INDIVIDUALS = 2;
        MAX_EPOCHS = 40;
        MAX_GENERATIONS_PER_EPOCH = 10;

        NUMBER_OF_CHANGES = 5;

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

        NETWORK_CONFIGURATION = new int[] { 1, 4, 4, 1 };
    }

    public static void main(String... args) {

        // initialization

        System.out.println("start initialization");

        Evaluator<Network> evaluator = new NetworkEvaluator(TRAINING_DATA);
        Mutator<Network> mutator = new NetworkMutator(NUMBER_OF_CHANGES);

        List<Network> networks = new ArrayList<>();
        for (int a = 0; a < MAX_POPULATION_SIZE; a++) {

            Network network = new FlatNetworkImpl(NETWORK_CONFIGURATION);
            networks.add(network);
        }

        Population<Network> currentGeneration = new PopulationImpl<>(evaluator, mutator, networks);

        System.out.println("initialization done.");

        Population<Network> fittestIndividuals = null;

        for (int b = 0; b < MAX_EPOCHS; b++) {

            System.out.println("epoch #" + (b + 1));

            // generational improvement - training

            System.out.println("\tstart training");

            Population<Network> previousGeneration;
            for (int a = 0; a < MAX_GENERATIONS_PER_EPOCH; a++) {

                System.out.println("\ttrain generation #" + (a + 1));

                previousGeneration = currentGeneration;
                fittestIndividuals = currentGeneration.getFittestIndividuals(MAX_BEST_INDIVIDUALS);

                currentGeneration = fittestIndividuals.mutate();
                currentGeneration = currentGeneration.grow(MAX_POPULATION_SIZE + 1);
            }

            System.out.println("\ttraining done.");


            // show statistics/ deviations for last generation

            System.out.println("\tmeasure performance");

            int max = currentGeneration.getSize();

            for (int index = 0; index < max; index++) {

                System.out.println("\tmeasure performance individual #" + (index + 1));

                Network network = currentGeneration.getIndividual(index);
                int score = evaluator.calculateScore(network);

                String summary = String.format("\tindividual #%d -> score %d", index, score);
                System.out.println(summary);
            }

            System.out.println("\tperformance measure done.");
        }

        // a detailed summary for the fittest individuals
        System.out.println("detailed summaries for fittest individuals");

        int max = fittestIndividuals.getSize();

        for (int index = 0; index < max; index++) {

            Network network = fittestIndividuals.getIndividual(index);
            System.out.println("individual #" + (index + 1));
            for (DataEntry entry : TRAINING_DATA) {

                Number input = entry.input;
                Number expectedOutput = entry.expectedOutput;
                Number actualOutput = network.send(input);

                DataEntryWithResult result = new DataEntryWithResult(input, expectedOutput, actualOutput);
                String message =
                    String.format("\tinput=%s; expected output=%s; actual output=%s; deviation=%s", result.input,
                                  result.expectedOutput, result.actualOutput, result.deviation());
                System.out.println(message);
            }
        }

        System.out.println("done.");
    }

}
