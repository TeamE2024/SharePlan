package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * 質問作成・入力チェック処理
 */
public class QuestionLogic {

	private Map<String, Map<String, Integer>> countMap; //質問・<選択肢・投票数>を保持するMap
	private List<Integer> optionNumberList; //選択肢の数を保有するList

	//コンストラクタ
	public QuestionLogic(Map<String, Map<String, Integer>> countMap, List<Integer> optionNumberList) {
		this.countMap = countMap;
		this.optionNumberList = optionNumberList;
	}

	/*
	 * 選択肢追加
	 */
	public List<Integer> addOption() {
		if (optionNumberList == null) {
			optionNumberList = new ArrayList<>();
		}
		int count = optionNumberList.size() + 1;
		optionNumberList.add(count);
		return optionNumberList;
	}

	/*
	 * 選択肢削除
	 */
	public List<Integer> removeOption() {
		if (!optionNumberList.isEmpty()) {
			optionNumberList.remove(optionNumberList.size() - 1);
		}
		return optionNumberList;
	}

	/*
	 * 質問全体チェック
	 * 文字列がある＝完了できない
	 */
	public String completeQuestion(String question, String[] textArray) {
		String Emsg = "";

		//質問自体がない時
		if (question == null || question.trim().isEmpty()) {
			Emsg = "質問は入力必須です。";
			return Emsg;

		//質問は書いたけど選択肢がないとき
		} else if (textArray == null || textArray.length == 0) {
			Emsg = "選択肢をYESとNOにしますか？";
			return Emsg;

		//質問を書いた時、選択肢内を1つずつ確認
		} else {
			return processOptions(question, textArray);
		}
	}

	/*
	 * 選択肢内を1つずつ確認しエラーチェック
	 */
	private String processOptions(String question, String[] textArray) {
		String Emsg = "";	//メッセージ
		List<String> test = new ArrayList<>();	//textArrayの中身チェックを保持
		
			//ループで中身1つずつ確認
			for (int i = 0; i < textArray.length; i++) {
				//textArrayの中身がある時List(test)へ追加
				if (textArray[i] != null && !textArray[i].trim().isEmpty()) {
					test.add(textArray[i]);
				}
			}
			//追加されたものが1つ以下の場合
			if (test.size() <= 1) {
				Emsg = "選択肢が入力されていない、または1つ以下です";
			
			//1つ以上の場合フィールドcountMapを更新しメッセージ(Emsg)を更新しない
			} else {
				List<String> textList = new ArrayList<>(Arrays.asList(textArray));

				Iterator<String> iterator = textList.iterator();
				while (iterator.hasNext()) {
					if (iterator.next().isEmpty()) {
						iterator.remove();
					}
				}
				
				Map<String, Integer> QandA = new LinkedHashMap<>();
				for (String str : textList) {
					QandA.put(str, 0);
				}
				if (countMap == null) {
					countMap = new LinkedHashMap<>();
				}
				if (!countMap.containsKey(question)) {
					countMap.put(question, QandA);
				}
			}
		return Emsg;
	}

	/*
	 * 選択肢がない時の処理
	 */
	public void finalizeQuestion(String question) {
		List<String> textList = new ArrayList<>();
		textList.add("YES");
		textList.add("NO");
		Map<String, Integer> QandA = new LinkedHashMap<>();
		for (String str : textList) {
			QandA.put(str, 0);
		}

		if (countMap == null) {
			countMap = new LinkedHashMap<>();
		}
		if (!countMap.containsKey(question)) {
			countMap.put(question, QandA);
		}

	}

	public Map<String, Map<String, Integer>> getCountMap() {
		return countMap;
	}

	public void setCountMap(Map<String, Map<String, Integer>> countMap) {
		this.countMap = countMap;
	}
}
