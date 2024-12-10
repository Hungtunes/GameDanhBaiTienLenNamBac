package module;

public class Card {
	// Khai báo 2 enum chứa giá trị quân bài và chất
	public enum Value {
		AT("A"), HAI("2"), BA("3"), BON("4"), NAM("5"), SAU("6"), BAY("7"), TAM("8"), CHIN("9"), MUOI("10"), RI("J"),
		QUY("Q"), KA("K");

		private final String displayValue;

		Value(String displayValue) {
			this.displayValue = displayValue;
		}

		public String getDisplayValue() {
			return displayValue;
		}
	}

	public enum Type {
		CO("♥"), RO("♦"), CHUON("♣"), BICH("♠");

		private final String symbol;

		Type(String symbol) {
			this.symbol = symbol;
		}

		public String getSymbol() {
			return symbol;
		}
	}

	// Thuộc tính của một lá bài
	Value value;
	Type type;

	public Card() {
	}

	public Card(Value value, Type type) {
		this.value = value;
		this.type = type;
	}

	// Lấy giá trị thực của lá bài
	public int getValueValue() {
		switch (value) {
		case AT:
			return 14;
		case HAI:
			return 15;
		case BA:
			return 3;
		case BON:
			return 4;
		case NAM:
			return 5;
		case SAU:
			return 6;
		case BAY:
			return 7;
		case TAM:
			return 8;
		case CHIN:
			return 9;
		case MUOI:
			return 10;
		case RI:
			return 11;
		case QUY:
			return 12;
		case KA:
			return 13;
		default:
			return 0;
		}
	}

	public int getTypeValue() {
		switch (type) {
		case BICH:
			return 1;
		case CHUON:
			return 2;
		case RO:
			return 3;
		case CO:
			return 4;
		default:
			return 0;
		}
	}

	// Hàm trả về thông tin lá bài với định dạng đẹp
	@Override
	public String toString() {
		return value.getDisplayValue() + type.getSymbol();
	}

	// Hàm so sánh giá trị của lá bài
	public boolean compareCard(Card otherCard) {
		if (this.getValueValue() != otherCard.getValueValue()) {
			return this.getValueValue() > otherCard.getValueValue();
		}
		return this.getTypeValue() > otherCard.getTypeValue();
	}

	// Getter và setter
	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}