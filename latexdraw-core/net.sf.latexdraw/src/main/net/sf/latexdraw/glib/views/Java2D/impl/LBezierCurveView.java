package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.shape.IArrow;
import net.sf.latexdraw.glib.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.shape.ILine;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewBezierCurve;

/**
 * Defines a view of the model IBeziershape.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 03/01/08<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class LBezierCurveView extends LModifiablePointsShapeView<IBezierCurve> implements IViewBezierCurve {
	/**
	 * Creates an initialises the Java view of a LBeziershape.
	 * @param model The model to view.
	 * @since 3.0
	 */
	protected LBezierCurveView(final IBezierCurve model) {
		super(model);
		arrows = new ArrayList<>();
		for(int i=0, size=shape.getNbArrows(); i<size; i++)
			arrows.add(new LArrowView(shape.getArrowAt(i)));
		update();
	}


	@Override
	public void paintShowPointsDots(final Graphics2D g) {//FIXME to add into the path not into the graphics
		final boolean isClosed		= shape.isClosed();
		final IArrow arr1			= shape.getArrowAt(0);
		final boolean arrow1Drawable 		= arr1.hasStyle() && shape.getNbPoints()>1;
		final boolean arrow2Drawable 		= shape.getArrowAt(-1).hasStyle() && shape.getNbPoints()>1 && !isClosed;
		final int size 				= shape.getNbPoints();
		final List<IPoint> pts 		= shape.getPoints();
		final List<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
		final List<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
		final double width 			= arr1.getDotSizeDim() + arr1.getDotSizeNum()*shape.getThickness();
		final Ellipse2D.Double d 			= new Ellipse2D.Double(0, 0, width, width);
		int i;

		g.setColor(shape.getLineColour().toAWT());

		if(!arrow1Drawable || isClosed)
			fillCircle(d, pts.get(0), width, g);

		if(!arrow2Drawable || isClosed)
			fillCircle(d, pts.get(size-1), width, g);

		for(i=1; i<size-1; i++) {
			fillCircle(d, pts.get(i), width, g);
			fillCircle(d, ctrlPts2.get(i), width, g);
		}

		for(i=0; i<size; i++)
			fillCircle(d, ctrlPts1.get(i), width, g);

		if(shape.isClosed()) {
			fillCircle(d, ctrlPts2.get(ctrlPts2.size()-1), width, g);
			fillCircle(d, ctrlPts2.get(0), width, g);
		}
	}



	private void fillCircle(final Ellipse2D ell, final IPoint pt, final double width, final Graphics2D g) {
		ell.setFrame(pt.getX()-width/2., pt.getY()-width/2., width, width);
		g.fill(ell);
	}



	private void paintLine(final Line2D line, final IPoint pt1, final IPoint pt2, final Graphics2D g) {
		line.setLine(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
		g.draw(line);
	}


	@Override
	public void paintShowPointsLines(final Graphics2D g) {
		final int size 				= shape.getNbPoints();
		final List<IPoint> pts 		= shape.getPoints();
		final List<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
		final List<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
		final float thick 			= (float)(shape.hasDbleBord()? shape.getDbleBordSep()+shape.getThickness()*2. : shape.getThickness());
		final Line2D.Double line 			= new Line2D.Double();
		int i;

		g.setStroke(new BasicStroke(thick/2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
					1.f, new float[]{(float)shape.getDashSepBlack(), (float)shape.getDashSepWhite()}, 0));
		g.setColor(shape.getLineColour().toAWT());

		for(i=3; i<size; i+=2) {
			paintLine(line, pts.get(i-1), ctrlPts2.get(i-1), g);
			paintLine(line, ctrlPts2.get(i-1), ctrlPts1.get(i), g);
			paintLine(line, ctrlPts1.get(i), pts.get(i), g);
		}

		for(i=2; i<size; i+=2) {
			paintLine(line, pts.get(i-1), ctrlPts2.get(i-1), g);
			paintLine(line, ctrlPts2.get(i-1), ctrlPts1.get(i), g);
			paintLine(line, ctrlPts1.get(i), pts.get(i), g);
		}

		if(shape.isClosed()) {
			paintLine(line, pts.get(size-1), ctrlPts2.get(size-1), g);
			paintLine(line, ctrlPts2.get(size-1), ctrlPts2.get(0), g);
			paintLine(line, ctrlPts2.get(0), pts.get(0), g);
		}

		paintLine(line, pts.get(0), ctrlPts1.get(0), g);
		paintLine(line, ctrlPts1.get(0), ctrlPts1.get(1), g);
		paintLine(line, ctrlPts1.get(1), pts.get(1), g);
	}


	protected static double[] updatePoint4Arrows(final double x, final double y, final IArrow arr) {
		final double[] coords = {x, y};

		if(arr.getArrowStyle().isReducingShape()) {
			final ILine line = arr.getArrowLine();

			if(line!=null) {
				final IPoint[] ps = line.findPoints(line.getPoint1(), arr.getArrowShapeLength()/2.);
				if(ps!=null) {
					if(line.isInSegment(ps[0])) {
						coords[0] = ps[0].getX();
						coords[1] = ps[0].getY();
					}else {
						coords[0] = ps[1].getX();
						coords[1] = ps[1].getY();
					}
				}
			}
		}
		return coords;
	}


	@Override
	public void updateBorder() {
		super.updateBorder();
		if(shape.isShowPts()) {
			final List<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
			final List<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
			double minX = Double.MAX_VALUE;
			double minY = Double.MAX_VALUE;
			double maxX = Double.MIN_VALUE;
			double maxY = Double.MIN_VALUE;
			IPoint pt;

            for(final IPoint aCtrlPts1 : ctrlPts1) {
                if (aCtrlPts1.getX() < minX) minX = aCtrlPts1.getX();
                if (aCtrlPts1.getY() < minY) minY = aCtrlPts1.getY();
                if (aCtrlPts1.getX() > maxX) maxX = aCtrlPts1.getX();
                if (aCtrlPts1.getY() > maxY) maxY = aCtrlPts1.getY();
            }

			for(int i=1, size=ctrlPts2.size()-1; i<size; i++) {
				pt = ctrlPts2.get(i);
				if(pt.getX()<minX) minX = pt.getX();
				if(pt.getY()<minY) minY = pt.getY();
				if(pt.getX()>maxX) maxX = pt.getX();
				if(pt.getY()>maxY) maxY = pt.getY();
			}

			minX = Math.min(minX, border.getMinX());
			minY = Math.min(minY, border.getMinY());
			maxX = Math.max(maxX, border.getMaxX());
			maxY = Math.max(maxY, border.getMaxY());
			border.setFrameFromDiagonal(minX, minY, maxX, maxY);
		}
	}


	@Override
	protected void setPath(final boolean close) {
		if(shape.getNbPoints()<2) return ;

		final List<IPoint> pts 		= shape.getPoints();
		final List<IPoint> ctrlPts1 = shape.getFirstCtrlPts();
		final List<IPoint> ctrlPts2 = shape.getSecondCtrlPts();
		IPoint ctrl1;
		final int size;
		final double[] coords = updatePoint4Arrows(pts.get(0).getX(), pts.get(0).getY(), shape.getArrowAt(0));
		final double[] coords2;
		if(pts.size()==2)
			// In this case the first curve contains the first and last points that must be modified.
			coords2 = updatePoint4Arrows(pts.get(1).getX(),pts.get(1).getY(), shape.getArrowAt(-1));
		else coords2 = new double[]{pts.get(1).getX(),pts.get(1).getY()};

		path.reset();
		path.moveTo(coords[0], coords[1]);
		path.curveTo(ctrlPts1.get(0).getX(), ctrlPts1.get(0).getY(),
				   ctrlPts1.get(1).getX(), ctrlPts1.get(1).getY(),
				   coords2[0], coords2[1]);

		if(shape.isClosed())
			 size = pts.size();
		else size = pts.size()-1;

		for(int i=2; i<size; i++) {
			ctrl1 = ctrlPts2.get(i-1);
			path.curveTo(ctrl1.getX(), ctrl1.getY(),
					   ctrlPts1.get(i).getX(), ctrlPts1.get(i).getY(),
					   pts.get(i).getX(), pts.get(i).getY());
		}

		if(shape.isClosed()) {
			final IPoint ctrl1b = ctrlPts1.get(0).centralSymmetry(pts.get(0));
			final IPoint ctrl2b = ctrlPts1.get(ctrlPts1.size()-1).centralSymmetry(pts.get(pts.size()-1));
			path.curveTo(ctrl2b.getX(), ctrl2b.getY(), ctrl1b.getX(), ctrl1b.getY(), pts.get(0).getX(), pts.get(0).getY());
			path.closePath();
		}else {
			if(pts.size()>2) {
				ctrl1 = ctrlPts2.get(size-1);
				path.curveTo(ctrl1.getX(), ctrl1.getY(), ctrlPts1.get(size).getX(), ctrlPts1.get(size).getY(), pts.get(size).getX(),  pts.get(size).getY());
			}
		}
	}
}
