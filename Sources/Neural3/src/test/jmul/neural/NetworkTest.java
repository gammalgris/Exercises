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

package test.jmul.neural;


import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.GlobalSettings;
import jmul.neural.neurons.FlatNetworkImpl;
import jmul.neural.neurons.Network;

import jmul.test.classification.ManualTest;


@ManualTest
public class NetworkTest {

    private static final int[] NETWORK_CONFIGURATION;

    static {

        NETWORK_CONFIGURATION = new int[] { 1, 4, 4, 1 };
    }

    public static void main(String... args) {

        Network network = new FlatNetworkImpl(NETWORK_CONFIGURATION);
        System.out.println(network);

        Number input = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "11");
        System.out.println("input: " + input);

        long before = System.currentTimeMillis();

        Number output = network.send(input);

        long after = System.currentTimeMillis();

        long delta = after - before;
        System.out.println("duration: " + delta + " ms");

        System.out.println("output: " + output);
    }

}
