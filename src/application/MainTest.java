package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainTest extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			
			primaryStage.setResizable(false);
			primaryStage.setTitle("Morpion");
			primaryStage.getIcons().add(new Image("file:../res/images/icon.jpg"));
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/main.fxml"));
			Parent root = loader.load();
			Scene s = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
			primaryStage.setScene(s);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// Ratio 16:9
	private static final int WIN_WIDTH = 1200;
	private static final int WIN_HEIGHT = 675;

}

// FXMLLoader prend un chemin à partir du fichier Java
// File prend un chemin depuis src
