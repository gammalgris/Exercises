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


/**
 * This enumeration contains various activation function.
 *
 * @author Kristian Kutin
 */
public enum ActivationFunctions implements ActivationFunction {


    ONE(new ConstantActivationFunction("1")),
    ZERO(new ConstantActivationFunction("0")),
    LINEAR1(new LinearActivationFunction("1.5", "-0.5")),
    LINEAR2(new LinearActivationFunction("1.5", "-0.5")),
    LINEAR3(new LinearActivationFunction("1.5", "-0.5")),
    THRESHOLD1(new ThresholdActivationFunction("0", "0", "1")),
    THRESHOLD2(new ThresholdActivationFunction("0.5", "0.25", "1.25")),
    THRESHOLD3(new ThresholdActivationFunction("-0.5", "0.25", "1.25")), ;


    /**
     * An activation function.
     */
    private final ActivationFunction activationFunction;

    /**
     * Creates a new enumeration element according to the specified parameter.
     *
     * @param activationFunction
     */
    private ActivationFunctions(ActivationFunction activationFunction) {

        this.activationFunction = activationFunction;
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

        return activationFunction.calculate(number);
    }

    /**
     * Returns a random activation function.
     *
     * @return an activation function
     */
    public static ActivationFunction randomActivationFunction() {

        ActivationFunction[] functions = values();
        int min = 0;
        int max = functions.length;

        int randomIndex = (int) (Math.random() * ((double) max - (double) min) + (double) min);

        ActivationFunction function = functions[randomIndex];

        return function;
    }

}
