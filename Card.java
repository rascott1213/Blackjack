package cardgames;

public class Card {
	public String suit;
	public int rank;

	public Card(String suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public String getFileName() {
		if (rank == 1) {
			return "ace_of_" + suit + ".png";
		} else if (rank == 11) {
			return "jack_of_" + suit + ".png";
		} else if (rank == 12) {
			return "queen_of_" + suit + ".png";
		} else if (rank == 13) {
			return "king_of_" + suit + ".png";
		} else
			return rank + "_of_" + suit + ".png";
	}
}
