# プロジェクトの概要
## 【アプリケーション名（システム名）】
**SharePlan**
## 【アプリケーション概要】
ユーザがブラウザより下記機能を利用できるアプリを提供する。

- ・Login機能およびGroup機能
- ・個人Todo List
- ・Scheduleのシェア
- ・チャット機能

## 【機能概要】

・Login機能およびGroup機能

個人で使用できるTodo Listを作成およびGroupCodeで認証後使用できるScheduleおよびチャット機能を提供

・Todoリスト(個人)

Main画面に「Todo List」として作成。

ログインしたデータベースのTodoテーブルよりユーザ名もしくはIDによってTodoリストを取得する。
　
※画面レイアウト、および動作は別資料「画面設計書」を参照。

・Scheduleのシェア

Group Share画面に「Schedule」として作成。

データベースから取得したScheduleテーブルより日付もしくは優先順位によって各データをソートして表示する。

※画面レイアウト、および動作は別資料「画面設計書」を参照。

・Chat 機能

Group Share画面に「Chat」として作成。

データベースから取得したChatテーブルより会話記録を表示。日付の新しい順にソートして表示する。

※画面レイアウト、および動作は別資料「画面設計書」を参照。

# 使用している主な技術

##
