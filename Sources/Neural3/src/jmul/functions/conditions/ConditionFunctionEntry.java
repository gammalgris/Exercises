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


import jmul.functions.Function;

import jmul.math.numbers.Number;


/**
 * An implementation of a data srtucture that contains a condition and a function.
 *
 * @author Kristian Kutin
 */
public final class ConditionFunctionEntry {

    /**
     * A condition.
     */
    public final Condition<Number> condition;

    /**
     * A function.
     */
    public final Function function;

    /**
     * Creates a new instance according to the specified parameters.
     *
     * @param condition
     *        a condition
     * @param function
     *        a function
     */
    public ConditionFunctionEntry(Condition<Number> condition, Function function) {

        super();

        if (condition == null) {

            throw new IllegalArgumentException("No condition (null) was specified!");
        }

        if (function == null) {

            throw new IllegalArgumentException("No function (null) was specified!");
        }

        this.condition = condition;
        this.function = function;
    }

    /**
     * Returns a string representation for this entry.
     *
     * @return a string representation
     */
    @Override
    public String toString() {

        return String.format("%s -> %s", condition, function);
    }

}
