/*
 *    AssetNegotiationGenerator.java
 *    Copyright (C) 2016 Pontifícia Universidade Católica do Paraná, Curitiba, Brazil
 *    @author Jean Paul Barddal (jean.barddal@ppgia.pucpr.br)
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program. If not, see <http://www.gnu.org/licenses/>.
 *    
 */

package moa.streams.generators;

import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Attribute;
import com.yahoo.labs.samoa.instances.DenseInstance;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Instances;
import com.yahoo.labs.samoa.instances.InstancesHeader;
import java.util.Arrays;
import java.util.Random;
import moa.core.FastVector;
import moa.core.InstanceExample;
import moa.core.ObjectRepository;
import moa.options.AbstractOptionHandler;
import moa.streams.InstanceStream;
import moa.tasks.TaskMonitor;

/**
 *
 * @author Jean Paul Barddal
 * @author Fabrício Enembreck
 *
 * @version 1.0
 * @see Originally discussed in F. Enembreck, B. C. Ávila, E. E. Scalabrin &
 * J-P. Barthès. LEARNING DRIFTING NEGOTIATIONS. In Applied Artificial
 * Intelligence: An International Journal. Volume 21, Issue 9, 2007. DOI:
 * 10.1080/08839510701526954
 * @see First used in the data stream configuration in J. P. Barddal, H. M.
 * Gomes, F. Enembreck, B. Pfahringer & A. Bifet. ON DYNAMIC FEATURE WEIGHTING
 * FOR FEATURE DRIFTING DATA STREAMS. In European Conference on Machine Learning
 * and Principles and Practice of Knowledge Discovery (ECML/PKDD'16). 2016.
 */

public class AssetNegotiationGenerator
	extends AbstractOptionHandler
	implements InstanceStream {

// Options

// Internals

// Feature Definition

// Labeling functions
    
// Generator Code
    }