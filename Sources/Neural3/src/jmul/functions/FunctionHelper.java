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


import jmul.functions.conditions.ConditionFunctionEntry;
import jmul.functions.conditions.GreaterOrEqualCondition;
import jmul.functions.conditions.LesserThanCondition;

import jmul.math.Math;
import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.GlobalSettings;


/**
 * A utility class for creating functions.
 *
 * @author Kristian Kutin
 */
public final class FunctionHelper {

    /**
     * The default constructor.
     */
    private FunctionHelper() {

        throw new UnsupportedOperationException();
    }

    /**
     * Creates a new polynomial function.
     *
     * @return a function
     */
    public static Function createPolynomialFunction() {

        return new PolynomialFunctionImpl(new Number[] { });
    }

    /**
     * Creates a new polynomial function according to the specified parameters.
     *
     * @param base
     *        a number base
     * @param coefficientStrings
     *        all coefficient number strings (in ascending order c<sub>0</sub>, c<sub>1</sub>, c<sub>2</sub>, ..., c<sub>n</sub>)
     *
     * @return a function
     */
    public static Function createPolynomialFunction(int base, String... coefficientStrings) {

        if (coefficientStrings == null) {

            throw new IllegalArgumentException("No coefficients (null) were specified!");
        }

        int length = coefficientStrings.length;
        Number[] coefficients = new Number[length];

        for (int index = 0; index < length; index++) {

            Number n = createNumber(base, coefficientStrings[index]);
            coefficients[index] = n;
        }

        return new PolynomialFunctionImpl(coefficients);
    }

    /**
     * Creates a random polynomial function with random coefficients.
     *
     * @param base
     *        a number base
     *
     * @return a function
     */
    public static Function createRandomPolynomialFunctionExcludingStaticFunctions(int base) {

        int maxNumbers = ((int) (java.lang
                                     .Math
                                     .random() * 2L)) + 2;

        Number[] coefficients = new Number[maxNumbers];
        for (int index = 0; index < maxNumbers; index++) {

            Number n = Math.random(base);
            coefficients[index] = n;
        }

        return new PolynomialFunctionImpl(coefficients);
    }

    /**
     * Creates a random polynomial function with random coefficients.
     *
     * @param base
     *        a number base
     *
     * @return a function
     */
    public static Function createRandomPolynomialFunctionIncludingStaticFunctions(int base) {

        int maxNumbers = ((int) (java.lang
                                     .Math
                                     .random() * 3L)) + 1;

        Number[] coefficients = new Number[maxNumbers];
        for (int index = 0; index < maxNumbers; index++) {

            Number n = Math.random(base);
            coefficients[index] = n;
        }

        return new PolynomialFunctionImpl(coefficients);
    }

    /**
     * Creates a threshold function according to the specified parameters.
     *
     * @param entries
     *        condition function entries
     *
     * @return a threshold function
     */
    public static Function createThresholdFunction(ConditionFunctionEntry... entries) {

        if (entries == null) {

            throw new IllegalArgumentException("No entries (null) were specified!");
        }

        return new ThresholdFunctionImpl(entries);
    }

    /**
     * Creates a threshold function with two functions.
     *
     * @param base
     *        a number base
     *
     * @return a function
     */
    public static Function createRandomThresholdFunction(int base) {

        Number threshold = Math.random(base);

        ConditionFunctionEntry[] entries = {
            new ConditionFunctionEntry(new LesserThanCondition(threshold),
                                       createRandomPolynomialFunctionIncludingStaticFunctions(base)),
            new ConditionFunctionEntry(new GreaterOrEqualCondition(threshold),
                                       createRandomPolynomialFunctionIncludingStaticFunctions(base))
        };

        return new ThresholdFunctionImpl(entries);
    }


    /**
     * Returns a random activation function.
     *
     * @return an activation function
     */
    public static Function randomActivationFunction() {

        int randomCase = (int) (java.lang
                                    .Math
                                    .random() * 2L);

        switch (randomCase) {

        case 0:
            return createRandomPolynomialFunctionExcludingStaticFunctions(GlobalSettings.DEFAULT_NUMBER_BASE);
        case 1:
            return createRandomThresholdFunction(GlobalSettings.DEFAULT_NUMBER_BASE);
        default:
            throw new RuntimeException("Ooops!");
        }
    }

}
