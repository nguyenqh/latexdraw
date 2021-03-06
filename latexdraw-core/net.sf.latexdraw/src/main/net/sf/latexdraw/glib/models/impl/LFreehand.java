package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.prop.IFreeHandProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

/**
 * Defines a model of a free hand shape.<br>
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
class LFreehand extends LModifiablePointsShape implements IFreehand {
	/** The type of the curves of the shape. */
	protected FreeHandType type;

	/** The interval to consider while painting the shape. */
	protected int interval;

	/** Defines if the drawing is opened of closed. */
	protected boolean open;


	/**
	 * Creates and initialises a freehand model.
	 * @throws IllegalArgumentException If the given point is not valid.
	 * @since 3.0
	 */
	protected LFreehand() {
		super();
		type 		= FreeHandType.CURVES;
		interval 	= 5;
		open		= true;
	}


	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IFreeHandProp) {
			final IFreeHandProp fh = (IFreeHandProp)sh;
			open 	= fh.isOpen();
			interval= fh.getInterval();
			type 	= fh.getType();
		}
	}


	@Override
	public int getInterval() {
		return interval;
	}


	@Override
	public FreeHandType getType() {
		return type;
	}


	@Override
	public boolean isOpen() {
		return open;
	}


	@Override
	public void setInterval(final int interval) {
		if(interval>0)
			this.interval = interval;
	}


	@Override
	public void setOpen(final boolean open) {
		this.open = open;
	}


	@Override
	public void setType(final FreeHandType type) {
		if(type!=null)
			this.type = type;
	}

	@Override
	public boolean isBordersMovable() {
		return false;
	}


	@Override
	public boolean isDbleBorderable() {
		return false;
	}


	@Override
	public boolean isFillable() {
		return true;
	}


	@Override
	public boolean isInteriorStylable() {
		return true;
	}


	@Override
	public boolean isLineStylable() {
		return true;
	}


	@Override
	public boolean isShadowable() {
		return true;
	}


	@Override
	public boolean isShowPtsable() {
		return false;
	}


	@Override
	public boolean isThicknessable() {
		return true;
	}
}
