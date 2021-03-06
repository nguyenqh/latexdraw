package net.sf.latexdraw.glib.models.interfaces.prop;

import java.util.Arrays;

import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.lang.LangTool;

/**
 * Groups axes' properties.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 */
public interface IAxesProp extends IStdGridProp {
	/** Defines the different kinds of axes. */
	enum AxesStyle {
		AXES {
			@Override public String getPSTToken() {return PSTricksConstants.TOKEN_AXES_STYLE_AXES;}
			@Override public boolean supportsArrows() {return true;}
			@Override public String toString() {return LangTool.INSTANCE.getString18("Axe.1");}//$NON-NLS-1$
		}, FRAME {
			@Override public String getPSTToken() {return PSTricksConstants.TOKEN_AXES_STYLE_FRAME;}
			@Override public boolean supportsArrows() {return false;}
			@Override public String toString() {return LangTool.INSTANCE.getString18("Axe.2");}//$NON-NLS-1$
		}, NONE {
			@Override public String getPSTToken() {return PSTricksConstants.TOKEN_AXES_STYLE_NONE;}
			@Override public boolean supportsArrows() {return false;}
			@Override public String toString() {return "None";}//$NON-NLS-1$
		};

		/**
		 * @return True if the axe style supports arrows.
		 * @since 3.0
		 */
		public abstract boolean supportsArrows();

		/**
		 * @return The PST token corresponding to the axe style.
		 * @since 3.0
		 */
		public abstract String getPSTToken();

		/**
		 * @param style The PST token or the name of the style (e.g. AXES.toString()) corresponding to the style to get.
		 * @return The corresponding style or null.
		 * @since 3.0
		 */
		public static AxesStyle getStyle(final String style) {
			return Arrays.asList(values()).stream().filter(it -> it.toString().equals(style) || it.getPSTToken().equals(style)).findFirst().orElse(null);
		}
	}


	/** Defines the different kinds of ticks. */
	enum TicksStyle {
		FULL {
			@Override public boolean isTop() {return true;}
			@Override public boolean isBottom() {return true;}
			@Override public String getPSTToken() {return PSTricksConstants.TOKEN_TICKS_STYLE_FULL;}
			@Override public String toString() {return LangTool.INSTANCE.getString18("Axe.3");}//$NON-NLS-1$
		}, TOP {
			@Override public boolean isTop() {return true;}
			@Override public boolean isBottom() {return false;}
			@Override public String getPSTToken() {return PSTricksConstants.TOKEN_TICKS_STYLE_TOP;}
			@Override public String toString() {return LangTool.INSTANCE.getString18("Axe.4");}//$NON-NLS-1$
		}, BOTTOM {
			@Override public boolean isTop() {return false;}
			@Override public boolean isBottom() {return true;}
			@Override public String getPSTToken() {return PSTricksConstants.TOKEN_TICKS_STYLE_BOTTOM;}
			@Override public String toString() {return LangTool.INSTANCE.getString18("Axe.5");}//$NON-NLS-1$
		};

		/**
		 * @return The PST token corresponding to the tick style.
		 * @since 3.0
		 */
		public abstract String getPSTToken();

		/**
		 * @return True if the current tick style considers the top ticks.
		 * @since 3.0
		 */
		public abstract boolean isTop();

		/**
		 * @return True if the current tick style considers the bottom ticks.
		 * @since 3.0
		 */
		public abstract boolean isBottom();

		/**
		 * @param style The style to check. Can be the PST token or the name of the style (e.g. FULL.toString()).
		 * @return The corresponding style or null.
		 * @since 3.0
		 */
		public static TicksStyle getStyle(final String style) {
			return Arrays.asList(values()).stream().filter(it -> it.toString().equals(style) || it.getPSTToken().equals(style)).findFirst().orElse(null);
		}
	}


	/** Defines the different style of labels. */
	enum PlottingStyle {
		ALL {
			@Override public boolean isX() {return true;}
			@Override public boolean isY() {return true;}
			@Override public String getPSTToken() {return PSTricksConstants.TOKEN_LABELS_DISPLAYED_ALL;}
		},
		X {
			@Override public boolean isX() {return true;}
			@Override public boolean isY() {return false;}
			@Override public String getPSTToken() {return PSTricksConstants.TOKEN_LABELS_DISPLAYED_X;}
		},
		Y {
			@Override public boolean isX() {return false;}
			@Override public boolean isY() {return true;}
			@Override public String getPSTToken() {return PSTricksConstants.TOKEN_LABELS_DISPLAYED_Y;}
		},
		NONE {
			@Override public boolean isX() {return false;}
			@Override public boolean isY() {return false;}
			@Override public String getPSTToken() {return PSTricksConstants.TOKEN_LABELS_DISPLAYED_NONE;}
		};

		@Override
		public String toString() {
			return getPSTToken();
		}

		/**
		 * @return The PST token corresponding to the labels style.
		 * @since 3.0
		 */
		public abstract String getPSTToken();


		/**
		 * @return True if the current style supports the X-axis.
		 * @since 3.0
		 */
		public abstract boolean isX();

		/**
		 * @return True if the current style supports the Y-axis.
		 * @since 3.0
		 */
		public abstract boolean isY();

		/**
		 * The style that corresponds to the given string.
		 * @param style The style to check.
		 * @return The corresponding style or null.
		 * @since 3.0
		 */
		public static PlottingStyle getStyle(final String style) {
			return Arrays.asList(values()).stream().filter(it -> it.toString().equals(style)).findFirst().orElse(null);
		}
	}

	/**
	 * @return The X increment of the axes.
	 * @since 3.0
	 */
	double getIncrementX();

	/**
	 * @return The Y increment of the axes.
	 * @since 3.0
	 */
	double getIncrementY();

	/**
	 * @return The increments of the axes.
	 * @since 3.0
	 */
	IPoint getIncrement();

	/**
	 * @param increment the X increment to set.
	 */
	void setIncrementX(final double increment);

	/**
	 * @param increment the Y increment to set.
	 */
	void setIncrementY(final double increment);

	/**
	 * @param increment The axes' increment to set.
	 */
	void setIncrement(final IPoint increment);

	/**
	 * @return The distance between the labels of the axes.
	 */
	IPoint getDistLabels();

	/**
	 * @return the distLabels.x.
	 */
	double getDistLabelsX();

	/**
	 * @return the distLabels.y.
	 */
	double getDistLabelsY();

	/**
	 * @param distLabels The distance between the labels of the axes.
	 */
	void setDistLabels(final IPoint distLabels);

	/**
	 * @param distLabelsX the distLabels.x to set.
	 */
	void setDistLabelsX(final double distLabelsX);

	/**
	 * @param distLabelsY the distLabels.y to set.
	 */
	void setDistLabelsY(final double distLabelsY);

	/**
	 * @return the labelsDisplayed.
	 */
	PlottingStyle getLabelsDisplayed();

	/**
	 * @param labelsDisplayed the labelsDisplayed to set.
	 */
	void setLabelsDisplayed(final PlottingStyle labelsDisplayed);

	/**
	 * @return the showOrigin.
	 */
	boolean isShowOrigin();

	/**
	 * @param showOrigin the showOrigin to set.
	 */
	void setShowOrigin(final boolean showOrigin);

	/**
	 * @return the ticksDisplayed.
	 */
	PlottingStyle getTicksDisplayed();

	/**
	 * @param ticksDisplayed the ticksDisplayed to set.
	 */
	void setTicksDisplayed(final PlottingStyle ticksDisplayed);

	/**
	 * @return the ticksStyle.
	 */
	TicksStyle getTicksStyle();

	/**
	 * @param ticksStyle the ticksStyle to set.
	 */
	void setTicksStyle(final TicksStyle ticksStyle);

	/**
	 * @return the ticksSize.
	 */
	double getTicksSize();

	/**
	 * @param ticksSize the ticksSize to set.
	 */
	void setTicksSize(final double ticksSize);

	/**
	 * @return the axesStyle.
	 */
	AxesStyle getAxesStyle();

	/**
	 * @param axesStyle the axesStyle to set.
	 */
	void setAxesStyle(final AxesStyle axesStyle);
}
