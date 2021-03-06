package net.sf.latexdraw.util;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;

import net.sf.latexdraw.badaboom.BadaboomCollector;

/**
 * Defines some routines that provides information about the operating system currently used.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 05/14/10<br>
 * @author Arnaud BLOUIN, Jan-Cornelius MOLNAR
 * @version 3.0
 */
public final class LSystem {
	/**
	 * The different operating systems managed.
	 */
	public enum OperatingSystem {
		/** Vista */
		VISTA, 
		/** XP */
		XP,
		/** Windows 7 */
		SEVEN, 
		/** Windows 8 */
		EIGHT, 
		/** Windows 10 */
		TEN,
		/** Mac OS X */
		MAC_OS_X {
			@Override
			public String getPS2EPSBinPath() {
				return "/usr/local/bin/ps2epsi"; //$NON-NLS-1$
			}

			@Override
			public String getLatexBinPath() {
				return "/usr/texbin/latex"; //$NON-NLS-1$
			}

			@Override
			public String getDvipsBinPath() {
				return "/usr/texbin/dvips"; //$NON-NLS-1$
			}

			@Override
			public String getPs2pdfBinPath() {
				return "/usr/local/bin/ps2pdf"; //$NON-NLS-1$
			}

			@Override
			public String getPdfcropBinPath() {
				return "pdfcrop"; //$NON-NLS-1$
			}
		},
		/** Linux */
		LINUX;

		/**
		 * @return The path where is the ps2eps binary.
		 * @since 3.1
		 */
		public String getPS2EPSBinPath() {
			return "ps2epsi"; //$NON-NLS-1$
		}

		/**
		 * @return The path where is the latex binary.
		 * @since 3.0
		 */
		public String getLatexBinPath() {
			return "latex"; //$NON-NLS-1$
		}

		/**
		 * @return The path where is the dvips binary.
		 * @since 3.0
		 */
		public String getDvipsBinPath() {
			return "dvips"; //$NON-NLS-1$
		}

		/**
		 * @return The path where is the ps2pdf binary.
		 * @since 3.0
		 */
		public String getPs2pdfBinPath() {
			return "ps2pdf"; //$NON-NLS-1$
		}

		/**
		 * @return The path where is the pdfcrop binary.
		 * @since 3.0
		 */
		public String getPdfcropBinPath() {
			return "pdfcrop"; //$NON-NLS-1$
		}
	}


	/** The singleton. */
	public static final LSystem INSTANCE = new LSystem();

	/**
	 * Creates the singleton.
	 */
	private LSystem() {
		super();
	}


	/**
	 * @return True: the operating system currently used is Windows.
	 * @since 3.0
	 */
	public boolean isWindows() {
		return isSeven() || isVista() || isXP() || is8() || is10();
	}

	/**
	 * @return True: the operating system currently used is Windows 10.
	 */
	public boolean is10() {
		return getSystem()==OperatingSystem.TEN;
	}

	/**
	 * @return True: the operating system currently used is Windows 8.
	 * @since 3.0
	 */
	public boolean is8() {
		return getSystem()==OperatingSystem.EIGHT;
	}

	/**
	 * @return True: the operating system currently used is Vista.
	 * @since 3.0
	 */
	public boolean isVista() {
		return getSystem()==OperatingSystem.VISTA;
	}


	/**
	 * @return True: the operating system currently used is XP.
	 * @since 3.0
	 */
	public boolean isXP() {
		return getSystem()==OperatingSystem.XP;
	}


	/**
	 * @return True: the operating system currently used is Seven.
	 * @since 3.0
	 */
	public boolean isSeven() {
		return getSystem()==OperatingSystem.SEVEN;
	}


	/**
	 * @return True: the operating system currently used is Linux.
	 * @since 3.0
	 */
	public boolean isLinux() {
		return getSystem()==OperatingSystem.LINUX;
	}


	/**
	 * @return True: the operating system currently used is Mac OS X.
	 * @since 3.0
	 */
	public boolean isMacOSX() {
		return getSystem()==OperatingSystem.MAC_OS_X;
	}


	/**
	 * @return The control modifier used by the currently used operating system.
	 * @since 3.0
	 */
	public int getControlKey() {
		if(LSystem.INSTANCE.isMacOSX())
			return KeyEvent.VK_META;
		return KeyEvent.VK_CONTROL;
	}


	/**
	 * @return The name of the operating system currently used.
	 * @since 3.0
	 */
	public OperatingSystem getSystem() {
		final String os = System.getProperty("os.name"); //$NON-NLS-1$

		if("linux".equalsIgnoreCase(os)) //$NON-NLS-1$
			return OperatingSystem.LINUX;

		if("windows 7".equalsIgnoreCase(os)) //$NON-NLS-1$
			return OperatingSystem.SEVEN;

		if("windows vista".equalsIgnoreCase(os)) //$NON-NLS-1$
			return OperatingSystem.VISTA;

		if("windows xp".equalsIgnoreCase(os)) //$NON-NLS-1$
			return OperatingSystem.XP;

		if("mac os x".equalsIgnoreCase(os)) //$NON-NLS-1$
			return OperatingSystem.MAC_OS_X;

		if(os.toLowerCase().contains("windows 8")) //$NON-NLS-1$
			return OperatingSystem.EIGHT;

		if(os.toLowerCase().contains("windows 10")) //$NON-NLS-1$
			return OperatingSystem.TEN;

		BadaboomCollector.INSTANCE.add(new IllegalArgumentException("This OS is not supported: " + os)); //$NON-NLS-1$

		return null;
	}


	/**
	 * @return The version of the current LaTeX.
	 * @since 3.1
	 */
	public String getLaTeXVersion() {
		return execute(new String[]{getSystem().getLatexBinPath(), "--version"}, null); //$NON-NLS-1$
	}

	/**
	 * @return The version of the current dvips.
	 * @since 3.1
	 */
	public String getDVIPSVersion() {
		return execute(new String[]{getSystem().getDvipsBinPath(), "--version"}, null); //$NON-NLS-1$
	}

	/**
	 * @return The version of the current ps2pdf.
	 * @since 3.1
	 */
	public String getPS2PDFVersion() {
		return execute(new String[]{getSystem().getPs2pdfBinPath()}, null);
	}

	/**
	 * @return The version of the current ps2eps.
	 * @since 3.1
	 */
	public String getPS2EPSVersion() {
		return execute(new String[]{getSystem().getPS2EPSBinPath(), "--version"}, null); //$NON-NLS-1$
	}

	/**
	 * @return The version of the current pdfcrop.
	 * @since 3.1
	 */
	public String getPDFCROPVersion() {
		return execute(new String[]{getSystem().getPdfcropBinPath(), "--version"}, null); //$NON-NLS-1$
	}


	/**
	 * Executes a command.
	 * @param cmd The execution command
	 * @param tmpdir The working dir
	 * @return The log.
	 * @since 3.1
	 */
	public String execute(final String[] cmd, final File tmpdir) {
		if(cmd==null || cmd.length==0)
			return null;

		try {
			final Process process 	 = Runtime.getRuntime().exec(cmd, null, tmpdir);  // Command launched
			final StreamExecReader err = new StreamExecReader(process.getErrorStream());// Catch the error log
			final StreamExecReader inp = new StreamExecReader(process.getInputStream());// Catch the log

			err.start();
			inp.start();

			process.waitFor();// Waiting for the end of the process.

			return err.getLog() + LResources.EOL + inp.getLog();
		}catch(final Exception e) {return "ERR while execute the command : " + Arrays.toString(cmd) + ": " + e.getMessage();} //$NON-NLS-1$ //$NON-NLS-2$
	}
}
