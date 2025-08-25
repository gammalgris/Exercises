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
import jmul.neural.neurons.Synapse;
import jmul.neural.neurons.SynapseImpl;
import jmul.neural.signals.Signal;
import jmul.neural.signals.SignalImpl;
import jmul.neural.signals.SignalListener;
import jmul.neural.signals.SignalSource;

import jmul.test.classification.UnitTest;

import org.junit.After;
import static org.junit.Assert.assertFalse;
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

    private SignalListener listener;

    /**
     * Prepares the test setup.
     */
    @Before
    public void setUp() {

        listener = new SignalListener() {

            Signal lastSignal = null;

            @Override
            public void sendSignal(Signal signal) {

                lastSignal = signal;
            }

            @Override
            public String toString() {

                if (lastSignal != null) {

                    return lastSignal.value().toString();

                } else {

                    return String.valueOf(lastSignal);
                }
            }
        };


        Number number = createNumber(10, "1");

        SynapseImpl tmpSynapse = new SynapseImpl();
        tmpSynapse.setWeight(number);
        tmpSynapse.addListener(listener);

        synapse = tmpSynapse;
    }

    /**
     * Cleans up the test setup.
     */
    @After
    public void tearDown() {

        listener = null;
        synapse = null;
    }

    /**
     * Tests the signal transfer.
     */
    @Test
    public void testSignalTransfer() {

        final SignalSource TEST_SOURCE = new SignalSource() {
        };
        final Number VALUE = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "5");

        Signal signal = new SignalImpl(TEST_SOURCE, VALUE);

        String listenerString = listener.toString();
        String valueString = VALUE.toString();
        assertFalse("before", listenerString.equals(valueString));

        synapse.sendSignal(signal);

        listenerString = listener.toString();
        valueString = VALUE.toString();
        assertTrue("after", listenerString.equals(valueString));
    }

}
