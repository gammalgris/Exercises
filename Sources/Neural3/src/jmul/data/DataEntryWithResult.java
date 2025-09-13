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

package jmul.data;


import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.GlobalSettings;


/**
 * This class defines a data entry.
 *
 * @author Kristian Kutin
 */
public class DataEntryWithResult extends DataEntry {

    /**
     * The corresponding expected output data.
     */
    public final Number actualOutput;

    /**
     * Creates a new entry accoring to the specified parameters.
     *
     * @param input
     *        an input
     * @param expectedOutput
     *        the expected output
     * @param actualOutput
     *        the actual output
     */
    public DataEntryWithResult(Number input, Number expectedOutput, Number actualOutput) {

        super(input, expectedOutput);

        if (actualOutput == null) {

            throw new IllegalArgumentException("No actual output (null) was specified!");
        }

        this.actualOutput = actualOutput;
    }

    /**
     * Creates a new entry accoring to the specified parameters.
     *
     * @param inputString
     *        an input string (i.e. number string)
     * @param expectedOutputString
     *        an output string (i.e. number string)
     * @param actualOutputString
     *        an output string (i.e. number string)
     */
    public DataEntryWithResult(String inputString, String expectedOutputString, String actualOutputString) {

        this(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, inputString),
             createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, expectedOutputString),
             createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, actualOutputString));
    }

    /**
     * Returns the deviation from the expected output and the actual output (i.e. the actual output is subtracted from
     * the expected output).
     *
     * @return a deviation
     */
    public Number deviation() {

        return expectedOutput.subtract(actualOutput);
    }

    @Override
    public String toString() {

        return String.format("inpput=%s; expected output=%s; actual output=%s", input, expectedOutput, actualOutput);
    }

}
