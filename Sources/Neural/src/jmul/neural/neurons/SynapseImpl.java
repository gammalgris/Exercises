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


import java.util.LinkedList;

import jmul.math.numbers.Number;

import jmul.neural.signals.Signal;
import jmul.neural.signals.SignalImpl;


/**
 * An implementation of a synapse.
 *
 * @author Kristian Kutin
 */
public class SynapseImpl implements Synapse {

    /**
     * The weight of this synapse (i.e. a value that amplifies a signal).
     */
    private Number weight;

    /**
     * A signal queue.
     */
    private LinkedList<Signal> signalQueue;

    /**
     * Creates a new synapse without any connections.
     */
    public SynapseImpl() {

        super();

        this.weight = null;
        this.signalQueue = new LinkedList<Signal>();
    }

    /**
     * Checks if this synapse has a weight.
     *
     * @return <code>true</code> if this synapse has a weight, else <code>false</code>
     */
    public boolean hasWeight() {

        return this.weight != null;
    }

    /**
     * The weight which amplifies an input.
     *
     * @return a weight
     */
    @Override
    public Number weight() {

        return this.weight;
    }

    /**
     * Sets the weight for this synapse.
     *
     * @param weight
     *        a weight
     */
    public void setWeight(Number weight) {

        this.weight = weight;
    }

    /**
     * Checks if this synapse has a stored signal.
     *
     * @return <code>true</code> if this synapse has stored signals, else <code>false</code>
     */
    @Override
    public boolean hasStoredSignals() {

        boolean result;

        synchronized (this) {

            result = signalQueue.size() > 0;
        }

        return result;
    }

    /**
     * Transmits a signal.
     *
     * @param signal
     *        a signal
     */
    @Override
    public void transmitSignal(Signal signal) {

        Signal amplifiedSignal = amplifySignal(signal);

        synchronized (this) {

            signalQueue.addFirst(amplifiedSignal);
        }
    }

    /**
     * Amplifies the specified signal.
     *
     * @param signal
     *        a signal
     *
     * @return an amplified signal
     */
    private Signal amplifySignal(Signal signal) {

        Number amplifiedSignal = signal.value().multiply(weight());

        return new SignalImpl(amplifiedSignal);
    }

    /**
     * Receives a signal.
     *
     * @return a signal
     */
    @Override
    public Signal receiveSignal() {

        Signal signal;

        synchronized (this) {

            signal = signalQueue.pollLast();
        }

        return signal;
    }

}
