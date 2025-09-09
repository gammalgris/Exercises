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

package jmul.functions.conditions;


import jmul.math.numbers.Number;


/**
 * An implementation of a condition that checks that a number is greater or equal a given threshold.
 *
 * @author Kristian Kutin
 */
public class GreaterOrEqualCondition extends ThresholdConditionBase {

    /**
     * Creates a new condition according to the specified parmaeter.
     *
     * @param threshold
     *        a threshold
     */
    public GreaterOrEqualCondition(Number threshold) {

        super(threshold);
    }

    /**
     * Checks if the specified parameter is lesser than the threshold.
     *
     * @param t
     *        a parameter
     *
     * @return <code>true</code> if the specified parameter is lesser than the threshold, else <code>false</code>
     */
    @Override
    public boolean meetsCondition(Number t) {

        return !threshold.isLesser(t);
    }

    /**
     * Returns a string representation for this condition.
     *
     * @return a string representation
     */
    @Override
    public String toString() {

        return String.format("x >= %s", threshold);
    }

}
