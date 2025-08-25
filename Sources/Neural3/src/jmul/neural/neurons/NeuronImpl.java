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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jmul.functions.Function;

import jmul.math.numbers.Number;
import static jmul.math.numbers.NumberHelper.createNumber;

import jmul.neural.GlobalSettings;
import jmul.neural.signals.Signal;
import jmul.neural.signals.SignalImpl;
import jmul.neural.signals.SignalListener;
import jmul.neural.signals.SignalSource;


/**
 * A concurrent implementation of a neuron.
 *
 * @author Kristian Kutin
 */
public class NeuronImpl implements Neuron {

    /**
     * This neuron's bias.
     */
    private Number bias;

    /**
     * An activation function.
     */
    private Function activationFunction;

    /**
     * All listeners.
     */
    private List<SignalListener> listeners;

    private List<SignalSource> signalSources;

    private Map<SignalSource, Number> cache;

    /**
     * Creates a new neuron.
     */
    public NeuronImpl() {

        super();

        this.bias = null;
        this.activationFunction = null;
        this.listeners = new ArrayList<>();
        this.signalSources = new ArrayList<>();
        this.cache = new HashMap<>();
    }

    /**
     * Checks if this neuron has a bias.
     *
     * @return <code>true</code> if this neuron has a bias, else <code>false</code>
     */
    public boolean hasBias() {

        return this.bias != null;
    }

    /**
     * The bias of this neuron.
     *
     * @return a bias value
     */
    @Override
    public Number bias() {

        return this.bias;
    }

    /**
     * Sets the bias.
     *
     * @param bias
     *        this neuron's new bias
     */
    public void setBias(Number bias) {

        this.bias = bias;
    }

    /**
     * The activation function for this neuron.
     *
     * @return an activation function
     */
    @Override
    public Function activationFunction() {

        return activationFunction;
    }

    /**
     * Replaces the activation function.
     *
     * @param activationFunction
     *        an activation function.
     */
    public void setActivationFunction(Function activationFunction) {

        this.activationFunction = activationFunction;
    }

    public void addListener(SignalListener listener) {

        listeners.add(listener);
    }

    public void removeListener(SignalListener listener) {

        listeners.remove(listener);
    }

    public void addSignalSource(SignalSource source) {

        signalSources.add(source);
    }

    public void removeSignalSource(SignalSource source) {

        signalSources.remove(signalSources);
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

        Number amplifiedSignal = activationFunction.calculate(signal.value());

        return new SignalImpl(this, amplifiedSignal);
    }

    @Override
    public void sendSignal(Signal signal) {

        cache.put(signal.source(), signal.value());

        if (cache.size() == signalSources.size()) {

            Number sum = createNumber(GlobalSettings.DEFAULT_NUMBER_BASE, "0");
            for (Number value : cache.values()) {

                sum = sum.add(value);
            }

            Signal newSignal = new SignalImpl(this, sum);
            newSignal = amplifySignal(signal);

            for (SignalListener listener : listeners) {

                listener.sendSignal(newSignal);
            }

            cache.clear();
        }
    }

}
