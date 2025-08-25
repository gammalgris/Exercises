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

package jmul.functions;


import jmul.math.numbers.Number;


/**
 * Implements a function with a threshold (i.e. function1 &lt; threshold &lt;= function2).
 *
 * @author Kristian Kutin
 * 
 * TODO try making it more generic
 */
public class ThresholdFunctionImpl implements Function {

    /**
     * A threshold.
     */
    private final Number threshold;

    /**
     * A function.
     */
    private final Function function1;

    /**
     * A function.
     */
    private final Function function2;

    /**
     * Creates a new threshold function.
     *
     * @param function1
     *        a function
     * @param threshold
     *        a threshold
     * @param function2
     *        a function
     */
    public ThresholdFunctionImpl(Function function1, Number threshold, Function function2) {

        super();

        if (function1 == null) {

            throw new IllegalArgumentException("No function #1 (null) was specified!");
        }

        if (threshold == null) {

            throw new IllegalArgumentException("No threshold (null) was specified!");
        }

        if (function2 == null) {

            throw new IllegalArgumentException("No function #2 (null) was specified!");
        }

        this.function1 = function1;
        this.threshold = threshold;
        this.function2 = function2;
    }

    /**
     * Calculates the output value.
     *
     * @param number
     *        a value
     *
     * @return an output value
     */
    @Override
    public Number calculate(Number number) {

        if (number == null) {

            throw new IllegalArgumentException("No number(null) was specified!");
        }

        if (number.isLesser(threshold)) {

            return function1.calculate(number);

        } else {

            return function2.calculate(number);
        }
    }

    /**
     * Returns the derivative function for this function.
     *
     * @return a derivative function
     */
    @Override
    public Function derivativeFunction() {

        return new ThresholdFunctionImpl(function1.derivativeFunction(), threshold, function2.derivativeFunction());
    }

    /**
     * Returns a string representation for this function.
     *
     * @return a string representation
     */
    @Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();

        buffer.append("f(x) = ");
        buffer.append("x < ");
        buffer.append(threshold);
        buffer.append(": ");
        buffer.append(function1);
        buffer.append("; x >=");
        buffer.append(threshold);
        buffer.append(": ");
        buffer.append(function2);

        return buffer.toString();
    }

}
