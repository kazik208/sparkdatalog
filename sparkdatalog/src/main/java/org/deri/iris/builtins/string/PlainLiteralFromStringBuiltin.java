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
package org.deri.iris.builtins.string;

import org.deri.iris.EvaluationException;
import org.deri.iris.api.basics.IPredicate;
import org.deri.iris.api.terms.IStringTerm;
import org.deri.iris.api.terms.ITerm;
import org.deri.iris.builtins.FunctionalBuiltin;

import static org.deri.iris.factory.Factory.BASIC;
import static org.deri.iris.factory.Factory.CONCRETE;

/**
 * Represents the RIF built-in funciton func:PlainLiteral-from-string.
 */
public class PlainLiteralFromStringBuiltin extends FunctionalBuiltin {

	/** The predicate defining this built-in. */
	private static final IPredicate PREDICATE = BASIC.createPredicate(
			"TEXT_FROM_STRING", 2);

	/**
	 * Constructor. Two terms must be passed to the constructor, otherwise an
	 * exception will be thrown.
	 * 
	 * @param terms The terms.
	 * @throws IllegalArgumentException If one of the terms is {@code null}.
	 * @throws IllegalArgumentException If the number of terms submitted is not
	 *             2.
	 * @throws IllegalArgumentException If terms is <code>null</code>.
	 */
	public PlainLiteralFromStringBuiltin(ITerm... terms) {
		super(PREDICATE, terms);
	}

	protected ITerm computeResult(ITerm[] terms) throws EvaluationException {
		if (terms[0] instanceof IStringTerm) {
			String string = ((IStringTerm) terms[0]).getValue();

			return CONCRETE.createPlainLiteral(string);
		}

		return null;
	}

}
