package controllers;

import java.io.IOException;

import ai.Config;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.Model;

public class HomeController {
	
	@FXML
	private MenuItem menu_settings;
	
	@FXML
	private MenuItem menu_models;
	
	@FXML
	private Button button_hvsh;
	
	@FXML
	private Button button_hvsia;
	
	@FXML
	private ToggleGroup dif;
	
	Model model = new Model();
	
	public void initialize() {
		// Paramètres de configuration de la difficulté.
		menu_settings.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/dif.fxml"));
				Parent root;
				root = loader.load();
				Scene s = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
				Stage primaryStage = (Stage) button_hvsh.getScene().getWindow();
				primaryStage.setScene(s);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// Paramètres de gestion des modèles.
		menu_models.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/models.fxml"));
				Parent root;
				root = loader.load();
				Scene s = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
				Stage primaryStage = (Stage) button_hvsh.getScene().getWindow();
				primaryStage.setScene(s);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		// Humain contre Humain.
		button_hvsh.setOnAction(event -> {
			// Lancer la partie directement.
			// ...
		});
		
		// Humain contre IA.
		button_hvsia.setOnAction(event -> {
			try {
				/// Si le modèle n'existe pas, lancer l'apprentissage.
				
				// Récupérer la difficulté choisie.
				RadioButton selected = (RadioButton) dif.getSelectedToggle();
				String chosenDifficulty = selected.getText();
				int index;
				switch (chosenDifficulty) {
				case "Facile":
					index = 0;
					break;
				case "Moyen":
					index = 1;
					break;
				case "Difficile":
					index = 2;
					break;
				default:
					index = 0;
				}
				
				// Vérifier l'existance d'un modèle de même configuration.
				Config[] settings = model.loadConfig();
				Config currentConfig = settings[index];
				String modelName = "net_" + 
						currentConfig.numberOfhiddenLayers + "_" + 
						currentConfig.hiddenLayerSize + "_" +
						currentConfig.learningRate + ".srl";

				if (model.modelExists(modelName)) {
					// Lancer la partie.
				} else {
					// Lancer l'apprentissage.
				}
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/learn.fxml"));
				Parent root;
				root = loader.load();
				Scene s = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
				Stage primaryStage = (Stage) button_hvsh.getScene().getWindow();
				primaryStage.setScene(s);
				primaryStage.show();
				
				// Lancer la partie
				// ...
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	// Ratio 16:9
	private static final int WIN_WIDTH = 1200;
	private static final int WIN_HEIGHT = 675;
}
