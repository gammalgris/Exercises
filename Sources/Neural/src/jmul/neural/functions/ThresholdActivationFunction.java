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
 * An activation function with a hard threshold.
 *
 * @author Kristian Kutin
 */
public class ThresholdActivationFunction implements ActivationFunction {

    /**
     * A threshold.
     */
    private final Number threshold;

    /**
     * A value when the threshold is not surpassed.
     */
    private final Number value1;

    /**
     * A value when the threshold is surpassed.
     */
    private final Number value2;

    /**
     * Creates a new function according to the specified parameters.
     *
     * @param thresholdString
     *        a number string for a threshold
     * @param value1String
     *        a number string for not suprassing the threshold
     * @param value2String
     *        a number string for surpassign the threshold
     */
    public ThresholdActivationFunction(String thresholdString, String value1String, String value2String) {

        super();

        if (thresholdString == null) {

            throw new IllegalArgumentException("No number string (null) for the threshold was specified!");
        }

        if (thresholdString.trim().isEmpty()) {

            throw new IllegalArgumentException("No number string (empty string) for the threshold was specified!");
        }

        if (value1String == null) {

            throw new IllegalArgumentException("No number string (null) for not surpassing the threshold was specified!");
        }

        if (value1String.trim().isEmpty()) {

            throw new IllegalArgumentException("No number string (empty string) for not surpassing the threshold was specified!");
        }

        if (value2String == null) {

            throw new IllegalArgumentException("No number string (null) for surpassing the threshold was specified!");
        }

        if (value2String.trim().isEmpty()) {

            throw new IllegalArgumentException("No number string (empty string) for surpassing the threshold was specified!");
        }

        this.threshold = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, thresholdString);
        this.value1 = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, value1String);
        this.value2 = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, value2String);
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

        if (number.isLesser(threshold)) {

            return value1;

        } else {

            return value2;
        }
    }

    /**
     * Returns a summary for this activation function.
     *
     * @return a summary
     */
    @Override
    public String toString() {

        return String.format("f(x) = %s if x < %s; %s if x >= %s", value1, threshold, value2, threshold);
    }

}
