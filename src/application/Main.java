package application;

import imageAction.BackgroundImageView;
import imageAction.StartButton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import soundEffect.BackgroundMusic;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Tạo màn hình chính
		StackPane root = new StackPane();
		root.setPrefSize(1200, 700);

		// Ảnh nền cho màn hình chính
		root.setBackground(BackgroundImageView.set());

		// Nhạc nền
		BackgroundMusic.play();

		// Nút bắt đầu
		root.getChildren().add(StartButton.create(primaryStage));

		// Tạo cảnh chính
		Scene mainScene = new Scene(root, 1200, 700);

		// Cài đặt cho Stage
		primaryStage.setTitle("Game Bài Nhóm 5");
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	// run
	public static void main(String[] args) {
		launch(args);
	}
}