package application;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import ai.Config;
import ai.Coup;
import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;

public class ModelController {
	private String chemin;
	public ModelController(String chemin) {
		this.chemin=chemin;
	}
	
	public String[] parseModels() {
		try {
			File f = new File(chemin);
			if(f.exists()) {
				String []fileName = f.list();
				return fileName;
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
		
	}
	
	public void supprimeModel(String model) {
		try {
			File f = new File(chemin+"/"+model);
			if(f.exists())f.delete();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void supprimeModelListe(ArrayList<String> models) {
        for(int i=0;i<models.size();i++) {
            supprimeModel(models.get(i));
        }
    }
	
	public void sauvgardeModel(MultiLayerPerceptron mlp,Config conf) {
		if(mlp != null && conf != null) mlp.save(chemin+"/net_"+conf.numberOfhiddenLayers+"_"+conf.hiddenLayerSize+"_"+conf.learningRate+".srl");
	}
	
	public MultiLayerPerceptron chargementModel(Config conf) {
		return MultiLayerPerceptron.load(chemin+"/net_"+conf.numberOfhiddenLayers+"_"+conf.hiddenLayerSize+"_"+conf.learningRate+".srl");
		
	}
}
