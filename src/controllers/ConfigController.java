package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import ai.Config;
import ai.ConfigFileLoader;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Model;
public class ConfigController {

	public void initialize() {
		Model mdl = new Model();
		Config[] cfg = mdl.loadConfig();
		
		text_f_h.setText(String.valueOf(cfg[0].hiddenLayerSize));
		text_f_l.setText(String.valueOf(cfg[0].numberOfhiddenLayers));
		text_f_lr.setText(String.valueOf(cfg[0].learningRate));
		
		text_m_h.setText(String.valueOf(cfg[1].hiddenLayerSize));
		text_m_l.setText(String.valueOf(cfg[1].numberOfhiddenLayers));
		text_m_lr.setText(String.valueOf(cfg[1].learningRate));
		
		text_d_h.setText(String.valueOf(cfg[2].hiddenLayerSize));
		text_d_l.setText(String.valueOf(cfg[2].numberOfhiddenLayers));
		text_d_lr.setText(String.valueOf(cfg[2].learningRate));
		
		ImageView imageCheckmark = image_checkmark;
		FadeTransition trans = new FadeTransition(Duration.millis(1000), imageCheckmark);
		trans.setToValue(1.0);
		trans.setOnFinished(event -> {
			imageCheckmark.setOpacity(0.0);
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/main.fxml"));
				Parent root = loader.load();
				Scene scn = new Scene(root);
				Stage stg = (Stage)button_save.getScene().getWindow();
				stg.setScene(scn);
				stg.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		button_save.setOnAction(event -> {
			// Sauvegarder les paramètres de difficulté
			TextField f_h = text_f_h;
			TextField f_l = text_f_l;
			TextField f_lr = text_f_lr;
			TextField m_h = text_m_h;
			TextField m_l = text_m_l;
			TextField m_lr = text_m_lr;
			TextField d_h = text_d_h;
			TextField d_l = text_d_l;
			TextField d_lr = text_d_lr;
			
			ConfigFileLoader cfl = new ConfigFileLoader();
			Config config_f = new Config("F", Integer.valueOf(f_h.getText()), Integer.valueOf(f_l.getText()), Double.valueOf(f_lr.getText()));
			Config config_m = new Config("M", Integer.valueOf(m_h.getText()), Integer.valueOf(m_l.getText()), Double.valueOf(m_lr.getText()));
			Config config_d = new Config("D", Integer.valueOf(d_h.getText()), Integer.valueOf(d_l.getText()), Double.valueOf(d_lr.getText()));

			
			Config[] conf = {config_f, config_m, config_d};
			cfl.writeConfigFile("res/config.txt", conf);
			
			trans.play();
		});
		button_retour.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/main.fxml"));
				Parent root = loader.load();
				Scene scn = new Scene(root);
				Stage stg = (Stage)button_save.getScene().getWindow();
				stg.setScene(scn);
				stg.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	@FXML
	private ImageView image_checkmark;
	
	@FXML
	private Button button_save;
	@FXML
	private Button button_retour;
	
	@FXML
	private TextField text_f_h;
	@FXML
	private TextField text_f_l;
	@FXML
	private TextField text_f_lr;
	
	@FXML
	private TextField text_m_h;
	@FXML
	private TextField text_m_l;
	@FXML
	private TextField text_m_lr;
	
	@FXML
	private TextField text_d_h;
	@FXML
	private TextField text_d_l;
	@FXML
	private TextField text_d_lr;
	

}
