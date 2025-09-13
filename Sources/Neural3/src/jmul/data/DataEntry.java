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
public class DataEntry {

    /**
     * Input data.
     */
    public final Number input;

    /**
     * The corresponding expected output data.
     */
    public final Number expectedOutput;

    /**
     * Creates a new entry accoring to the specified parameters.
     *
     * @param input
     *        an input
     * @param expectedOutput
     *        the expected output
     */
    public DataEntry(Number input, Number expectedOutput) {

        super();

        if (input == null) {

            throw new IllegalArgumentException("No input (null) was specified!");
        }

        if (expectedOutput == null) {

            throw new IllegalArgumentException("No expected output (null) was specified!");
        }

        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    /**
     * Creates a new entry accoring to the specified parameters.
     *
     * @param inputString
     *        an input string (i.e. number string)
     * @param expectedOutputString
     *        an output string (i.e. number string)
     */
    public DataEntry(String inputString, String expectedOutputString) {

        this(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, inputString),
             createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, expectedOutputString));
    }

    @Override
    public String toString( ) {

        return String.format("inpput=%s; expected output=%s", input, expectedOutput);
    }

}
