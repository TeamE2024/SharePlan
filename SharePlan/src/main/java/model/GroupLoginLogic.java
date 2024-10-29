package model;

import java.util.List;

/*
 * ログインに関する処理を行うモデル
 */
public class GroupLoginLogic {
	public boolean execute(String input_group_name, List<Group> groupList) {
		//入力情報＝input_group_name
		//登録済情報＝groupList

		// 入室時に入力したgroup名が存在すればgroup_name一致で入室成功
		if (groupList != null) {
			try {
				for (Group group : groupList) {
					if (input_group_name.equals(group.getGroupName())) {
						return true; // ログイン成功（パスワード一致）
					}
				}
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		// 以下の場合はログイン失敗とする
		// ① groupListがnull（Groupが一件も登録されていない）
		// ②入室時に入力したグループ名に対応したグループ未登録
		// ③パスワード不一致
		return false;
	}
}