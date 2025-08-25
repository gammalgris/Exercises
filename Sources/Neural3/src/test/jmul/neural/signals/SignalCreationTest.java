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

package test.jmul.neural.signals;


import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.signals.Signal;
import jmul.neural.signals.SignalImpl;
import jmul.neural.signals.SignalSource;

import jmul.test.classification.UnitTest;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * This test suite test creating signals.
 *
 * @author Kristian Kutin
 */
@UnitTest
public class SignalCreationTest {

    /**
     * Creates a signal and checks it's properties.
     */
    @Test
    public void testCreateSignal() {

        SignalSource SOURCE = new SignalSource() {
        };
        Number number = createNumber(10, "1");

        Signal signal = new SignalImpl(SOURCE, number);

        assertEquals(SOURCE, signal.source());
        assertEquals(number, signal.value());
    }

    /**
     * Creates a signal and checks it's properties.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateSignalWithNullValue() {

        SignalSource SOURCE = new SignalSource() {
        };
        Number number = null;
        new SignalImpl(SOURCE, number);
    }

    /**
     * Creates a signal and checks it's properties.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateSignalWithNullSource() {

        SignalSource SOURCE = null;
        Number number = createNumber(10, "1");

        new SignalImpl(SOURCE, number);
    }

}
