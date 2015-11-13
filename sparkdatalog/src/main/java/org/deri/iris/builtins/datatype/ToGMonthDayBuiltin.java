/*
 * Integrated Rule Inference System (IRIS):
 * An extensible rule inference system for datalog with extensions.
 * 
 * Copyright (C) 2009 Semantic Technology Institute (STI) Innsbruck, 
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
package org.deri.iris.builtins.datatype;

import org.deri.iris.api.basics.IPredicate;
import org.deri.iris.api.terms.ITerm;
import org.deri.iris.api.terms.concrete.IDateTerm;
import org.deri.iris.api.terms.concrete.IDateTime;
import org.deri.iris.api.terms.concrete.IGMonthDay;

import static org.deri.iris.factory.Factory.BASIC;
import static org.deri.iris.factory.Factory.CONCRETE;

/**
 * Represents a data type conversion function, which converts supported data
 * type instances to GMonthDay instances. The following data types are
 * supported:
 * <ul>
 * <li>Date</li>
 * <li>DateTime</li>
 * </ul>
 */
public class ToGMonthDayBuiltin extends ConversionBuiltin {

	private static final IPredicate PREDICATE = BASIC.createPredicate(
			"TO_GMONTHDAY", 2);

	/**
	 * Creates a new instance of this builtin.
	 * 
	 * @param terms The term representing the data type instance to be
	 *            converted.
	 */
	public ToGMonthDayBuiltin(ITerm... terms) {
		super(PREDICATE, terms);
	}

	@Override
	protected ITerm convert(ITerm term) {
		if (term instanceof IGMonthDay) {
			return term;
		} else if (term instanceof IDateTerm) {
			return toGMonthDay((IDateTerm) term);
		} else if (term instanceof IDateTime) {
			return toGMonthDay((IDateTime) term);
		}

		return null;
	}

	/**
	 * Converts a Date term to a GMonthDay term.
	 * 
	 * @param term The Date term to be converted.
	 * @return A new GMonthDay term representing the result of the conversion.
	 */
	public static IGMonthDay toGMonthDay(IDateTerm term) {
		return CONCRETE.createGMonthDay(term.getMonth(), term.getDay());
	}

	/**
	 * Converts a DateTime term to a GMonthDay term.
	 * 
	 * @param term The DateTime term to be converted.
	 * @return A new GMonthDay term representing the result of the conversion.
	 */
	public static IGMonthDay toGMonthDay(IDateTime term) {
		return CONCRETE.createGMonthDay(term.getMonth(), term.getDay());
	}

}
