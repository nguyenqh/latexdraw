package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGPatternElement;

import org.junit.Test;

public class TestSVGPatternElement extends AbstractTestSVGElement {
	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testContructorFail() throws MalformedSVGDocument {
		new SVGPatternElement(null, null);
	}

	@SuppressWarnings("unused")
	@Test
	public void testContructorOK() throws MalformedSVGDocument {
		new SVGPatternElement(node, null);
	}



	@Test
	public void testGetHeight() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(e.getHeight(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		e = new SVGPatternElement(node, null);
		assertEquals(e.getHeight(), 20., 0.0001);
	}



	@Test
	public void testGetWidth() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(e.getWidth(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_WIDTH, "30"); //$NON-NLS-1$
		e = new SVGPatternElement(node, null);
		assertEquals(e.getWidth(), 30., 0.0001);
	}



	@Test
	public void testGetPatternUnits() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(e.getPatternUnits(), SVGAttributes.SVG_UNITS_VALUE_OBJ);

		node.setAttribute(SVGAttributes.SVG_PATTERN_UNITS, SVGAttributes.SVG_UNITS_VALUE_USR);
		e = new SVGPatternElement(node, null);
		assertEquals(e.getPatternUnits(), SVGAttributes.SVG_UNITS_VALUE_USR);

		node.setAttribute(SVGAttributes.SVG_PATTERN_UNITS, SVGAttributes.SVG_UNITS_VALUE_OBJ);
		e = new SVGPatternElement(node, null);
		assertEquals(e.getPatternUnits(), SVGAttributes.SVG_UNITS_VALUE_OBJ);
	}



	@Test
	public void testGetPatternContentUnits() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(e.getPatternContentUnits(), SVGAttributes.SVG_UNITS_VALUE_USR);

		node.setAttribute(SVGAttributes.SVG_PATTERN_CONTENTS_UNITS, SVGAttributes.SVG_UNITS_VALUE_USR);
		e = new SVGPatternElement(node, null);
		assertEquals(e.getPatternContentUnits(), SVGAttributes.SVG_UNITS_VALUE_USR);

		node.setAttribute(SVGAttributes.SVG_PATTERN_CONTENTS_UNITS, SVGAttributes.SVG_UNITS_VALUE_OBJ);
		e = new SVGPatternElement(node, null);
		assertEquals(e.getPatternContentUnits(), SVGAttributes.SVG_UNITS_VALUE_OBJ);
	}



	@Test
	public void testEnableRendering() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0"); //$NON-NLS-1$
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0"); //$NON-NLS-1$
		e = new SVGPatternElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "0"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10"); //$NON-NLS-1$
		e = new SVGPatternElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10"); //$NON-NLS-1$
		e = new SVGPatternElement(node, null);
		assertTrue(e.enableRendering());
	}



	@Test
	public void testGetY() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(e.getY(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_Y, "1"); //$NON-NLS-1$
		e = new SVGPatternElement(node, null);
		assertEquals(1., e.getY(), 0.0001);
	}



	@Test
	public void testGetX() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(e.getX(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_X, "2"); //$NON-NLS-1$
		e = new SVGPatternElement(node, null);
		assertEquals(2., e.getX(), 0.0001);
	}


	@Override
	public String getNameNode() {
		return SVGElements.SVG_PATTERN;
	}
}
