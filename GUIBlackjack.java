package cardgames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class GUIBlackjack extends JFrame {
	
	ArrayList<Card> deck;
	ArrayList<Card> dealerCards = new ArrayList<Card>();
	ArrayList<Card> playerCards = new ArrayList<Card>();
	
	private JButton dealerButton;
	private JButton playerButton;
	private JPanel dealerPanel;
	private JPanel playerPanel;
	private JLabel turnAnnouncement;
	ArrayList<JLabel> dealerCardReps = new ArrayList<JLabel>();
	ArrayList<JLabel> playerCardReps = new ArrayList<JLabel>();
	
	private int playerScore;
	private int dealerScore;
	
	private int dealerLock = 0;
	
	private int gameOn = 0;
	
	private String dealerTotal = "Dealer Score: ";
	private String playerTotal = "Player Score: ";
	
	JLabel dealerScoreDisplay = new JLabel("Dealer Score: 0   ");
	JLabel playerScoreDisplay = new JLabel("Player Score: 0   ");
	
	
	public GUIBlackjack(){
		deck = createDeck();
		
		setTitle("World's coolest Blackjack!");
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();

		dealerButton = new JButton("Dealer");
		dealerButton.addActionListener(new MyButtonHandler());
		buttonPanel.add(dealerButton);
		
		playerButton = new JButton("Player");
		playerButton.addActionListener(new MyButtonHandler());
		buttonPanel.add(playerButton);
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		
		
		JPanel cardsPanel = new JPanel();
		cardsPanel.setLayout(new GridLayout(2, 1));
		add(cardsPanel, BorderLayout.CENTER);
		
		dealerPanel = new JPanel();
		playerPanel = new JPanel();
		cardsPanel.add(dealerPanel);
		cardsPanel.add(playerPanel);
		
		JPanel scorePanel = new JPanel(new BorderLayout());
		
		turnAnnouncement = new JLabel("Player's Turn");
		scorePanel.add(turnAnnouncement, BorderLayout.WEST);
		
		JPanel dealerScorePanel = new JPanel();
		dealerScoreDisplay.setFont(new Font("Verdana",1,20));
		updateDealerScore();
		dealerScorePanel.add(dealerScoreDisplay);
		scorePanel.add(dealerScorePanel, BorderLayout.NORTH);
		
		JPanel playerScorePanel = new JPanel();
		playerScoreDisplay.setFont(new Font("Verdana",1,20));
		updatePlayerScore();
		playerScorePanel.add(playerScoreDisplay);
		scorePanel.add(playerScorePanel, BorderLayout.SOUTH);
		
		turnAnnouncement = new JLabel("Player's Turn");
		turnAnnouncement.setFont(new Font("Verdana",1,30));
		scorePanel.add(turnAnnouncement, BorderLayout.WEST);
		
		scorePanel.setBorder(new LineBorder(Color.BLACK));
		add(scorePanel, BorderLayout.WEST);
		
		JPanel rulesPanel = new JPanel(new BorderLayout());
		JLabel howToPlay = new JLabel("<html>Click the Player button to deal yourself <br/>as many cards as you"
				+ " like, up to or<br/> over 21. Then, when you have your<br/> cards, click the Dealer button to start<br/>"
				+ " dealing the Dealer's cards. Note that <br/>once you have started dealing the <br/>Dealer's cards,"
				+ " you cannot deal more <br/>player cards until the Dealer busts or <br/>gets over 16, ending the"
				+ " round and<br/> starting a new one.</html>");
		rulesPanel.add(howToPlay, BorderLayout.EAST);
		add(rulesPanel, BorderLayout.EAST);
		
		dealToPlayer();
		dealToDealer();
		updateUI();
		
	}
	
	public void start(){
		setVisible(true);
	}
	
	public void dealToDealer(){
		Card c = deck.remove(deck.size() - 1);
		dealerCards.add(c);
		dealerScore=addToScore(c, dealerScore);
		if (dealerScore > 16) {
			dealerLock = 2;
		}
	}
	
	public void dealToPlayer(){
		Card c = deck.remove(deck.size() - 1);
		playerCards.add(c);
		playerScore=addToScore(c, playerScore);
		if (playerScore == 21)
			dealerLock = 1;
	}
	
	private int addToScore (Card c, int score) {
		if (c.rank == 1)
			return score+11;
		else if (c.rank >= 10)
			return score+10;
		else
			return score+c.rank;
	}
	
	private ArrayList<Card> createDeck() {
		ArrayList<Card> deck = new ArrayList<Card>();
		for (int i = 1; i < 14; i++) {
			deck.add(new Card("hearts", i));
			deck.add(new Card("diamonds", i));
			deck.add(new Card("clubs", i));
			deck.add(new Card("spades", i));
		}
		
		Collections.shuffle(deck);
		
		return deck;
	}
	
	private void updateUI(){
		updateDealerCards();
		updatePlayerCards();
		updateDealerScore();
		updatePlayerScore();
		if (dealerLock == 1)
			turnAnnouncement.setText("Dealer's Turn");
		else if (dealerLock == 0)
			turnAnnouncement.setText("Player's Turn");
	}
	
	private void updateDealerCards() {
		for (JLabel label : dealerCardReps) {
			dealerPanel.remove(label);
		}
		dealerCardReps.clear();
		for(Card c : dealerCards){
			JLabel l = new JLabel();
			changeImage(l, c.getFileName());
			dealerPanel.add(l);
			dealerCardReps.add(l);
		}
	}
	
	private void updatePlayerCards() {
		for (JLabel label : playerCardReps) {
			playerPanel.remove(label);
		}
		playerCardReps.clear();
		for(Card c : playerCards){
			JLabel l = new JLabel();
			changeImage(l, c.getFileName());
			playerPanel.add(l);
			playerCardReps.add(l);
		}
	}
	
	private void updateDealerScore() {
		StringBuilder tempDealerTotal = new StringBuilder();
		tempDealerTotal.append(dealerTotal);
		tempDealerTotal.append(dealerScore);
		dealerScoreDisplay.setText(String.format("%-18s", tempDealerTotal));
	}
	
	private void updatePlayerScore() {
		StringBuilder tempPlayerTotal = new StringBuilder();
		tempPlayerTotal.append(playerTotal);
		tempPlayerTotal.append(playerScore);
		playerScoreDisplay.setText(String.format("%-18s", tempPlayerTotal));
	}
	
	private void compareScores() {
		if (playerScore > 21) {
			dealerLock = 2;
			turnAnnouncement.setText("<html>Player is<br/>over 21.<br/>Player busts.<br/>Dealer wins.</html>");
		}
		else if (gameOn == 1 && dealerLock > 0) {
			if ((dealerScore > playerScore) && (dealerScore <= 21)) {
				dealerLock = 2;
				turnAnnouncement.setText("<html>Dealer is<br/>higher than<br/>Player.<br/>Dealer wins.</html>");
			}
			else if ((dealerScore < playerScore) && (dealerScore >= 17)) {
				dealerLock = 2;
				turnAnnouncement.setText("<html>Player is<br/>higher than<br/>Dealer.<br/>Player wins.</html>");
			}
			else if ((dealerScore == playerScore) && (dealerScore > 0)) {
				dealerLock = 2;
				turnAnnouncement.setText("<html>Dealer is tied<br/>with Player.<br/>Dealer wins.</html>");
			}
			else if (dealerScore > 21) {
				dealerLock = 2;
				turnAnnouncement.setText("<html>Dealer is<br/>over 21.<br/>Dealer busts.<br/>Player wins.");
			}
		}
	}
	
	private void changeImage(JLabel label, String filename) {
		try {
			BufferedImage img = 
					ImageIO.read(new File(filename));
			Image resized = img.getScaledInstance(
					70,
					100, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(resized);
			label.setIcon(icon);
		} catch (IOException e) {
			System.out.println("Error opening image file");
		}
	}
	
	private void resetGame() {
		dealerLock = 0;
		dealerScore = 0;
		playerScore = 0;
		for (JLabel label : dealerCardReps) {
			dealerPanel.remove(label);
		}
		dealerCardReps.clear();
		for (JLabel label : playerCardReps) {
			playerPanel.remove(label);
		}
		playerCardReps.clear();
		dealerCards.clear();
		playerCards.clear();
		deck = createDeck();
		dealToPlayer();
		dealToDealer();
		gameOn = 0;
		
		
	}
	
	
	public class MyButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (dealerLock == 2) {
				resetGame();
			}
			else if (((e.getSource() == playerButton) && (playerScore < 21)) && dealerLock == 0) {
				dealToPlayer();
				gameOn = 1;
			}
			else if ((e.getSource() == dealerButton) && (dealerScore < 17)){
				dealerLock = 1;
				dealToDealer();
				gameOn = 1;
			}
			
			compareScores();
			updateUI();
			validate();
			repaint();
		}
		
	}
}