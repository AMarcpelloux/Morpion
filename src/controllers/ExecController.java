package controllers;

import java.io.IOException;

public class ExecController {

	public static void main(String[] args) {
		try {
			Runtime runTime = Runtime.getRuntime();
			Process process = runTime.exec("pwd");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}