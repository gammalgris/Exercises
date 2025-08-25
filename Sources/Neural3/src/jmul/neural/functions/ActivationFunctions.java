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


import jmul.functions.Function;
import jmul.functions.PolynomialFunctionImpl;
import jmul.functions.ThresholdFunctionImpl;

import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.GlobalSettings;


/**
 * This enumeration contains various activation function.
 *
 * @author Kristian Kutin
 */
public enum ActivationFunctions implements Function {


    ONE(new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"))),

    ZERO(new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"))),

    LINEAR1(new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1.5"),
                                       createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "-0.5"))),

    LINEAR2(new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "-1.5"),
                                       createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0.5"))),

    LINEAR3(new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                       createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "-1"))),

    QUADRATIC1(new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                          createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "-1"),
                                          createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "-1"))),

    QUADRATIC2(new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                          createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "-0.5"),
                                          createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "-0.5"))),

    QUADRATIC3(new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                          createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "-1"),
                                          createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "-1"),
                                          createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"))),

    THRESHOLD1(new ThresholdFunctionImpl(new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE,
                                                                                 "0")),
                                         createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                         new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE,
                                                                                 "2")))),

    THRESHOLD2(new ThresholdFunctionImpl(new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE,
                                                                                 "0")),
                                         createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                         new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE,
                                                                                 "2")))),

    THRESHOLD3(new ThresholdFunctionImpl(new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE,
                                                                                 "0")),
                                         createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                         new PolynomialFunctionImpl(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE,
                                                                                 "2"))));


    /**
     * An activation function.
     */
    private final Function function;

    /**
     * Creates a new enumeration element according to the specified parameter.
     *
     * @param function
     *        a function which is to be used as activation function
     */
    private ActivationFunctions(Function function) {

        this.function = function;
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

        return function.calculate(number);
    }

    /**
     * Returns the derivative function for this function.
     *
     * @return a derivative function
     */
    @Override
    public Function derivativeFunction() {

        return function.derivativeFunction();
    }

    /**
     * Returns a random activation function.
     *
     * @return an activation function
     */
    public static Function randomActivationFunction() {

        Function[] functions = values();
        int min = 0;
        int max = functions.length;

        int randomIndex = (int) (Math.random() * ((double) max - (double) min) + (double) min);

        Function function = functions[randomIndex];

        return function;
    }

}
