/*
2024年10月9日作成
仮データ登録用
【文字型】user_id,chat,plan
【日付型】chat_day,plan_day
→データ登録するときは、VALUESの後は文字型と日付型なので、’’（シングルクォーテーション）で囲む。
【SERIAL型】id
→接続時は自動で作成される。
*/

INSERT INTO shareplan (id, user_name, chat, chat_day, plan, plan_day)VALUES ('0001', 'minato',null,null,null,null)
