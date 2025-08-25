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

import jmul.concurrent.threads.ThreadHelper;

import jmul.math.numbers.Number;

import jmul.neural.GlobalSettings;
import jmul.neural.functions.ActivationFunction;
import jmul.neural.signals.Signal;
import static jmul.neural.signals.SignalHelper.createSignal;


/**
 * A concurrent implementation of a neuron.
 *
 * @author Kristian Kutin
 */
public class ConcurrentNeuronImpl implements Neuron {

    /**
     * This neuron's bias.
     */
    private Number bias;

    /**
     * An activation function.
     */
    private ActivationFunction activationFunction;

    /**
     * All signal sources (i.e. synapses).
     */
    private List<Synapse> signalSources;

    /**
     * All signal destnations (i.e. synapses).
     */
    private List<Synapse> signalDestinations;

    /**
     * A flag that indicates whether this neuron is active or not.
     */
    private volatile boolean running; //TODO maybe replace with a status

    /**
     * Creates a new neuron.
     */
    public ConcurrentNeuronImpl() {

        super();

        this.bias = null;
        this.activationFunction = null;
        this.signalSources = new ArrayList<>();
        this.signalDestinations = new ArrayList<>();
        this.running = false;
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
    public ActivationFunction activationFunction() {

        return activationFunction;
    }

    /**
     * Replaces the activation function.
     *
     * @param activationFunction
     *        an activation function.
     */
    public void setActivationFunction(ActivationFunction activationFunction) {

        this.activationFunction = activationFunction;
    }

    /**
     * All signal sources (i.e. synapses).
     *
     * @return any number of synapses
     */
    @Override
    public List<Synapse> signalSources() {

        return signalSources;
    }

    /**
     * Adds the specified signal source.
     *
     * @param synapse
     *        a synapse
     */
    public void addSignalSource(Synapse synapse) {

        if (synapse == null) {

            throw new IllegalArgumentException("No synapse (null) was specified!");
        }

        this.signalSources.add(synapse);
    }

    /**
     * Removes the specified signal source.
     *
     * @param synapse
     *        a synapse
     */
    public void removeSignalSource(Synapse synapse) {

        if (synapse == null) {

            throw new IllegalArgumentException("No synapse (null) was specified!");
        }

        this.signalSources.remove(synapse);
    }

    /**
     * All signal destinations (i.e. synapses).
     *
     * @return any number of synapses
     */
    @Override
    public List<Synapse> signalDestinations() {

        return signalDestinations;
    }

    /**
     * Adds the specified signal destination.
     *
     * @param synapse
     *        a synapse
     */
    public void addSignalDestination(Synapse synapse) {

        if (synapse == null) {

            throw new IllegalArgumentException("No synapse (null) was specified!");
        }

        this.signalDestinations.add(synapse);
    }

    /**
     *
     * @param synapse
     */
    public void removeSignalDestination(Synapse synapse) {

        if (synapse == null) {

            throw new IllegalArgumentException("No synapse (null) was specified!");
        }

        this.signalDestinations.remove(synapse);
    }

    /**
     * Checks if this neuron is connected.
     *
     * @return <code>true</code> if this neuron is conneced, else <code>false</code>
     */
    public boolean isConnected() {

        return !signalSources.isEmpty() && !signalDestinations.isEmpty();
    }

    /**
     * Activates this neuron.
     */
    @Override
    public void activate() {

        synchronized (this) {

            running = true;
        }
    }

    /**
     * Deactivates this neuron.
     */
    @Override
    public void deactivate() {

        synchronized (this) {

            running = false;
        }
    }

    /**
     * This method handles receiving signals, modifying signals and sending on signals.
     */
    @Override
    public void run() {

        Map<Synapse, Signal> signals = new HashMap<>();

        while (running) {

            for (Synapse synapse : signalSources()) {

                if (signals.containsKey(synapse)) {

                    continue;
                }

                if (!synapse.hasStoredSignals()) {

                    continue;
                }

                Signal signal = synapse.receiveSignal();
                signals.put(synapse, signal);
            }

            if (signals.size() == signalSources.size()) {

                Number sum = null;
                for (Signal signal : signals.values()) {

                    Number signalValue = signal.value();
                    if (sum == null) {

                        sum = signalValue;

                    } else {

                        sum = sum.add(signalValue);
                    }
                }

                //String message = String.format("DEBUG:: %s -> %s", this, sum);
                //System.out.println(message);

                // apply the activation function and the bias to the signal sum
                sum = activationFunction().calculate(sum);
                sum = sum.multiply(bias());

                Signal signal = createSignal(sum);

                for (Synapse synapse : signalDestinations()) {

                    synapse.transmitSignal(signal);
                }

                signals.clear();
            }

            ThreadHelper.sleep(GlobalSettings.DEFAULT_SLEEP_TIME);
        }
    }

}
