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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;
import static jmul.math.numbers.NumberHelper.parseInteger;

import jmul.neural.GlobalSettings;


/**
 * An implementation of a function f(x) = c<sub>n</sub> * x<sup>n</sup> + c<sub>n-1</sub> * x<sup>n-1</sup> + ... + c<sub>1</sub> * x + c<sub>0</sub>.
 *
 * @author Kristian Kutin
 */
public class PolynomialFunctionImpl implements Function {

    /**
     * All coefficients of the function. The index position determines the position within the formula (see class
     * description; in ascending order c<sub>0</sub>, c<sub>1</sub>, c<sub>2</sub>, ..., c<sub>n</sub>).
     *
     * @author Kristian Kutin
     */
    private final List<Number> coefficients;

    /**
     * Creates a new instance according to the specified parameters.
     *
     * @param coefficients
     *        all coefficients. The index position determines the position within the formula (see class description;
     *        in ascending order c<sub>0</sub>, c<sub>1</sub>, c<sub>2</sub>, ..., c<sub>n</sub>).
     */
    PolynomialFunctionImpl(Number... coefficients) {

        super();

        if (coefficients == null) {

            throw new IllegalArgumentException("No coefficients (null) were specified!");
        }

        this.coefficients = Collections.unmodifiableList(Arrays.asList(coefficients));
    }

    /**
     * Creates a new instance according to the specified parameters.
     *
     * @param coefficients
     *        all coefficients. The index position determines the position within the formula (see class description).
     */
    private PolynomialFunctionImpl(List<Number> coefficients) {

        this.coefficients = coefficients;
    }

    /**
     * Evaluate the function.
     *
     * @param number
     *        the input value
     *
     * @return the output value
     */
    @Override
    public Number calculate(Number number) {

        final Number ZERO = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0");
        final Number ONE = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1");

        Number sum = ZERO;

        for (int index = 0; index < coefficients.size(); index++) {

            Number coefficient = coefficients.get(index);

            Number x = ONE;
            for (int exponent = 1; exponent <= index; exponent++) {

                x = x.multiply(number);
            }

            Number term = coefficient.multiply(x);
            sum = sum.add(term);
        }

        return sum;
    }

    /**
     * Returns the derivative function for this function.
     *
     * @return a derivative function
     */
    @Override
    public Function derivativeFunction() {

        List<Number> newCoefficients = new ArrayList<>();

        for (int index = 1; index < coefficients.size(); index++) {

            Number newCoefficient = coefficients.get(index);
            Number newFactor = parseInteger(index);
            newCoefficient = newCoefficient.multiply(newFactor);

            newCoefficients.add(newCoefficient);
        }

        return new PolynomialFunctionImpl(newCoefficients);
    }

    /**
     * Returns a string representation for this function.
     *
     * @return a string representation
     */
    @Override
    public String toString() {

        if (coefficients.size() == 0) {

            return "f(x) = 0";
        }

        StringBuilder buffer = new StringBuilder();

        buffer.append("f(x) = ");

        for (int index = coefficients.size() - 1; index >= 0; index--) {

            Number coefficient = coefficients.get(index);

            buffer.append(coefficient);

            if (index > 0) {

                buffer.append(" * x");

                if (index > 1) {

                    buffer.append("^");
                    buffer.append(index);
                }

                buffer.append(" + ");
            }
        }

        return buffer.toString();
    }

}
