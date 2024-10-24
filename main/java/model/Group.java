package model;

import java.io.Serializable;

/*
 * ユーザーに関する情報を持つJavaBeans
 */
public class Group implements Serializable {
	private String group_name;	//グループ名

	public Group() {}
	
	public Group (String group_name) {
		this.group_name = group_name;
	}

	public String getGroupName() {
		return group_name;
	}
}
