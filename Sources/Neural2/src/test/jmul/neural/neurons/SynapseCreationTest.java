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

package test.jmul.neural.neurons;


import jmul.neural.neurons.Synapse;
import jmul.neural.neurons.SynapseImpl;

import jmul.test.classification.UnitTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import org.junit.Test;


/**
 * This test suite tests creating a synapse.
 *
 * @author Kristian Kutin
 */
@UnitTest
public class SynapseCreationTest {

    /**
     * Tests creating a synapse.
     */
    @Test
    public void createSynapse() {

        SynapseImpl synapse = new SynapseImpl();

        assertNull(synapse.weight());

        assertFalse(synapse.hasWeight());

        Synapse synapse2 = (Synapse) synapse;
        assertNull(synapse2.weight());
    }

}
