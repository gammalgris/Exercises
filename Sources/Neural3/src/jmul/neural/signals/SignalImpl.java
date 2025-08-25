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


/**
 * An implementation of a signal.
 *
 * @author Kristian Kutin
 */
public class SignalImpl implements Signal {

    private final SignalSource source;

    /**
     * A signal value.
     */
    private final Number value;

    /**
     * Creates a new signal.
     *
     * @param source
     *        a signal source
     * @param number
     *        a signal value
     */
    public SignalImpl(SignalSource source, Number number) {

        super();

        if (source == null) {

            throw new IllegalArgumentException("No source (null) was specified!");
        }

        if (number == null) {

            throw new IllegalArgumentException("No value (null) was specified!");
        }

        this.source = source;
        this.value = number;
    }

    /**
     * The amplified signal value from a synapse.
     *
     * @return a signal value
     */
    @Override
    public Number value() {

        return value;
    }

    /**
     * Returns a string representation for this signal.
     *
     * @return a string representation
     */
    @Override
    public String toString() {

        String summary = String.format("source @%s value %s", source, value);
        return String.valueOf(summary);
    }

    @Override
    public SignalSource source() {

        return source;
    }
}
