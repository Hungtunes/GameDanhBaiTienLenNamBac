package module;

import gameLogic.TienLenGame;
import soundEffect.ClickSound;

public class AiPlayer extends Player {

	public AiPlayer() {
		super();
	}

	public Deck getCardToPlay(int n) {
		Deck temp = clone();
		temp.shuffleDeck();

		if (this.size() == 1) {
			return this.clone();
		}

		n--;
		while (n > 0 && temp.size() > 0) {
			temp.removeCard(0);
			n--;
		}
		return temp;
	}

	public <T extends TienLenGame> boolean AiPlay(T gameType) {
		Deck temp;
		int m = 1;

		if (gameType.getLastPlay().size() > 0) {
			m = gameType.getLastPlay().size();
		}

		if (m > this.size()) {
			m = this.size();
		}

		int k = 1000;
		while (k > 0) {
			temp = getCardToPlay(m).clone();
			gameType.getSelectionCard().removeDeck();

			for (int l = 0; l < m; l++) {
				try {
					gameType.getSelectionCard().addCard(temp.getCard(l));
				} catch (Exception e) {
					k = 0;
					break;
				}
			}

			if (gameType.isHit()) {
				ClickSound.play();
				gameType.playGame();
				gameType.getSelectionCard().removeDeck();
				return true;
			}

			k--; //
		}
		ClickSound.play();
		gameType.skip();
		return false;
	}
}
