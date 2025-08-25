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

package jmul.neural.signals;


import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;


/**
 * A helper class for signals.
 *
 * @author Kristian Kutin
 */
public final class SignalHelper {

    /**
     * The default cosntructor.
     */
    private SignalHelper() {

        throw new UnsupportedOperationException();
    }

    /**
     * Creates a new signal according to the specified parameters.
     *
     * @param base
     *        a number base
     * @param numberString
     *        the string representation of a number
     *
     * @return a new signal
     */
    public static Signal createSignal(int base, String numberString) {

        Number number = createNumber(base, numberString);
        Signal signal = new SignalImpl(number);

        return signal;
    }

    /**
     * Creates a new signal accordign to the specified parameter.
     *
     * @param number
     *        a number
     *
     * @return a new signal
     */
    public static Signal createSignal(Number number) {

        Signal signal = new SignalImpl(number);

        return signal;
    }

}
