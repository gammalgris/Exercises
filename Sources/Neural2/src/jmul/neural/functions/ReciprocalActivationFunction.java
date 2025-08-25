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


import jmul.math.functions.repository.FunctionIdentifiers;
import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.math.numbers.exceptions.UndefinedOperationException;

import jmul.neural.GlobalSettings;


public class ReciprocalActivationFunction implements ActivationFunction {

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
    public ReciprocalActivationFunction() {

        this("1", "0");
    }

    /**
     * Creates a new function according to the specified parameter.
     *
     * @param coefficientString
     *        a number string for the coefficient
     */
    public ReciprocalActivationFunction(String coefficientString) {

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
    public ReciprocalActivationFunction(String coefficientString, String constantCoefficientString) {

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

    @Override
    public Number calculate(Number number) {

        if (number == null) {

            throw new IllegalArgumentException("No number (null) was specified!");
        }

        Number term1 = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1");
        
        try {
            term1 = term1.divide(FunctionIdentifiers.RUSSIAN_DIVISION_FUNCTION, number);
            
        } catch (UndefinedOperationException e) {

            System.out.println("DEBUG:: number=" + number);
            throw e;
        }

        term1 = term1.multiply(coefficient);

        Number term2 = constantCoefficient;

        Number result = term1.add(term2);

        return result;
    }

    /**
     * Returns a summary for this activation function.
     *
     * @return a summary
     */
    @Override
    public String toString() {

        return String.format("f(x) = %s * 1 / x + %s", coefficient, constantCoefficient);
    }

}
