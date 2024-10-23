/* 2024年10月8日作成*/
/* 「user」や「group」は使用できなかったため、名前を変更しております。
	また、テーブルの名前が、「A5M2」上では、大文字のところが小文字になっていたので、
	小文字に変更しております。<m(__)m>池辺
	
   2024年10月17日
   shareplanテーブルを削除して、新たにplanテーブルとscheduleテーブルを作成しました。池辺
	
*/

/* 2024年10月18日更新 テーブル名：schedule*/

CREATE TABLE schedule(
  id SERIAL,
  group_name VARCHAR (16) NOT NULL,
  plan      VARCHAR (100),
  plan_day    DATE,
  plan_time TIME,
  PRIMARY KEY (id));
  

/* 2024年10月18日作成 テーブル名：chat*/

CREATE TABLE chat(
  id SERIAL,
  user_name   VARCHAR (16),
  group_name VARCHAR (16),
  chat      VARCHAR (100),
  chat_day   TIMESTAMP,
  PRIMARY KEY (id));


/* 2024年10月22日更新 テーブル名：user_inf*/

CREATE TABLE user_inf(
  user_name   VARCHAR (16) NOT NULL,
  password  VARCHAR (8) NOT NULL,
  PRIMARY KEY (user_name));
  

/* 2024年10月21日更新 テーブル名：todo */
create table todo (
  id serial not null, 
  user_name VARCHAR(16) not null,
  list VARCHAR(100), 
  todo_day timestamp(6) without time zone,
  complete boolean not null,
  PRIMARY KEY(id));


/* 2024年10月9日作成 テーブル名：group_inf*/
CREATE TABLE group_inf(
  group_name  VARCHAR (16) NOT NULL,
  PRIMARY KEY (group_name));

