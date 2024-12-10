package imageAction;

import gameScene.GameSeclectScene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import soundEffect.ClickSound;

public class StartButton {
	public static ImageView create(Stage primaryStage) {
		try {
			ImageView startButton = new ImageView(
					StartButton.class.getResource("/resources/card/startButton.png").toExternalForm());
			startButton.setFitWidth(150);
			startButton.setFitHeight(150);
			startButton.setOnMouseClicked(e -> {
				ClickSound.play();
				primaryStage.setScene(GameSeclectScene.create(primaryStage));
			});
			return startButton;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
