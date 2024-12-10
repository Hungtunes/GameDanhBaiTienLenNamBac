package gameLogic;

import module.Deck;

public class TienLenMienNam extends TienLenGame {

	public TienLenMienNam(int numOfPlayer, int numOfAIPlayer) {
		super(numOfPlayer, numOfAIPlayer);
	}

	// Check các lá bài người chơi lấy ra thuộc kiểu chơi nào
	public PlayType checkTypePlay(Deck deck) {
		if (deck.size() == 1) {
			return PlayType.COC;
		} else if (deck.size() == 2) {
			if (checkDoi(deck.getCard(0), deck.getCard(1))) {
				return PlayType.DOI;
			}
		} else if (deck.size() == 3) {
			if (checkDoi(deck.getCard(0), deck.getCard(1))) {
				if (checkDoi(deck.getCard(0), deck.getCard(2))) {
					return PlayType.BA;
				}
			} else {
				return checkSanh(deck);
			}
		} else if (deck.size() == 4) {
			if (checkDoi(deck.getCard(0), deck.getCard(1))) {
				if (checkDoi(deck.getCard(0), deck.getCard(2))) {
					if (checkDoi(deck.getCard(0), deck.getCard(3))) {
						return PlayType.TU;
					}
				}
			} else {
				return checkSanh(deck);
			}
		} else if (deck.size() == 0) {
			return PlayType.NULL;
		} else if (deck.size() % 2 == 0 && checkSanh(deck) != PlayType.SANH) {// da sua

			// check doi thong nhung quen khong check sanh truoc->da sua
			for (int i = 0; i < deck.size(); i = i + 2) {
				if (!checkDoi(deck.getCard(i), deck.getCard(i + 1))) {
					return PlayType.NULL;
				}
			}
			Deck temp = new Deck();
			for (int i = 0; i < deck.size(); i = i + 2) {
				temp.addCard(deck.getCard(i));
			}
			if (checkSanh(temp) == PlayType.SANH) {
				return PlayType.DOI_THONG;
			}
		} else {
			return checkSanh(deck);
		}
		return PlayType.NULL;
	}

	// Check xem có là sảnh
	public PlayType checkSanh(Deck deck) {
		deck.sortDeck();
		if (deck.size() == 0 || deck.getCard(deck.size() - 1).getValueValue() == 15)
			return PlayType.NULL;
		if (deck.size() < 3) {
			return PlayType.NULL;
		}
		for (int i = 0; i < deck.size() - 1; i++) {
			if ((deck.getCard(i + 1).getValueValue() - deck.getCard(i).getValueValue()) != 1) {
				return PlayType.NULL;
			}
		}
		return PlayType.SANH;
	}
}