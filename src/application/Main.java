package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ai.*;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class Main extends Application {

	// Ratio 16:9
	private static final int WIN_WIDTH = 1200;
	private static final int WIN_HEIGHT = 675;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setResizable(false);
			primaryStage.setTitle("Morpion");
			primaryStage.getIcons().add(new Image("file:../../res/images/icon.jpg"));
			
			// Scène principale
			BorderPane rootMain = (BorderPane) FXMLLoader.load(getClass().getResource("../views/main.fxml"));
			Scene sceneMain = new Scene(rootMain, WIN_WIDTH, WIN_HEIGHT);
			 sceneMain.getStylesheets().add(getClass().getResource("../views/application.css").toExternalForm());
			
			MenuBar menuBar = new MenuBar();
			Menu menu1 = new Menu("IA");
			MenuItem menu1Item1 = new MenuItem("Settings");
			MenuItem menu1Item2 = new MenuItem("Models");
			menu1.getItems().addAll(menu1Item1, menu1Item2);
			menuBar.getMenus().add(menu1);
			rootMain.setTop(menuBar);
			
			// Scène Settings
			VBox rootSettings = FXMLLoader.load(getClass().getResource("../views/dif.fxml"));
			Scene sceneSettings = new Scene(rootSettings, WIN_WIDTH, WIN_HEIGHT);
			sceneSettings.getStylesheets().add(getClass().getResource("../views/application.css").toExternalForm());
			
			// Scène Chargement
			HBox rootLoading = FXMLLoader.load(getClass().getResource("../views/learn.fxml"));
			Scene sceneLoading = new Scene(rootLoading, WIN_WIDTH, WIN_HEIGHT);
			sceneLoading.getStylesheets().add(getClass().getResource("../views/application.css").toExternalForm());
			
			// Scène Modèles
			Pane rootModels = (Pane) FXMLLoader.load(getClass().getResource("../views/models.fxml"));
			Scene sceneModels = new Scene(rootModels, WIN_WIDTH, WIN_HEIGHT);
			sceneModels.getStylesheets().add(getClass().getResource("../views/application.css").toExternalForm());

			// Transitions
			ImageView imageCheckmark = (ImageView) sceneSettings.lookup("#image_checkmark");
			FadeTransition trans = new FadeTransition(Duration.millis(1000), imageCheckmark);
			trans.setToValue(1.0);
			trans.setOnFinished(event -> {
				primaryStage.setScene(sceneMain);
				primaryStage.show();
				imageCheckmark.setOpacity(0.0);
			});
			
			// Event handling
			menu1Item1.setOnAction(event -> {
				primaryStage.setScene(sceneSettings);
				
				// Pré-remplir les textfields
				TextField f_h = (TextField) sceneSettings.lookup("#text_f_h");
				TextField f_l = (TextField) sceneSettings.lookup("#text_f_l");
				TextField f_lr = (TextField) sceneSettings.lookup("#text_f_lr");
				TextField m_h = (TextField) sceneSettings.lookup("#text_m_h");
				TextField m_l = (TextField) sceneSettings.lookup("#text_m_l");
				TextField m_lr = (TextField) sceneSettings.lookup("#text_m_lr");
				TextField d_h = (TextField) sceneSettings.lookup("#text_d_h");
				TextField d_l = (TextField) sceneSettings.lookup("#text_d_l");
				TextField d_lr = (TextField) sceneSettings.lookup("#text_d_lr");

				ConfigFileLoader cfl = new ConfigFileLoader();
				cfl.loadConfigFile("res/config.txt");
				Config config_f = cfl.get("F");
				Config config_m = cfl.get("M");
				Config config_d = cfl.get("D");
				
				f_h.setText(String.valueOf(config_f.hiddenLayerSize));
				f_l.setText(String.valueOf(config_f.numberOfhiddenLayers));
				f_lr.setText(String.valueOf(config_f.learningRate));
				
				m_h.setText(String.valueOf(config_m.hiddenLayerSize));
				m_l.setText(String.valueOf(config_m.numberOfhiddenLayers));
				m_lr.setText(String.valueOf(config_m.learningRate));
				
				d_h.setText(String.valueOf(config_d.hiddenLayerSize));
				d_l.setText(String.valueOf(config_d.numberOfhiddenLayers));
				d_lr.setText(String.valueOf(config_d.learningRate));
				
				primaryStage.show();
			});
			
			menu1Item2.setOnAction(event -> {
				primaryStage.setScene(sceneModels);
				
				VBox content = (VBox) sceneModels.lookup("#vbox_models");
				
				ModelController mc = new ModelController("res/models");
				String[] list = mc.parseModels();
				
				ArrayList<String> selected = new ArrayList<>();
				for (int i = 0; i < list.length; i++) {
					String modelName = list[i];
//					Label fileName = new Label(list[i]);
					CheckBox fileSelectBox = new CheckBox(modelName);
//					fileSelectBox.setId("checkbox_model_" + i);
					fileSelectBox.setOnAction(click -> {
						if (selected.contains(modelName)) {
							selected.remove(modelName);
						} else {
							selected.add(modelName);
						}
					});
					HBox container = new HBox();
					container.setPrefHeight(60);
					container.setPrefWidth(1200);
					container.getChildren().addAll(fileSelectBox);
					content.getChildren().add(container);
				}
				
				Button deleteButton = (Button) sceneModels.lookup("#delete");
				deleteButton.setOnAction(e -> {
					mc.supprimeModelListe(selected);
					// Reconstruire la scène OU 
				});
			});
			
			Button buttonSave = (Button) sceneSettings.lookup("#button_save");
			buttonSave.setOnAction(event -> {
				// Sauvegarder les paramètres de difficulté
				TextField f_h = (TextField) sceneSettings.lookup("#text_f_h");
				TextField f_l = (TextField) sceneSettings.lookup("#text_f_l");
				TextField f_lr = (TextField) sceneSettings.lookup("#text_f_lr");
				TextField m_h = (TextField) sceneSettings.lookup("#text_m_h");
				TextField m_l = (TextField) sceneSettings.lookup("#text_m_l");
				TextField m_lr = (TextField) sceneSettings.lookup("#text_m_lr");
				TextField d_h = (TextField) sceneSettings.lookup("#text_d_h");
				TextField d_l = (TextField) sceneSettings.lookup("#text_d_l");
				TextField d_lr = (TextField) sceneSettings.lookup("#text_d_lr");
				
				ConfigFileLoader cfl = new ConfigFileLoader();
				Config config_f = new Config("F", Integer.valueOf(f_h.getText()), Integer.valueOf(f_l.getText()), Double.valueOf(f_lr.getText()));
				Config config_m = new Config("M", Integer.valueOf(m_h.getText()), Integer.valueOf(m_l.getText()), Double.valueOf(m_lr.getText()));
				Config config_d = new Config("D", Integer.valueOf(d_h.getText()), Integer.valueOf(d_l.getText()), Double.valueOf(d_lr.getText()));
				// TODO valeur nulle ?
				
				Config[] conf = {config_f, config_m, config_d};
				cfl.writeConfigFile("res/config.txt", conf);
				
				trans.play();
			});
			
			Button buttonGo = (Button) sceneLoading.lookup("#button_go");
			Button buttonHumanVSAI = (Button) sceneMain.lookup("#button_hvsia");
			buttonHumanVSAI.setOnAction(event -> {
				primaryStage.setScene(sceneLoading);
				
				// Taille des couches d'entrée et sortie
				int size = 9;
				// Nombre de couches cachées
				int l = 2;
				// Nombre de neurones dans les couches cachées
				int h = 128;
				// Taux d'apprentissage du modèle
				double lr = 0.1;
				// Tableau des couches du réseau de neurones
				int[] layers = new int[l+2];
				layers[0] = size ;
				for (int i = 0; i < l; i++) {
					layers[i+1] = h ;
				}
				layers[layers.length-1] = size;
				// Itérations de l'algorithme d'entraîment du modèle
				double epochs = 100000;
				// Données d'entraînement
				HashMap<Integer, Coup> mapTrain = ai.Test.loadCoupsFromFile("./res/train_dev_test/train.txt");
				
				Task<Double> task = new Task<Double>() {
					@Override
					protected Double call() throws Exception {
						// RÉSEAU DE NEURONE "CRU"
						MultiLayerPerceptron net = new MultiLayerPerceptron(layers, lr, new SigmoidalTransferFunction());
						
						// Erreur d'entraînement
						double error = 0.0, prevError = 0.0 ;
						
						// ENTRAÎNEMENT DU MODÈLE
						for(int i = 0; i < epochs; i++) {
							Coup c = null;
							while (c == null)
								c = mapTrain.get((int)(Math.round(Math.random() * mapTrain.size())));

							prevError = error;
							error += net.backPropagate(c.in, c.out);

							if (prevError < error) updateMessage("Error at step "+i+" is "+ (error/(double)i));
							updateProgress(i, epochs);
						}
						
						// Fin du chargement
						updateMessage("Learning complete!");
						buttonGo.setStyle("-fx-font-weight: bold; -fx-text-fill: green; -fx-font-size: 30px");
						
						// save the model
						return error;
					}
				};
				
				ProgressBar bar = (ProgressBar) sceneLoading.lookup("#bar_loading");
				bar.progressProperty().bind(task.progressProperty());
				
				TextField textLoading = (TextField) sceneLoading.lookup("#text_loading");
//				textLoading.textProperty().bind(task.messageProperty());
				task.messageProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						textLoading.setText(newValue);
					}
				});
				
				// Exécution de la task dans un autre thread.
				Thread learnThread = new Thread(task);
				learnThread.start();
			});
			
			// Display home page
			primaryStage.setScene(sceneMain);
			primaryStage.show();
			
		} catch(Exception error) {
			error.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

/*
 * 
 * Prochaine séance:
 * 
 * Delete des modèles
 * Balsamiq -> 
 * 		on va garder ça, on va garder ça... 
 * 		dans quelle mesure on va pouvoir implémenter ça...
 * 		discussion de l'ergonomie finale
 * 
 * Paradigme MVC
 * 
*/
