package com.wacajou.init;

import java.io.IOException;
import java.io.InputStream;

public class ExecuteCommand {
	public ExecuteCommand(String cmd) {
		final String dosCommand = cmd;
		try {
			final Process process = Runtime.getRuntime().exec(
					dosCommand );
			final InputStream in = process.getInputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				System.out.print((char) ch);
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}

