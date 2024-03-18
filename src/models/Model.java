package models;

import java.io.File;

import ai.Config;
import ai.ConfigFileLoader;

public class Model {
	private String configFilePath = "res/config.txt";
	private String modelsDirPath = "res/models/";
	
	public Config[] loadConfig() {
		ConfigFileLoader cfl = new ConfigFileLoader();
		cfl.loadConfigFile(configFilePath);
		Config[] res = {cfl.get("F"), cfl.get("M"), cfl.get("D")};
		return res;
	}
	
	public boolean modelExists(String modelName) {
		return new File(modelsDirPath + modelName).isFile();
	}
}
