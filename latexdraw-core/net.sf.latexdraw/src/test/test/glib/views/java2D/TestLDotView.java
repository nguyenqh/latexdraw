package test.glib.views.java2D;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IDot;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;

import org.junit.Before;
import org.junit.Test;

public class TestLDotView extends TestLShapeView {
	@Override
	@Before
	public void setUp() {
		super.setUp();
		view = View2DTK.getFactory().createView(ShapeFactory.createDot(ShapeFactory.createPoint()));
	}


	private IDot getDotShape() {
		return (IDot) view.getShape();
	}

	private void setDot(final double x, final double y, final DotStyle style, final double size) {
		IDot dot = getDotShape();
		dot.setPosition(x, y);
		dot.setDotStyle(style);
		dot.setDiametre(size);
		view.update();
	}

	@Override
	@Test public void testContains1() {
		setDot(100, 200, DotStyle.DOT, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsASTERISK() {
		setDot(100, 200, DotStyle.ASTERISK, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsBAR() {
		setDot(100, 200, DotStyle.BAR, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsOPLUS() {
		setDot(100, 200, DotStyle.OPLUS, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsOTIMES() {
		setDot(100, 200, DotStyle.OTIMES, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsPLUS() {
		setDot(100, 200, DotStyle.PLUS, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsX() {
		setDot(100, 200, DotStyle.X, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsFDIAMOND() {
		setDot(100, 200, DotStyle.FDIAMOND, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsFTRIANGLE() {
		setDot(100, 200, DotStyle.FTRIANGLE, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsFPENTAGON() {
		setDot(100, 200, DotStyle.FPENTAGON, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsDIAMOND() {
		setDot(100, 200, DotStyle.DIAMOND, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsO() {
		setDot(100, 200, DotStyle.O, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsTRIANGLE() {
		setDot(100, 200, DotStyle.TRIANGLE, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsPENTAGON() {
		setDot(100, 200, DotStyle.PENTAGON, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Test public void testContainsSQUARE() {
		setDot(100, 200, DotStyle.SQUARE, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Override
	@Test public void testContains2() {
		setDot(100, 200, DotStyle.FSQUARE, 10);
		assertTrue(view.contains(100, 200));
		assertFalse(view.contains(0, 0));
	}

	@Override
	@Test public void testUpdateDblePathOutside() {
		//TODO
	}

	@Override
	@Test public void testUpdateDblePathInside() {
		//TODO
	}

	@Override
	@Test public void testUpdateDblePathMiddle() {
		//TODO
	}

	@Override
	@Test public void testUpdateGeneralPathInside() {
		//TODO
	}

	@Override
	@Test public void testUpdateGeneralPathMiddle() {
		//TODO
	}

	@Override
	@Test public void testUpdateGeneralPathOutside() {
		//TODO
	}

	@Override
	@Test public void testIntersects() {
		//TODO
	}

}
