-- 账号相关数据表

DROP TABLE IF EXISTS account_user;
CREATE TABLE account_user
(
    id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    username       VARCHAR(32)      DEFAULT '' COMMENT '用户名',
    password       VARCHAR(128)     DEFAULT '' COMMENT '密码（密文）',
    avatar         VARCHAR(255)     DEFAULT '' COMMENT '头像URL',
    phone          VARCHAR(32)      DEFAULT '' COMMENT '手机号码',
    email          VARCHAR(64)      DEFAULT '' COMMENT '电子邮箱',
    description    VARCHAR(255)     DEFAULT '' COMMENT '简介',
    enable         TINYINT UNSIGNED DEFAULT 0 COMMENT '是否启用，1=启用，0=未启用',
    last_login_ip  VARCHAR(32)      DEFAULT '' COMMENT '最后登录IP地址（冗余）',
    login_count    INT UNSIGNED     DEFAULT 0 COMMENT '累计登录次数（冗余）',
    gmt_last_login DATETIME         DEFAULT NULL COMMENT '最后登录时间（冗余）',
    gmt_create     DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified   DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='用户';

DROP TABLE IF EXISTS account_role;
CREATE TABLE account_role
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    name         VARCHAR(32)      DEFAULT '' COMMENT '名称',
    description  VARCHAR(255)     DEFAULT '' COMMENT '简介',
    sort         TINYINT UNSIGNED DEFAULT 0 COMMENT '排序序号',
    gmt_create   DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='角色';

DROP TABLE IF EXISTS account_user_role;
CREATE TABLE account_user_role
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    user_id      BIGINT UNSIGNED DEFAULT 0 COMMENT '用户ID',
    role_id      BIGINT UNSIGNED DEFAULT 0 COMMENT '角色ID',
    gmt_create   DATETIME        DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified DATETIME        DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色关联';

DROP TABLE IF EXISTS account_permission;
CREATE TABLE account_permission
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    name         VARCHAR(32)      DEFAULT '' COMMENT '名称',
    value        VARCHAR(255)     DEFAULT '' COMMENT '值',
    description  VARCHAR(255)     DEFAULT '' COMMENT '简介',
    sort         TINYINT UNSIGNED DEFAULT 0 COMMENT '排序序号',
    gmt_create   DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='权限';

DROP TABLE IF EXISTS account_role_permission;
CREATE TABLE account_role_permission
(
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    role_id       BIGINT UNSIGNED DEFAULT 0 COMMENT '角色ID',
    permission_id BIGINT UNSIGNED DEFAULT 0 COMMENT '权限ID',
    gmt_create    DATETIME        DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified  DATETIME        DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='角色权限关联';

DROP TABLE IF EXISTS account_login_log;
CREATE TABLE account_login_log
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    user_id      BIGINT UNSIGNED DEFAULT 0 COMMENT '用户ID',
    username     VARCHAR(32)     DEFAULT '' COMMENT '用户用户名（冗余）',
    ip           VARCHAR(32)     DEFAULT '' COMMENT '登录IP地址',
    user_agent   VARCHAR(255)    DEFAULT '' COMMENT '浏览器内核',
    gmt_login    DATETIME        DEFAULT NULL COMMENT '登录时间',
    gmt_create   DATETIME        DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified DATETIME        DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='用户登录日志';