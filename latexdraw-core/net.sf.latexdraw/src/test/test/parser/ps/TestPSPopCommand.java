package test.parser.ps;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
import net.sf.latexdraw.parsers.ps.PSPopCommand;

import org.junit.Test;

public class TestPSPopCommand extends TestPSCommand<PSPopCommand> {
	@Override protected PSPopCommand createCmd() { return new PSPopCommand(); }

	@Override @Test
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(0, dequeue.size());
	}

	@Override @Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.0);
		dequeue.push(-20.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(-10.0,dequeue.peek(),0.0);
		assertEquals(1, dequeue.size());
	}

	@Override @Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(20.0);
		dequeue.push(30.0);
		cmd.execute(dequeue, 0.0);
		assertEquals(20.0,dequeue.peek(),0.0);
		assertEquals(2, dequeue.size());
	}

	@Override @Test(expected=InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
	}
}
