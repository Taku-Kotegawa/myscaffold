CREATE TABLE account(
    username VARCHAR(128),
    password VARCHAR(88) NOT NULL,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,
    url VARCHAR(255) NOT NULL,
    profile TEXT NOT NULL,
    CONSTRAINT pk_tbl_account PRIMARY KEY (username)
);

COMMENT ON TABLE account IS 'アカウント';
COMMENT ON COLUMN account.username IS 'ユーザID';
COMMENT ON COLUMN account.password IS 'パスワード';
COMMENT ON COLUMN account.first_name IS '名';
COMMENT ON COLUMN account.last_name IS '姓';
COMMENT ON COLUMN account.email IS 'メールアドレス';
COMMENT ON COLUMN account.url IS 'URL';
COMMENT ON COLUMN account.profile IS 'プロフィール';

CREATE TABLE role(
    username VARCHAR(128),
    role VARCHAR(10) NOT NULL,
    CONSTRAINT pk_tbl_role PRIMARY KEY (username, role),
    CONSTRAINT fk_tbl_role FOREIGN KEY (username) REFERENCES account(username)
);
COMMENT ON TABLE role IS 'ユーザ・ロール関連';
COMMENT ON COLUMN role.username IS 'ユーザID';
COMMENT ON COLUMN role.role IS 'ロール';

CREATE TABLE successful_authentication(
    username VARCHAR(128),
    authentication_timestamp TIMESTAMP,
    CONSTRAINT pk_tbl_sa PRIMARY KEY (username, authentication_timestamp),
    CONSTRAINT fk_tbl_sa FOREIGN KEY (username) REFERENCES account(username)
);
COMMENT ON TABLE successful_authentication IS '認証成功履歴';
COMMENT ON COLUMN successful_authentication.username IS 'ユーザID';
COMMENT ON COLUMN successful_authentication.authentication_timestamp IS '成功日時';

CREATE TABLE failed_authentication(
    username VARCHAR(128),
    authentication_timestamp TIMESTAMP,
    CONSTRAINT pk_tbl_fa PRIMARY KEY (username, authentication_timestamp),
    CONSTRAINT fk_tbl_fa FOREIGN KEY (username) REFERENCES account(username)
);
CREATE INDEX idx_tbl_aasl_t ON successful_authentication (authentication_timestamp);
CREATE INDEX idx_tbl_aafl_t ON failed_authentication (authentication_timestamp);
COMMENT ON TABLE failed_authentication IS '認証失敗履歴';
COMMENT ON COLUMN failed_authentication.username IS 'ユーザID';
COMMENT ON COLUMN failed_authentication.authentication_timestamp IS '失敗日時';

CREATE TABLE password_history(
    username VARCHAR(128),
    password VARCHAR(128) NOT NULL,
    use_from TIMESTAMP,
    CONSTRAINT pk_tbl_ph PRIMARY KEY (username, use_from),
    CONSTRAINT fk_tbl_ph FOREIGN KEY (username) REFERENCES account(username)
);
COMMENT ON TABLE password_history IS 'パスワード履歴';
COMMENT ON COLUMN password_history.username IS 'ユーザID';
COMMENT ON COLUMN password_history.password IS 'パスワード';
COMMENT ON COLUMN password_history.use_from IS '利用開始日時';

CREATE TABLE password_reissue_info(
    username VARCHAR(128) NOT NULL,
    token VARCHAR(128),
    secret VARCHAR(88) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    CONSTRAINT pk_tbl_pri PRIMARY KEY (token),
    CONSTRAINT fk_tbl_pri FOREIGN KEY (username) REFERENCES account(username)
);
COMMENT ON TABLE password_reissue_info IS 'パスワード再発行認証情報';
COMMENT ON COLUMN password_reissue_info.username IS 'ユーザID';
COMMENT ON COLUMN password_reissue_info.token IS 'トークン';
COMMENT ON COLUMN password_reissue_info.secret IS '秘密情報';
COMMENT ON COLUMN password_reissue_info.expiry_date IS '有効期限';

CREATE TABLE failed_password_reissue(
    token VARCHAR(128),
    attempt_date TIMESTAMP,
    CONSTRAINT pk_tbl_prfl PRIMARY KEY (token, attempt_date),
    CONSTRAINT fk_tbl_prfl FOREIGN KEY (token) REFERENCES password_reissue_info(token)
);
CREATE INDEX idx_tbl_prfl ON failed_password_reissue (token);
COMMENT ON TABLE failed_password_reissue IS 'パスワード再発行失敗履歴';
COMMENT ON COLUMN failed_password_reissue.token IS 'トークン';
COMMENT ON COLUMN failed_password_reissue.attempt_date IS '試行日時';

CREATE TABLE temp_file(
    id CHAR(36),
    original_name VARCHAR(256),
    body BYTEA NOT NULL,
    uploaded_date TIMESTAMP NOT NULL,
    CONSTRAINT pk_tbl_tf PRIMARY KEY (id)
);
CREATE INDEX idx_tbl_tf ON temp_file (uploaded_date);
COMMENT ON TABLE temp_file IS '一時保存ファイル';
COMMENT ON COLUMN temp_file.id IS 'ID(内部番号)';
COMMENT ON COLUMN temp_file.original_name IS 'オリジナルファイル名';
COMMENT ON COLUMN temp_file.body IS 'ファイル本体';
COMMENT ON COLUMN temp_file.uploaded_date IS 'アップロード日時';

CREATE TABLE account_image(
    username VARCHAR(128),
    body BYTEA NOT NULL,
    extension CHAR(3),
    CONSTRAINT pk_tbl_ui PRIMARY KEY (username),
    CONSTRAINT fk_tbl_ui FOREIGN KEY (username) REFERENCES account(username)
);
COMMENT ON TABLE account_image IS 'アカウント画像ファイル';
COMMENT ON COLUMN account_image.username IS 'ユーザID';
COMMENT ON COLUMN account_image.body IS 'ファイル本体';
COMMENT ON COLUMN account_image.extension IS '拡張子';
