package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IPositionShape;

/**
 * Defines a model of a shape that has a position.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 07/05/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
abstract class LPositionShape extends LShape implements IPositionShape {
	/**
	 * Creates a LPositionShape with a predefined point.
	 * @param pt The position. If pt is not valid, a point at position (0,0) is used.
	 */
	protected LPositionShape(final IPoint pt) {
		super();
		points.add(GLibUtilities.isValidPoint(pt) ? pt : ShapeFactory.createPoint());
	}


	@Override
	public void setPosition(final IPoint pt) {
		if(GLibUtilities.isValidPoint(pt))
			setPosition(pt.getX(), pt.getY());
	}


	@Override
	public void setPosition(final double x, final double y) {
		if(GLibUtilities.isValidPoint(x, y)) {
			final IPoint pos = getPosition();
			translate(x - pos.getX(), y - pos.getY());
		}
	}


	@Override
	public void setX(final double x) {
		if(GLibUtilities.isValidCoordinate(x))
			translate(x - getPosition().getX(), 0);
	}


	@Override
	public void setY(final double y) {
		if(GLibUtilities.isValidCoordinate(y))
			translate(0, y - getPosition().getY());
	}


	@Override
	public IPoint getPosition() {
		return getBottomLeftPoint();
	}


	@Override
	public double getX() {
		return getPosition().getX();
	}


	@Override
	public double getY() {
		return getPosition().getY();
	}
}
