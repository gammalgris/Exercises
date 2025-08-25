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

package test.jmul.neural.neurons;


import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.GlobalSettings;
import jmul.neural.neurons.FlatNetworkImpl;
import jmul.neural.neurons.Network;
import jmul.neural.neurons.Synapse;

import jmul.test.classification.UnitTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * This test suite tests transmitting signals in a flat network layout.
 *
 * @author Kristian Kutin
 */
@UnitTest
public class MediumFlatNetworkTest {

    /**
     * An input synapse.
     */
    private Synapse inputSynapse;

    /**
     * An output synapse.
     */
    private Synapse outputSynapse;

    /**
     * A network of neurons.
     */
    private Network network;

    /**
     * Sets up the test setup.
     */
    @Before
    public void setUp() {

        network = new FlatNetworkImpl(1, 6, 6, 1);
        inputSynapse = network.inputSynapse();
        outputSynapse = network.outputSynapse();
    }

    /**
     * Cleans up the test setup.
     */
    @After
    public void tearDown() {

        network = null;
        inputSynapse = null;
        outputSynapse = null;
    }

    @Test
    public void testTransmitSignal() {

        Number input = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1");
        System.out.println("input : " + input);

        long startTime = System.currentTimeMillis();

        Number output = network.send(input);

        long stopTime = System.currentTimeMillis();

        System.out.println("output : " + output);

        long duration = stopTime - startTime;
        System.out.println("duration = " + duration + " ms");
    }

}
