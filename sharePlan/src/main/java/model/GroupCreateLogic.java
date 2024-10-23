package model;

import java.util.List;

public class GroupCreateLogic {

	/*
	 * グループ登録に関する処理を行うモデル
	 */
		public static boolean execute(String input_group_name, List<Group> groupList) {
			//入力情報＝input_group_name
			//登録済情報＝groupList

			// 入室時に入力したgroup名が存在すればgroup_name一致で入室成功
			if (groupList != null) {
				try {
					for (Group group : groupList) {
						if (input_group_name.equals(group.getGroupName())) {
							return false; // 一致したらすでにあるのでエラー（パスワード一致）
						}
					}
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
			return true;
		}
	}