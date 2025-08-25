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

import jmul.neural.signals.Signal;
import static jmul.neural.signals.SignalHelper.createSignal;
import jmul.neural.neurons.Synapse;
import jmul.neural.neurons.SynapseImpl;

import jmul.test.classification.UnitTest;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


/**
 * THis test suite tests a signal transfer via synapse.
 */
@UnitTest
public class SignalTransferTest {

    /**
     * A snyapse for signal transfer.
     */
    private Synapse synapse;

    /**
     * Prepares the test setup.
     */
    @Before
    public void setUp() {

        Number number = createNumber(10, "1");

        SynapseImpl tmpSynapse = new SynapseImpl();
        tmpSynapse.setWeight(number);

        synapse = tmpSynapse;
    }

    /**
     * Cleans up the test setup.
     */
    @After
    public void tearDown() {

        synapse = null;
    }

    /**
     * Tests the signal transfer.
     */
    @Test
    public void testSignalTransfer() {

        Signal signal = createSignal(10, "5");

        assertFalse(synapse.hasStoredSignals());

        synapse.transmitSignal(signal);
        assertTrue(synapse.hasStoredSignals());

        Signal receivedSignal = synapse.receiveSignal();
        assertNotEquals(signal, receivedSignal);
        assertEquals(signal.value(), receivedSignal.value());

        assertFalse(synapse.hasStoredSignals());
    }

    /**
     * Tests the signal transfer with an empty signal queue.
     */
    @Test
    public void testEmptySignalQueue() {

        assertFalse(synapse.hasStoredSignals());
        Signal receivedSignal = synapse.receiveSignal();
        assertNull(receivedSignal);
    }

}
