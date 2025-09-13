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

package jmul.neural.neurons;


import jmul.functions.Function;

import jmul.math.numbers.Number;

import jmul.neural.signals.SignalListener;
import jmul.neural.signals.SignalSource;


/**
 * This interface describes a neuron.
 *
 * @author Kristian Kutin
 */
public interface Neuron extends SignalSource, SignalListener {

    /**
     * The activation function for this neuron.
     *
     * @return an activation function
     */
    Function activationFunction();

    /**
     * The bias of this neuron.
     *
     * @return a bias value
     */
    Number bias();

    /**
     * The layer to which this synapse belongs to.
     *
     * @return a layer
     */
    Layer layer();

}
