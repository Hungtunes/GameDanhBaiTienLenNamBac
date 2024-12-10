package gameLogic;

import module.Card;
import module.Deck;

public class TienLenMienBac extends TienLenGame {
	public TienLenMienBac(int numOfPlayer, int numOfAIPlayer) {
		super(numOfPlayer, numOfAIPlayer);
	}

	public PlayType checkTypePlay(Deck deck) {
		if (deck.size() == 1) {
			return PlayType.COC;
		} else if (deck.size() == 2) {
			Card card1 = deck.getCard(0);
			Card card2 = deck.getCard(1);
			if ((card1.getValueValue() == card2.getValueValue()) && (color(card1) == color(card2))) {
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
		} else {
			return checkSanh(deck);
		}
		return PlayType.NULL;
	}

	public int color(Card card) {
		return (card.getTypeValue() - 1) / 2;
	}

	public PlayType checkSanh(Deck deck) {
		deck.sortDeck();
		if (deck.size() == 0 || deck.getCard(deck.size() - 1).getValueValue() == 15)
			return PlayType.NULL;
		if (deck.size() < 3) {
			return PlayType.NULL;
		}
		for (int i = 0; i < deck.size() - 1; i++) {
			if (((deck.getCard(i + 1).getValueValue() - deck.getCard(i).getValueValue()) != 1)
					|| (deck.getCard(i + 1).getTypeValue() - deck.getCard(i).getTypeValue()) != 0) {
				return PlayType.NULL;
			}
		}
		return PlayType.SANH;
	}
}
