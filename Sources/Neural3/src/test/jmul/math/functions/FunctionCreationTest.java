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

package test.jmul.math.functions;


import jmul.data.DataEntry;
import jmul.data.TrainingData;

import jmul.functions.Function;
import jmul.functions.FunctionHelper;
import jmul.functions.conditions.ConditionFunctionEntry;
import jmul.functions.conditions.GreaterOrEqualCondition;
import jmul.functions.conditions.LesserThanCondition;

import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.GlobalSettings;

import jmul.test.classification.UnitTest;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * This test suite tests various polinomial functions.
 *
 * @author Kristian Kutin
 */
@UnitTest
public class FunctionCreationTest {

    /**
     * ZERO
     */
    private static final Number ZERO;

    /**
     * ONE
     */
    private static final Number ONE;

    /*
     * The static initializer.
     */
    static {

        ZERO = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0");
        ONE = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1");
    }

    /**
     * Tests a constant function.
     */
    @Test
    public void testConstantFunctionCreation() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")));

        Function f = FunctionHelper.createPolynomialFunction();

        String s = f.toString();
        assertEquals("formula", "f(x) = 0", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

    /**
     * Tests the derivative function of a constant function.
     */
    @Test
    public void testConstantFunctionDerivativeFunction() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")));

        Function f = FunctionHelper.createPolynomialFunction();
        f = f.derivativeFunction();

        String s = f.toString();
        assertEquals("formula", "f(x) = 0", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

    /**
     * Tests a constant function.
     */
    @Test
    public void testConstantFunction2Creation() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "5")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "5")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "5")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "5")));

        Function f = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "5");

        String s = f.toString();
        assertEquals("formula", "f(x) = 5", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

    /**
     * Tests a constant function.
     */
    @Test
    public void testConstantFunctionDerivativeFunction2() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0")));

        Function f = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "5");
        f = f.derivativeFunction();

        String s = f.toString();
        assertEquals("formula", "f(x) = 0", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

    /**
     * Tests a linear function.
     */
    @Test
    public void testLinarFunctionCreation() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "5")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "7")));

        Function f = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "1", "2");

        String s = f.toString();
        assertEquals("formula", "f(x) = 2 * x + 1", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

    /**
     * Tests the derivative function of a linear function.
     */
    @Test
    public void testLinarFunctionDerivativeFunction() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2")));

        Function f = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "1", "2");
        f = f.derivativeFunction();

        String s = f.toString();
        assertEquals("formula", "f(x) = 2", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

    /**
     * Tests a quadratic function.
     */
    @Test
    public void testQuadraticFunctionCreation() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "6")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "17")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "34")));

        Function f = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "1", "2", "3");

        String s = f.toString();
        assertEquals("formula", "f(x) = 3 * x^2 + 2 * x + 1", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

    /**
     * Tests the derivative function of a quadratic function.
     */
    @Test
    public void testDerivativeFunctionQuadraticFunction() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "8")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "14")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "20")));

        Function f = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "1", "2", "3");
        f = f.derivativeFunction();

        String s = f.toString();
        assertEquals("formula", "f(x) = 6 * x + 2", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

    /**
     * Tests a cubic function.
     */
    @Test
    public void testCubicFunctionCreation() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "10")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "49")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "142")));

        Function f = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "1", "2", "3", "4");

        String s = f.toString();
        assertEquals("formula", "f(x) = 4 * x^3 + 3 * x^2 + 2 * x + 1", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

    /**
     * Tests the derivative function of a cubic function.
     */
    @Test
    public void testDerivateiveFunctionCubicFunction() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "20")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "62")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "128")));

        Function f = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "1", "2", "3", "4");
        f = f.derivativeFunction();

        String s = f.toString();
        assertEquals("formula", "f(x) = 12 * x^2 + 6 * x + 2", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

    /**
     * Tests a threshold function.
     */
    @Test
    public void testThresholdFunctionCreation() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "6")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "5")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "7")));

        Function f1 = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "1", "2", "3");
        Function f2 = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "1", "2");
        Function f =
            FunctionHelper.createThresholdFunction(new ConditionFunctionEntry(new LesserThanCondition(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE,
                                                                                                                   "2")),
                                                                              f1),
                                                   new ConditionFunctionEntry(new GreaterOrEqualCondition(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE,
                                                                                                                       "2")),
                                                                              f2));

        String s = f.toString();
        assertEquals("formula", "f(x) = { x < 2 : f(x) = 3 * x^2 + 2 * x + 1; x >= 2 : f(x) = 2 * x + 1 }", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

    /**
     * Tests the derivative function of a threshold function.
     */
    @Test
    public void testDerivativeFunctionThresholdFunction() {

        TrainingData data =
            new TrainingData(new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "1"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "8")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2")),
                             new DataEntry(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "3"),
                                           createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "2")));

        Function f1 = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "1", "2", "3");
        Function f2 = FunctionHelper.createPolynomialFunction(GlobalSettings.DEFAULT_NUMBER_BASE, "1", "2");
        Function f =
            FunctionHelper.createThresholdFunction(new ConditionFunctionEntry(new LesserThanCondition(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE,
                                                                                                                   "2")),
                                                                              f1),
                                                   new ConditionFunctionEntry(new GreaterOrEqualCondition(createNumber(GlobalSettings.DEFAULT_NUMBER_BASE,
                                                                                                                       "2")),
                                                                              f2));
        f = f.derivativeFunction();

        String s = f.toString();
        assertEquals("formula", "f(x) = { x < 2 : f(x) = 6 * x + 2; x >= 2 : f(x) = 2 }", s);

        for (DataEntry entry : data) {

            Number actualOutput = f.calculate(entry.input);
            assertEquals("function values", entry.expectedOutput, actualOutput);
        }
    }

}
