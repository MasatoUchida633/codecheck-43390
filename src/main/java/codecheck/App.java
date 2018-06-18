package codecheck;

import java.util.ArrayList;
import java.util.List;

public class App {
	public static void main(String[] args) {
		// 引数の分割
		String argList[] = args[0].split(" ");
		
		// カード枚数、MP、カード情報の取得
		int num = Integer.parseInt(argList[0]);
		int mp = Integer.parseInt(argList[1]);
		List<Card> cardList = new ArrayList<Card>();
		for (int i = 2; i < argList.length; i = i + 2) {
			Card card = new Card(Integer.parseInt(argList[i + 1]), Integer.parseInt(argList[i]));
			cardList.add(card);
		}
		
		// 最大火力の標準出力
		System.out.println(getMaximumFirepower(num, mp, cardList));
	}
	
	/**
	 * 引数より最大火力を取得する。
	 * 
	 * @param num カード枚数
	 * @param mp MP
	 * @param cardList カード情報
	 * @return 最大火力
	 */
	private static String getMaximumFirepower(int num, int mp, List<Card> cardList) {
		// 与えられたカードの枚数の取得
		int cardNum = cardList.size();
		
		// カード枚数、MP、与えられたカードの枚数のいずれかが0の場合、
		// カードを選択できないため、最大火力は0である
		if (cardNum == 0 || num == 0 || mp == 0) {
			return "0";
		}
		
		// 動的計画法のマップ
		int dp[][][] = new int[cardNum + 1][num + 1][mp + 1];
		
		// 動的計画法の実施
		for (int i = 0; i <= cardNum; i++) {
			Card card = null;
			int cost = 0;
			int attack = 0;
			
			if (i != 0) {
				card = cardList.get(i -1);
				cost = card.getCost();
				attack = card.getAttack();
			}
			
			for (int j = 0; j <= num; j++ ) {
				for (int k = 0; k <= mp; k++) {
					if (i == 0 || j == 0 || k == 0) {
						dp[i][j][k] = 0;
						continue;
					}
					
					int oldAttack = dp[i- 1][j - 1][k];
					if (k < cost) {
						dp[i][j][k] = oldAttack;
						continue;
					}
					
					int newAttack = dp[i -1][j -1][k - cost] + attack;
					if (newAttack > oldAttack) {
						dp[i][j][k] = newAttack;
					} else {
						dp[i][j][k] = oldAttack;
					}
				}
			}
			
		}
		
		// 最大火力の返却
		return String.valueOf(dp[cardNum][num][mp]);
	}
	
	/**
	 * カード情報。
	 * 
	 * @author Uchida Masato
	 * @since 1.0
	 */
	public static class Card {
		/** コスト */
		private int cost;
		
		/** 攻撃力 */
		private int attack;
		
		/**
		 * コンテキスト。
		 * 
		 * @param c コスト
		 * @param a 攻撃力
		 */
		Card(int c, int a) {
			this.cost = c;
			this.attack = a;
		}
		
		/**
		 * コストを取得する。
		 * 
		 * @return コスト
		 */
		public int getCost() {
			return this.cost;
		}
		
		/**
		 * 攻撃力を取得する。
		 * 
		 * @return 攻撃力
		 */
		public int getAttack() {
			return this.attack;
		}
	}
}
