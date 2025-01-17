# プロジェクトの概要
## 【アプリケーション名（システム名）】
**SharePlan**
## 【アプリケーション概要】
ユーザがブラウザより下記機能を利用できるアプリを提供する。

- Login機能およびGroup機能
- 個人Todo List
- Scheduleのシェア
- Chat機能

## 【機能概要】

- Login機能およびGroup機能

個人で使用できるTodo Listを作成およびGroupCodeで認証後使用できるScheduleおよびチャット機能を提供

- Todoリスト(個人)

Main画面に「Todo List」として作成。

ログインしたデータベースのTodoテーブルよりユーザ名もしくはIDによってTodoリストを取得する。

- Scheduleのシェア

Group Share画面に「Schedule」として作成。

データベースから取得したScheduleテーブルより日付もしくは優先順位によって各データをソートして表示する。

- Chat 機能

Group Share画面に「Chat」として作成。

データベースから取得したChatテーブルより会話記録を表示。日付の新しい順にソートして表示する。

# 使用している主な技術

## 【使用言語】
- Java
- HTML
- CSS
- JavaScript

## 【データベース】
- PostgreSQL

## 【開発環境】
- Eclipse
- Git/GitHub
- WinMerge
- A5M2
