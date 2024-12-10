package gameScene;

import java.io.IOException;
import java.util.List;

import controller.GameSceneController;
import gameLogic.TienLenGame;
import imageAction.BackOfCard;
import imageAction.BackgroundImageView;
import imageAction.CardImg;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import module.AiPlayer;
import module.Card;
import module.Deck;
import module.Player;
import module.Player.PlayerState;
import soundEffect.ClickSound;

public class GameMainScene<T extends TienLenGame> {
	private T gameType;
	private StackPane centerPane;
	private StackPane bottomPane;
	private StackPane topPane;
	private StackPane leftPane;
	private StackPane rightPane;
	private HBox buttonBox1;

	// Constructor sử dụng kiểu T
	public GameMainScene(T gameType) {
		this.gameType = gameType;
	}

	public Scene createScene() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameScene/GameMainScene.fxml"));
			Parent root = loader.load();
			GameSceneController controller = loader.getController();
			((Pane) root).setBackground(BackgroundImageView.set());

			// Khởi tạo các pane
			centerPane = controller.getCenterPane();
			bottomPane = controller.getBottomPane();
			topPane = controller.getTopPane();
			leftPane = controller.getLeftPane();
			rightPane = controller.getRightPane();

			// Cài đặt các phần tử giao diện
			bottomPane.setPadding(new Insets(10, 0, 10, 0));
			topPane.setPadding(new Insets(10, 0, 10, 0));
			leftPane.setPadding(new Insets(80, 150, 80, 150));
			rightPane.setPadding(new Insets(80, 150, 80, 150));

			// Tạo nút và các thành phần giao diện khác
			createActionButtons();
			createCenterPack();

			return new Scene(root, 1200, 700);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void createActionButtons() {
		// Tạo nút "Hit" và "Skip"
		Button hitButton = new Button("Hit");
		Button skipButton = new Button("Skip");

		// Thiết lập kích thước và kiểu dáng cho các nút
		hitButton.setPrefSize(150, 40);
		skipButton.setPrefSize(150, 40);

		String buttonStyle = """
				    -fx-background-color: linear-gradient(to right, #4CAF50, #81C784);
				    -fx-text-fill: white;
				    -fx-font-size: 16px;
				    -fx-font-weight: bold;
				    -fx-background-radius: 20;
				    -fx-border-radius: 20;
				    -fx-border-color: white;
				    -fx-border-width: 2;
				""";

		hitButton.setStyle(buttonStyle);
		skipButton.setStyle(buttonStyle);

		// Tạo HBox để chứa các nút (chỉ tạo 1 lần)
		buttonBox1 = new HBox(20); // Khoảng cách giữa các nút
		buttonBox1.getChildren().addAll(skipButton, hitButton);
		buttonBox1.setPadding(new Insets(10, 0, 10, 0)); // Tăng khoảng cách trên dưới để nút không sát mép

		// Thêm hành động cho các nút
		hitButton.setOnAction(e -> {
			if (gameType.isHit()) {
				hit();
			}
		});

		skipButton.setOnAction(e -> {
			skip();
		});

		// Thêm HBox vào centerPane
		centerPane.getChildren().add(buttonBox1);
	}

	private void hit() {
		ClickSound.play();
		gameType.playGame();

		if (gameType.isTheEnd()) {
			endGame();
		} else {
			// Xóa bộ bài đã chọn và cập nhật scene chính
			gameType.getSelectionCard().removeDeck();
			updateScene(centerPane, bottomPane, topPane, leftPane, rightPane); // Cập nhật lại scene chơi chính
		}
	}

	private void skip() {
		ClickSound.play();
		gameType.skip();
		updateScene(centerPane, bottomPane, topPane, leftPane, rightPane);
	}

	private void createCenterPack() {
		ImageView cardImage = BackOfCard.create(0, 0);
		cardImage.setOnMouseClicked(e -> {
			ClickSound.play();
			updateScene(centerPane, bottomPane, topPane, leftPane, rightPane);
		});
		centerPane.getChildren().add(cardImage);
	}

	public void updateScene(StackPane centerPane, StackPane bottomPane, StackPane topPane, StackPane leftPane,
			StackPane rightPane) {

		playerCardShow();

		lastPlayCardShow();

		ifAiTurn();

		createActionButtons();
	}

	private void playerCardShow() {
		// Lấy danh sách bài đã lưu
		List<Player> player = gameType.getPlayer();

		// Danh sách các vị trí đích
		StackPane[] destinations = { bottomPane, rightPane, topPane, leftPane };

		// Vòng lặp hiển thị bài của các người chơi
		for (int i = 0; i < player.size(); i++) {
			destinations[i].getChildren().clear();
			for (int j = 0; j < player.get(i).size(); j++) {
				Card card = player.get(i).getCards().get(j);
				int size = player.get(i).size();
				if (i == gameType.getNowPlayerNum()) {
					while (gameType.getNowPlayer().getPlayerState() != PlayerState.TRONG_VONG) {
						gameType.nextPlayer();
					}
					if (gameType.getNowPlayer() != null && gameType.getNowPlayer().getCards() != null) {
						if (gameType.getNowPlayer() instanceof AiPlayer) {
							destinations[i].getChildren().add(BackOfCard.create(j, size));
						} else {
							ImageView cardImg = CardImg.create(j, size, card);
							cardImg.setOnMouseClicked(e -> {
								ClickSound.play();
								if (gameType.getSelectionCard().getCards().contains(card)) {
									gameType.deselectCard(card);
									cardImg.setTranslateY(0);
								} else {
									gameType.setSelectionCard(card);
									cardImg.setTranslateY(-10);
								}
							});

							destinations[i].getChildren().add(cardImg);
						}
					}
				} else {
					destinations[i].getChildren().add(BackOfCard.create(j, size));
				}
			}
		}
	}

	private void lastPlayCardShow() {
		Deck lastPlay = gameType.getLastPlay();
		centerPane.getChildren().clear();
		for (int i = 0; i < lastPlay.size(); i++) {
			centerPane.getChildren().add(CardImg.create(i, lastPlay.size(), lastPlay.getCard(i)));
		}
	}

	private void ifAiTurn() {
		if (gameType.getNowPlayer() instanceof AiPlayer) {
			PauseTransition pause = new PauseTransition();
			pause.setOnFinished(e -> {
				int k = 0;
				AiPlayer tempBot = (AiPlayer) gameType.getNowPlayer();
				if (tempBot.AiPlay(gameType)) {
					if (gameType.isTheEnd()) {
						k = 1;
						endGame();
					}

				}
				if (k == 0) {
					updateScene(centerPane, bottomPane, topPane, leftPane, rightPane);
				}
			});
			pause.play();
		}
	}

	private void endGame() {
		lastPlayCardShow();
		String temp = "Thứ tự xếp hạng chiến thắng: ";
		temp += gameType.rankToString();
		Label winnerLabel = new Label(temp);
		winnerLabel.setFont(Font.font("Arial", 20));
		winnerLabel.setTextFill(Color.WHITE);
		Button newGame = new Button("New Game");
		newGame.setStyle("-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); "
				+ "-fx-text-fill: white; " + "-fx-font-size: 16px; " + "-fx-font-weight: bold; "
				+ "-fx-padding: 10 20 10 20; " + "-fx-background-radius: 15; " + "-fx-border-radius: 15; "
				+ "-fx-border-color: #1B5E20; " + "-fx-border-width: 2px;");
		newGame.setOnAction(e -> {
			updateScene(centerPane, bottomPane, topPane, leftPane, rightPane);
		});
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.getChildren().addAll(winnerLabel, newGame);
		centerPane.getChildren().addAll(vbox);
		gameType.resetGame();
	}
}