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


@ManualTest
public class Training2Test {

    private static final int INITIAL_POPULATION_SIZE;

    private static final int MAX_BEST_INDIVIDUALS;

    private static final int NUMBER_OF_CHANGES;

    private static TrainingData TRAINING_DATA;

    private static final int[] NETWORK_CONFIGURATION;

    /*
     * The static initializer.
     */
    static {

        INITIAL_POPULATION_SIZE = 8;
        MAX_BEST_INDIVIDUALS = INITIAL_POPULATION_SIZE / 2;

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

        Evaluator<Network> evaluator = newEvaluator(TRAINING_DATA);
        Mutator<Network> mutator = newMutator(NUMBER_OF_CHANGES);
        Population<Network> currentGeneration =
            newPopulation(evaluator, mutator, INITIAL_POPULATION_SIZE, NETWORK_CONFIGURATION);

        System.out.println("initialization done.");


        System.out.println("\tstart training");

        int trainingRun = 0;
        int score = 0;
        Network fittestIndividual = null;

        while (true) {

            trainingRun++;
            System.out.println("\ttraining run #" + trainingRun);

            for (int a = 0; a < 30; a++) {

                System.out.println("\t\tmutating #" + (a + 1));
                currentGeneration = currentGeneration.mutate();
            }

            Population<Network> fittestIndividuals = currentGeneration.getFittestIndividuals(MAX_BEST_INDIVIDUALS);
            int lastIndex = fittestIndividuals.getSize() - 1;
            fittestIndividual = fittestIndividuals.getIndividual(lastIndex);

            score = evaluator.calculateScore(fittestIndividual);
            System.out.println("\t\tbest score: " + score);
            if (score > 134) {

                break;
            }


            int newPopulationSize = currentGeneration.getSize() + 1;
            currentGeneration = currentGeneration.grow(newPopulationSize);
            
            //NetworkEvaluator.showScoreRanges();
        }

        System.out.println("\ttraining done.");


        // a detailed summary for the fittest individuals
        System.out.println("score: " + score);

        System.out.println("detailed summaries for fittest individuals");

        for (DataEntry entry : TRAINING_DATA) {

            Number input = entry.input;
            Number expectedOutput = entry.expectedOutput;
            Number actualOutput = fittestIndividual.send(input);

            DataEntryWithResult result = new DataEntryWithResult(input, expectedOutput, actualOutput);
            String message =
                String.format("\tinput=%s; expected output=%s; actual output=%s; deviation=%s", result.input,
                              result.expectedOutput, result.actualOutput, result.deviation());
            System.out.println(message);
        }

        System.out.println("done.");
    }

    private static Evaluator<Network> newEvaluator(TrainingData trainingData) {

        return new NetworkEvaluator(trainingData);
    }

    private static Mutator<Network> newMutator(int numberOfChanges) {

        return new NetworkMutator(numberOfChanges);
    }

    private static Network newNetwork(int[] networkConfiguration) {

        return new FlatNetworkImpl(networkConfiguration);
    }

    private static Population<Network> newPopulation(Evaluator<Network> evaluator, Mutator<Network> mutator,
                                                     int populationSize, int[] networkConfiguration) {

        List<Network> networks = new ArrayList<>();
        for (int a = 0; a < populationSize; a++) {

            Network network = newNetwork(networkConfiguration);
            networks.add(network);
        }

        return new PopulationImpl<>(evaluator, mutator, networks);
    }

}
