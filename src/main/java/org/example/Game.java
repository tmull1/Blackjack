package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Deck deck;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private int playerBalance;

    public Game() {
        deck = new Deck();
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        playerBalance = 1000;  // Starting balance
    }

    private int calculateHandValue(List<Card> hand) {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            String cardValue = card.getValue();
            switch (cardValue) {
                case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9": case "10":
                    value += Integer.parseInt(cardValue);
                    break;
                case "Jack": case "Queen": case "King":
                    value += 10;
                    break;
                case "Ace":
                    value += 11;
                    aceCount++;
                    break;
            }
        }

        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }

        return value;
    }

    private void displayHands(boolean revealDealer) {
        System.out.println("Dealer's hand:");
        if (revealDealer) {
            for (Card card : dealerHand) {
                System.out.println(card);
            }
            System.out.println("Total value: " + calculateHandValue(dealerHand));
        } else {
            System.out.println("Hidden");
            for (int i = 1; i < dealerHand.size(); i++) {
                System.out.println(dealerHand.get(i));
            }
        }

        System.out.println("\nPlayer's hand:");
        for (Card card : playerHand) {
            System.out.println(card);
        }
        System.out.println("Total value: " + calculateHandValue(playerHand));
    }

    public void play() {
        playerHand.add(deck.drawCard());
        playerHand.add(deck.drawCard());

        dealerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());

        Scanner scanner = new Scanner(System.in);
        boolean playerTurn = true;
        boolean gameEnded = false;

        while (!gameEnded) {
            displayHands(false);

            if (playerTurn) {
                System.out.println("Do you want to hit (H) or stand (S)?");
                String choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("H")) {
                    playerHand.add(deck.drawCard());
                    if (calculateHandValue(playerHand) > 21) {
                        gameEnded = true;
                        System.out.println("Player busts! Dealer wins.");
                        playerBalance -= 100;
                    }
                } else {
                    playerTurn = false;
                }
            } else {
                while (calculateHandValue(dealerHand) < 17) {
                    dealerHand.add(deck.drawCard());
                }
                gameEnded = true;
                int playerTotal = calculateHandValue(playerHand);
                int dealerTotal = calculateHandValue(dealerHand);

                displayHands(true);

                if (dealerTotal > 21 || playerTotal > dealerTotal) {
                    System.out.println("Player wins!");
                    playerBalance += 100;
                } else if (playerTotal < dealerTotal) {
                    System.out.println("Dealer wins!");
                    playerBalance -= 100;
                } else {
                    System.out.println("It's a tie!");
                }
            }
        }

        System.out.println("Player's new balance: $" + playerBalance);
        scanner.close();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}


