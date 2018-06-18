package codecheck;

import java.util.ArrayList;
import java.util.List;

public class App {
	public static void main(String[] args) {
//		for (int i = 0, l = args.length; i < l; i++) {
//			String output = String.format("argv[%s]: %s", i, args[i]);
//			System.out.println(output);
//		}
		
		int num = Integer.parseInt(args[0]);
		int mp = Integer.parseInt(args[1]);
		List<Card> cardList = new ArrayList<Card>();
		for (int i = 2; i < args.length; i = i + 2) {
			Card card = new Card(Integer.parseInt(args[i + 1]), Integer.parseInt(args[i]));
			cardList.add(card);
		}
		
		System.out.println(getMaximumFirepower(num, mp, cardList));
	}
	
	private static String getMaximumFirepower(int num, int mp, List<Card> cardList) {
		int cardNum = cardList.size();
		
		if (cardNum == 0 || num == 0 || mp == 0) {
			return "0";
		}
		
		int dp[][][] = new int[cardNum + 1][num + 1][mp + 1];
		
		for (int i = 0; i <= cardNum; i++) {
			for (int j = 0; j <= num; j++ ) {
				for (int k = 0; k <= mp; k++) {
					if (i == 0 || j == 0 || k == 0) {
						dp[i][j][k] = 0;
						continue;
					}
					
					int cost = cardList.get(i -1).getCost();
					if (k < cost) {
						dp[i][j][k] = dp[i- 1][j - 1][k];
						continue;
					}
					
					int newAttack = dp[i -1][j -1][k - cost] + cardList.get(i -1).getAttack();
					int oldAttack = dp[i- 1][j - 1][k];
					if (newAttack > oldAttack) {
						dp[i][j][k] = newAttack;
					} else {
						dp[i][j][k] = oldAttack;
					}
				}
			}
			
		}
		
		return String.valueOf(dp[cardNum][num][mp]);
	}
	
	private static class Card {
		private int cost;
		private int attack;
		
		Card(int c, int a) {
			this.cost = c;
			this.attack = a;
		}
		
		private int getCost() {
			return this.cost;
		}
		
		private int getAttack() {
			return this.attack;
		}
	}
}
