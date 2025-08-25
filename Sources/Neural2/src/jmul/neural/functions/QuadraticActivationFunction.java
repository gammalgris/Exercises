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


public class QuadraticActivationFunction implements ActivationFunction {

    /**
     * A coefficient in the function.
     */
    private final Number coefficient1;

    /**
     * A coefficient in the function.
     */
    private final Number coefficient2;

    /**
     * A constant coefficient in the function.
     */
    private final Number constantCoefficient;

    public QuadraticActivationFunction() {

        this("1", "0", "0");


    }

    public QuadraticActivationFunction(String coefficient1String) {

        this(coefficient1String, "0", "0");
    }

    public QuadraticActivationFunction(String coefficient1String, String coefficient2String) {

        this(coefficient1String, coefficient2String, "0");
    }

    public QuadraticActivationFunction(String coefficient1String, String coefficient2String,
                                       String constantCoefficientString) {

        super();

        if (coefficient1String == null) {

            throw new IllegalArgumentException("No number string (null) for the coefficient 1 was specified!");
        }

        if (coefficient1String.trim().isEmpty()) {

            throw new IllegalArgumentException("No number string (empty string) for the coefficient 1 was specified!");
        }

        if (coefficient2String == null) {

            throw new IllegalArgumentException("No number string (null) for the coefficient 2 was specified!");
        }

        if (coefficient2String.trim().isEmpty()) {

            throw new IllegalArgumentException("No number string (empty string) for the coefficient 2 was specified!");
        }

        if (constantCoefficientString == null) {

            throw new IllegalArgumentException("No number string (null) for the constant coefficient was specified!");
        }

        if (constantCoefficientString.trim().isEmpty()) {

            throw new IllegalArgumentException("No number string (empty string) for the constant coefficient was specified!");
        }

        this.coefficient1 = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, coefficient1String);
        this.coefficient2 = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, coefficient2String);
        this.constantCoefficient = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, constantCoefficientString);
    }

    @Override
    public Number calculate(Number number) {

        if (number == null) {

            throw new IllegalArgumentException("No number (null) was specified!");
        }

        Number term1 = number;
        term1 = term1.multiply(number);
        term1 = term1.multiply(coefficient1);

        Number term2 = number;
        term2 = term2.multiply(coefficient2);

        Number term3 = constantCoefficient;

        Number result = term1.add(term2);
        result = result.add(term3);

        return result;
    }

    /**
     * Returns a summary for this activation function.
     *
     * @return a summary
     */
    @Override
    public String toString() {

        return String.format("f(x) = %s * x^2 + %s * x + %s", coefficient1, coefficient2, constantCoefficient);
    }

}
