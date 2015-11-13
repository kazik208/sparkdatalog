/*
 * Integrated Rule Inference System (IRIS):
 * An extensible rule inference system for datalog with extensions.
 * 
 * Copyright (C) 2008 Semantic Technology Institute (STI) Innsbruck, 
 * University of Innsbruck, Technikerstrasse 21a, 6020 Innsbruck, Austria.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 */
package org.deri.iris.rules;

import org.deri.iris.api.basics.IRule;

import java.util.Collection;
import java.util.List;

/**
 * Represents all classes that can optimise the order of rule evaluation.
 * Ideally, the rule with the fewest or no dependencies will be evaluated first
 * and so on up to the rule with the most dependencies.
 * Cycles, branches and independent networks will have to be taken in to account.
 */
public interface IRuleReOrderingOptimiser
{
	/**
	 * Re-order the rules.
	 * The returned collection will have an implied ordering.
	 * @param rules The rules to re-order.
	 * @return The same rules, but in a more efficient order for evaluation.
	 */
	List<IRule> reOrder(Collection<IRule> rules);
}
