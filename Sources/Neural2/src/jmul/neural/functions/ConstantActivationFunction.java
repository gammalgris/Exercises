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

package jmul.neural.functions;


import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.GlobalSettings;


/**
 * An activation function that returns a constant value.
 *
 * @author Kristian Kutin
 */
public class ConstantActivationFunction implements ActivationFunction {

    /**
     * A number representing the constant value.
     */
    private final Number constantNumber;

    /**
     * Creates an activation function accordign to the specified parameter.
     *
     * @param constantNumberString
     *        a constant number string
     */
    public ConstantActivationFunction(String constantNumberString) {

        super();

        if (constantNumberString == null) {

            throw new IllegalArgumentException("No constant number string (null) was specified!");
        }

        if (constantNumberString.trim().isEmpty()) {

            throw new IllegalArgumentException("No constant number string (empty string) was specified!");
        }

        this.constantNumber = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, constantNumberString);
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

            throw new IllegalArgumentException("No number (null) was specified!");
        }

        return constantNumber;
    }

    /**
     * Returns a summary for this activation function.
     *
     * @return a summary
     */
    @Override
    public String toString() {

        return String.format("f(x) = %s", constantNumber);
    }

}
