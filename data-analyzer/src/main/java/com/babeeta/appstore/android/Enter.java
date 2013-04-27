package com.babeeta.appstore.android;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Enter {

	private static final Logger logger = LoggerFactory
			.getLogger(Enter.class);

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Need args[0]:jobs dir");
			return;
		}
		Enter enter;
		try {
			enter = new Enter();
			enter.go(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void go(String jobHomePath) throws IOException {
		// TODO build all jobs ,by args[0]
		// TODO start these jobs
	}
}
