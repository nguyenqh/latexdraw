package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import scala.collection.mutable.ListBuffer
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IGrid
import net.sf.latexdraw.glib.views.pst.PSTricksConstants
import net.sf.latexdraw.glib.models.interfaces.shape.IStandardGrid
import net.sf.latexdraw.util.LNumber
import net.sf.latexdraw.glib.models.interfaces.shape.IAxes
import net.sf.latexdraw.glib.models.ShapeFactory

/**
 * A parser grouping parsers parsing grids and axes.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
 * 2012-05-06<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSGridAxes extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser with PSTBracketBlockParser {
	def parsePsaxes(ctx : PSTContext) : Parser[List[IShape]] =
		"\\psaxes" ~ opt(parseParam(ctx)) ~ opt(parseBracket(ctx)) ~ opt(parseCoord(ctx)) ~ opt(parseCoord(ctx)) ~ opt(parseCoord(ctx)) ^^ {
		case _ ~ _ ~ arr ~ p1 ~ p2 ~ p3 =>
		(p1, p2, p3) match {
			case (Some(pt1), Some(pt2), Some(pt3)) => checkTextParsed(ctx) ::: List(createAxes(pt2, pt3, arr, ctx))
			case (Some(pt1), Some(pt2), None) => checkTextParsed(ctx) ::: List(createAxes(pt1, pt2, arr, ctx))
			case (Some(pt1), None, None) =>
				checkTextParsed(ctx) ::: List(createAxes(new PointUnit(0,0, "", ""), pt1, arr, ctx))
			case _ =>
				val gridEnd = new PointUnit(getApproxCoord(ctx.pictureNEPt.getX), getApproxCoord(ctx.pictureNEPt.getY), "", "")
				val gridStart = new PointUnit(getApproxCoord(ctx.pictureSWPt.getX), getApproxCoord(ctx.pictureSWPt.getY), "", "")
				checkTextParsed(ctx) ::: List(createAxes(gridStart, gridEnd, arr, ctx))
		}
	}


	/**
	 * Parses psgrid commands.
	 */
	def parsePsgrid(ctx : PSTContext) : Parser[List[IShape]] =
		"\\psgrid" ~ opt(parseParam(ctx)) ~ opt(parseCoord(ctx)) ~ opt(parseCoord(ctx)) ~ opt(parseCoord(ctx)) ^^ {
		case _ ~ _ ~ p1 ~ p2 ~ p3 =>

		(p1, p2, p3) match {
			case (Some(pt1), Some(pt2), Some(pt3)) => checkTextParsed(ctx) ::: List(createGrid(pt1, pt2, pt3, ctx))
			case (Some(pt1), Some(pt2), None) => checkTextParsed(ctx) ::: List(createGrid(pt1.dup(), pt1, pt2, ctx))
			case (Some(pt1), None, None) =>
				checkTextParsed(ctx) ::: List(createGrid(new PointUnit(0,0, "", ""), new PointUnit(0,0, "", ""), pt1, ctx))
			case _ =>
				val gridEnd = new PointUnit(getApproxCoord(ctx.pictureNEPt.getX), getApproxCoord(ctx.pictureNEPt.getY), "", "")
				val gridStart = new PointUnit(getApproxCoord(ctx.pictureSWPt.getX), getApproxCoord(ctx.pictureSWPt.getY), "", "")
				val grid = createGrid(new PointUnit(0,0,"",""), gridStart, gridEnd, ctx)
				grid.setPosition(0,0)
				grid.setLabelsSize(0)
				checkTextParsed(ctx) ::: List(grid)
		}
	}


	private def createAxes(min : PointUnit, max : PointUnit, arrows : Option[String], ctx : PSTContext) : IAxes = {
		val axes = ShapeFactory.createAxes(ShapeFactory.createPoint)

		setArrows(axes, arrows, false, ctx)
		setStdGridParams(new PointUnit(ctx.ox, ctx.oy, null, null), min, max, axes, ctx)
		setShapeGeneralParameters(axes, ctx)
		axes.setAxesStyle(ctx.axesStyle)
		axes.setTicksDisplayed(ctx.ticks)
		axes.setLabelsDisplayed(ctx.labels)
		axes.setTicksStyle(ctx.ticksStyle)
		axes.setIncrementX(ctx.dxIncrement)
		axes.setIncrementY(ctx.dyIncrement)
		axes.setDistLabelsX(ctx.dxLabelDist)
		axes.setDistLabelsY(ctx.dyLabelDist)
		axes.setShowOrigin(ctx.showOrigin)
		axes.setGridEndX(max.x)
		axes.setGridEndY(max.y)
		axes.setGridStartX(min.x)
		axes.setGridStartY(min.y)
		axes.setPosition(ShapeFactory.createPoint(0.0, 0.0))
		axes
	}


	private def createGrid(origin : PointUnit, min : PointUnit, max : PointUnit, ctx : PSTContext) : IGrid = {
		val grid = ShapeFactory.createGrid(ShapeFactory.createPoint)
		var gridEndX = max.x
		var gridEndY = max.y
		var gridStartX = min.x
		var gridStartY = min.y
		var isGridXLabelInverted = false
		var isGridYLabelInverted = false

		if(gridStartX>=gridEndX) {
			val tmp = gridEndX
			gridEndX = gridStartX
			gridStartX = tmp
			isGridXLabelInverted = true
		}

		if(gridStartY>=gridEndY) {
			val tmp = gridEndY
			gridEndY = gridStartY
			gridStartY = tmp
			isGridYLabelInverted = true
		}

		setStdGridParams(origin, min, max, grid, ctx)
		setShapeGeneralParameters(grid, ctx)
		grid.setPosition(ShapeFactory.createPoint(ctx.pictureSWPt.getX*IShape.PPC, (ctx.pictureSWPt.getY+ctx.pictureNEPt.getY)/2.0*IShape.PPC*(-1.0)))
		grid.setUnit(ctx.unit)
		grid.setGridDots(ctx.gridDots.toInt)
		grid.setGridLabelsColour(ctx.gridlabelcolor)
		grid.setLabelsSize((ctx.gridLabel*IShape.PPC).toInt)
		grid.setGridWidth(scala.math.abs(ctx.gridWidth*IShape.PPC))
		grid.setSubGridColour(ctx.subGridCol)
		grid.setSubGridDiv(ctx.subGridDiv.toInt)
		grid.setSubGridDots(ctx.subGridDots.toInt)
		grid.setSubGridWidth(scala.math.abs(ctx.subGridWidth*IShape.PPC))
		grid.setLineColour(ctx.gridColor)
		grid.setXLabelSouth(!isGridYLabelInverted)
		grid.setYLabelWest(!isGridXLabelInverted)
		grid.setGridEndX(gridEndX)
		grid.setGridEndY(gridEndY)
		grid.setGridStartX(gridStartX)
		grid.setGridStartY(gridStartY)
		grid
	}


	/** Sets the parameters of std grids (axes and grids). */
	private def setStdGridParams(origin : PointUnit, min : PointUnit, max : PointUnit, grid : IStandardGrid, ctx : PSTContext) {
		grid.setLineColour(ctx.gridColor)
		grid.setOriginX(origin.x)
		grid.setOriginY(origin.y)
	}



	private def getApproxCoord(value : Double) : Double = {
		if(value>=0)
			if(value-value.toInt>=0.5)
				value.toInt+1
			else
				value.toInt
		else
			if(value.toInt-value>=0.5)
				value.toInt-1
			else
				value.toInt+1
	}
}
