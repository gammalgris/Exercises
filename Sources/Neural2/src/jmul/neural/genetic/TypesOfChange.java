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

package jmul.neural.genetic;


public enum TypesOfChange implements TypeOfChange {

    MUTATE_NEURON_BIAS,
    MUTATE_NEURON_ACTIVATION_FUNCTION,
    MUTATE_SYNAPSE_WEIGHT;

    /**
     * Returns a random activation function.
     *
     * @return an activation function
     */
    public static TypeOfChange randomTypeOfChange() {

        TypeOfChange[] typesOfChanges = values();
        int min = 0;
        int max = typesOfChanges.length;

        int randomIndex = (int) (Math.random() * ((double) max - (double) min) + (double) min);

        TypeOfChange typeOfChange = typesOfChanges[randomIndex];

        return typeOfChange;
    }

}
