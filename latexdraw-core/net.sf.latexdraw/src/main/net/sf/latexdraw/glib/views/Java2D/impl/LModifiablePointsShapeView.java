package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewModifiablePtsShape;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines an abstract view of the IModifiablePointsShape model.<br>
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
 * 03/18/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
abstract class LModifiablePointsShapeView<S extends IModifiablePointsShape> extends LShapeView<S> implements IViewModifiablePtsShape {
	/**
	 * Initialises the view of a IModifiablePointsShape.
	 */
	protected LModifiablePointsShapeView(final S model) {
		super(model);
	}

	/**
	 * Update the path of the multi-point shape.
	 * @param close True: the shape will be closed.
	 * @since 3.0
	 */
	protected void setPath(final boolean close) {
		final List<IPoint> pts = shape.getPoints();

		path.reset();

		if(pts.size()>1) {
			final int size 	= pts.size();
			IPoint pt		= pts.get(0);
			final double firstX = pt.getX();
			final double firstY = pt.getY();

			path.moveTo(firstX, firstY);

			for(int i=1; i<size-1; i++) {
				pt = pts.get(i);
				path.lineTo(pt.getX(), pt.getY());
			}

			pt = pts.get(pts.size()-1);
			path.lineTo(pt.getX(), pt.getY());

			final Rectangle2D bound = path.getBounds2D();
			if(LNumber.equalsDouble(bound.getWidth(), 0.001) && LNumber.equalsDouble(bound.getHeight(), 0.001)) {
				path.lineTo(firstX+1, firstY);
			}
			else
				if(close)
					path.closePath();
		}
	}


	@Override
	public Shape getRotatedShape2D() {
		return path;
	}

	@Override
	protected IPoint beginRotation(final Graphics2D g) {
		return null;
	}

	@Override
	protected void endRotation(final Graphics2D g, final IPoint translation) {
		// Nothing to do.
	}

	@Override
	protected void updateDblePathInside() {
		updateDblePathMiddle();
	}

	@Override
	protected void updateDblePathMiddle() {
		updateGeneralPathMiddle();
	}

	@Override
	protected void updateDblePathOutside() {
		updateDblePathMiddle();
	}

	@Override
	protected void updateGeneralPathInside() {
		updateGeneralPathMiddle();
	}

	@Override
	protected void updateGeneralPathMiddle() {
		setPath(true);
	}

	@Override
	protected void updateGeneralPathOutside() {
		updateGeneralPathMiddle();
	}
}
