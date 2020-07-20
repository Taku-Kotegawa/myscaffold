create table account (
    username varchar(128),
    password varchar(88) not null,
    first_name varchar(128) not null,
    last_name varchar(128) not null,
    email varchar(128) not null,
    url varchar(255) not null,
    profile text not null,
    constraint pk_tbl_account primary key (username)
);

COMMENT ON TABLE account IS 'アカウント';
COMMENT ON COLUMN account.username IS 'ユーザID';
COMMENT ON COLUMN account.password IS 'パスワード';
COMMENT ON COLUMN account.first_name IS '名';
COMMENT ON COLUMN account.last_name IS '姓';
COMMENT ON COLUMN account.email IS 'メールアドレス';
COMMENT ON COLUMN account.url IS 'URL';
COMMENT ON COLUMN account.profile IS 'プロフィール';

----

create table role (
    username varchar(128),
    role varchar(10) not null,
    constraint pk_tbl_role primary key (username, role),
    constraint fk_tbl_role foreign key (username) references account(username)
);
COMMENT ON TABLE role IS 'ユーザ・ロール関連';
COMMENT ON COLUMN role.username IS 'ユーザID';
COMMENT ON COLUMN role.role IS 'ロール';

----

create table successful_authentication (
    username varchar(128),
    authentication_timestamp timestamp,
    constraint pk_tbl_sa primary key (username, authentication_timestamp),
    constraint fk_tbl_sa foreign key (username) references account(username)
);
COMMENT ON TABLE successful_authentication IS '認証成功履歴';
COMMENT ON COLUMN successful_authentication.username IS 'ユーザID';
COMMENT ON COLUMN successful_authentication.authentication_timestamp IS '成功日時';

----

create table failed_authentication (
    username varchar(128),
    authentication_timestamp timestamp,
    constraint pk_tbl_fa primary key (username, authentication_timestamp),
    constraint fk_tbl_fa foreign key (username) references account(username)
);
create index idx_tbl_aasl_t on successful_authentication (authentication_timestamp);
create index idx_tbl_aafl_t on failed_authentication (authentication_timestamp);
COMMENT ON TABLE failed_authentication IS '認証失敗履歴';
COMMENT ON COLUMN failed_authentication.username IS 'ユーザID';
COMMENT ON COLUMN failed_authentication.authentication_timestamp IS '失敗日時';

----

create table password_history (
    username varchar(128),
    password varchar(128) not null,
    use_from timestamp,
    constraint pk_tbl_ph primary key (username, use_from),
    constraint fk_tbl_ph foreign key (username) references account(username)
);
COMMENT ON TABLE password_history IS 'パスワード履歴';
COMMENT ON COLUMN password_history.username IS 'ユーザID';
COMMENT ON COLUMN password_history.password IS 'パスワード';
COMMENT ON COLUMN password_history.use_from IS '利用開始日時';

----

create table password_reissue_info (
    username varchar(128) not null,
    token varchar(128),
    secret varchar(88) not null,
    expiry_date timestamp not null,
    constraint pk_tbl_pri primary key (token),
    constraint fk_tbl_pri foreign key (username) references account(username)
);
COMMENT ON TABLE password_reissue_info IS 'パスワード再発行認証情報';
COMMENT ON COLUMN password_reissue_info.username IS 'ユーザID';
COMMENT ON COLUMN password_reissue_info.token IS 'トークン';
COMMENT ON COLUMN password_reissue_info.secret IS '秘密情報';
COMMENT ON COLUMN password_reissue_info.expiry_date IS '有効期限';

----

create table failed_password_reissue (
    token varchar(128),
    attempt_date timestamp,
    constraint pk_tbl_prfl primary key (token, attempt_date),
    constraint fk_tbl_prfl foreign key (token) references password_reissue_info(token)
);
create index idx_tbl_prfl on failed_password_reissue (token);
COMMENT ON TABLE failed_password_reissue IS 'パスワード再発行失敗履歴';
COMMENT ON COLUMN failed_password_reissue.token IS 'トークン';
COMMENT ON COLUMN failed_password_reissue.attempt_date IS '試行日時';

----

create table temp_file (
    id char(36),
    original_name varchar(256),
    body bytea not null,
    uploaded_date timestamp not null,
    constraint pk_tbl_tf primary key (id)
);
create index idx_tbl_tf on temp_file (uploaded_date);
COMMENT ON TABLE temp_file IS '一時保存ファイル';
COMMENT ON COLUMN temp_file.id IS 'ID(内部番号)';
COMMENT ON COLUMN temp_file.original_name IS 'オリジナルファイル名';
COMMENT ON COLUMN temp_file.body IS 'ファイル本体';
COMMENT ON COLUMN temp_file.uploaded_date IS 'アップロード日時';

----

create table account_image (
    username varchar(128),
    body bytea not null,
    extension char(3),
    constraint pk_tbl_ui primary key (username),
    constraint fk_tbl_ui foreign key (username) references account(username)
);
COMMENT ON TABLE account_image IS 'アカウント画像ファイル';
COMMENT ON COLUMN account_image.username IS 'ユーザID';
COMMENT ON COLUMN account_image.body IS 'ファイル本体';
COMMENT ON COLUMN account_image.extension IS '拡張子';

----

create table if not exists sample1 (
    integer_number            integer,
    real_number               real,
    double_precision_number   double precision,
    decimal_number            decimal,
    serial_number             serial,
    character_varying_string  character varying(5),
    varchar_string            varchar(5),
    character_string          character(10),
    char_string               char(10),
    text_string               text,
    date_data                 date,
    time_data                 time without time zone,
    time_zone_data            time(2) with time zone,
    timestamp_data            timestamp,
    timestamp_zone_data       timestamp(3) with time zone,
    boolean_boolean           boolean,
    binary_binary             bytea,
    constraint pk_tbl_sample1 primary key (integer_number)
);

COMMENT ON TABLE sample1 IS 'サンプル１';
COMMENT ON COLUMN sample1.integer_number IS '数値・整数１';
COMMENT ON COLUMN sample1.real_number IS '数値・整数２';
COMMENT ON COLUMN sample1.double_precision_number IS '数値・小数１';
COMMENT ON COLUMN sample1.decimal_number IS '数値・小数２';
COMMENT ON COLUMN sample1.serial_number IS '数値・連番';
COMMENT ON COLUMN sample1.character_varying_string IS '文字列・可変長１';
COMMENT ON COLUMN sample1.varchar_string IS '文字列・可変長２';
COMMENT ON COLUMN sample1.character_string IS '文字列・固定長１';
COMMENT ON COLUMN sample1.char_string IS '文字列・固定長２';
COMMENT ON COLUMN sample1.text_string IS '文字列・テキスト';
COMMENT ON COLUMN sample1.date_data IS '日付・日付';
COMMENT ON COLUMN sample1.time_data IS '日付・時刻１';
COMMENT ON COLUMN sample1.time_zone_data IS '日付・時刻２';
COMMENT ON COLUMN sample1.timestamp_data IS '日付・日時１';
COMMENT ON COLUMN sample1.timestamp_zone_data IS '日付・日時２';
COMMENT ON COLUMN sample1.boolean_boolean IS '真偽値１';
COMMENT ON COLUMN sample1.binary_binary IS 'バイナリ';
