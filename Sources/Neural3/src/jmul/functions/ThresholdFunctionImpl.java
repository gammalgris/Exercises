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


import java.util.HashMap;
import java.util.Map;

import jmul.functions.conditions.Condition;
import jmul.functions.conditions.ConditionFunctionEntry;

import jmul.math.numbers.Number;


/**
 * Implements a function with a threshold (i.e. function1 &lt; threshold &lt;= function2).
 *
 * @author Kristian Kutin
 */
public class ThresholdFunctionImpl implements Function {

    /**
     * A map containing all functions and conditions.
     */
    private Map<Condition<Number>, Function> functionMap;

    /**
     * Creates a new threshold function.
     *
     * @param entries
     *        all condition-&gt;function entries
     */
    public ThresholdFunctionImpl(ConditionFunctionEntry... entries) {

        super();

        if (entries == null) {

            throw new IllegalArgumentException("No entries (null) were specified!");
        }

        this.functionMap = new HashMap<>();

        for (ConditionFunctionEntry entry : entries) {

            functionMap.put(entry.condition, entry.function);
        }
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

        for (Map.Entry<Condition<Number>, Function> entry : functionMap.entrySet()) {

            Condition<Number> condition = entry.getKey();
            Function function = entry.getValue();

            if (condition.meetsCondition(number)) {

                return function.calculate(number);
            }
        }

        throw new MissingConditionCaseException();
    }

    /**
     * Returns the derivative function for this function.
     *
     * @return a derivative function
     */
    @Override
    public Function derivativeFunction() {

        int length = functionMap.size();
        ConditionFunctionEntry[] entries = new ConditionFunctionEntry[length];

        int index = 0;
        for (Map.Entry<Condition<Number>, Function> entry : functionMap.entrySet()) {

            Condition<Number> condition = entry.getKey();
            Function function = entry.getValue();

            entries[index] = new ConditionFunctionEntry(condition, function.derivativeFunction());
            index++;
        }

        return new ThresholdFunctionImpl(entries);
    }

    /**
     * Returns a string representation for this function.
     *
     * @return a string representation
     */
    @Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();

        buffer.append("f(x) = { ");

        boolean first = true;
        for (Map.Entry<Condition<Number>, Function> entry : functionMap.entrySet()) {

            if (first) {

                first = false;

            } else {

                buffer.append("; ");
            }

            Condition<Number> condition = entry.getKey();
            Function function = entry.getValue();

            buffer.append(condition);
            buffer.append(" : ");
            buffer.append(function);
        }

        buffer.append(" }");

        return buffer.toString();
    }

}
