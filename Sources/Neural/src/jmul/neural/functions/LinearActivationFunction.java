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
 * An activation function that is a linear function.
 *
 * @author Kristian Kutin
 */
public class LinearActivationFunction implements ActivationFunction {

    /**
     * A coefficient in the function.
     */
    private final Number coefficient;

    /**
     * A constant coefficient in the function.
     */
    private final Number constantCoefficient;

    /**
     * The default constructor.
     */
    public LinearActivationFunction() {

        this("1", "0");
    }

    /**
     * Creates a new function according to the specified parameter.
     *
     * @param coefficientString
     *        a number string for the coefficient
     */
    public LinearActivationFunction(String coefficientString) {

        this(coefficientString, "0");
    }

    /**
     * Creates a new function according to the specified parameters.
     *
     * @param coefficientString
     *        a number string for the coefficient
     * @param constantCoefficientString
     *        a number string for the constant coefficient
     */
    public LinearActivationFunction(String coefficientString, String constantCoefficientString) {

        super();

        if (coefficientString == null) {

            throw new IllegalArgumentException("No number string (null) for the coefficient was specified!");
        }

        if (coefficientString.trim().isEmpty()) {

            throw new IllegalArgumentException("No number string (empty string) for the coefficient was specified!");
        }

        if (constantCoefficientString == null) {

            throw new IllegalArgumentException("No number string (null) for the constant coefficient was specified!");
        }

        if (constantCoefficientString.trim().isEmpty()) {

            throw new IllegalArgumentException("No number string (empty string) for the constant coefficient was specified!");
        }

        this.coefficient = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, coefficientString);
        this.constantCoefficient = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, constantCoefficientString);
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

        Number result = coefficient.multiply(number);
        result = result.add(constantCoefficient);

        return result;
    }

    /**
     * Returns a summary for this activation function.
     *
     * @return a summary
     */
    @Override
    public String toString() {

        return String.format("f(x) = %s * x + %s", coefficient, constantCoefficient);
    }

}
