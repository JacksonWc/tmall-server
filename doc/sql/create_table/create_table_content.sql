-- 资讯相关数据表

DROP TABLE IF EXISTS content_category;
CREATE TABLE content_category
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    name         VARCHAR(16)      DEFAULT '' COMMENT '类别名称',
    keywords     VARCHAR(255)     DEFAULT '' COMMENT '关键词列表，各关键词使用英文的逗号分隔',
    sort         TINYINT UNSIGNED DEFAULT 0 COMMENT '排序序号',
    enable       TINYINT UNSIGNED DEFAULT 0 COMMENT '是否启用，1=启用，0=未启用',
    is_display   TINYINT UNSIGNED DEFAULT 0 COMMENT '是否显示在导航栏中，1=启用，0=未启用',
    gmt_create   DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT ='内容-类别' CHARSET = utf8mb4;

DROP TABLE IF EXISTS content_article;
CREATE TABLE content_article
(
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    author_id     BIGINT UNSIGNED  DEFAULT 0 COMMENT '作者ID',
    author_name   VARCHAR(32)      DEFAULT '' COMMENT '作者名字',
    category_id   BIGINT UNSIGNED  DEFAULT 0 COMMENT '类别ID',
    category_name VARCHAR(16)      DEFAULT '' COMMENT '类别名称',
    title         VARCHAR(255)     DEFAULT '' COMMENT '标题',
    brief         VARCHAR(255)     DEFAULT '' COMMENT '摘要',
    cover_url     VARCHAR(255)     DEFAULT '' COMMENT '封面图',
    keywords      VARCHAR(255)     DEFAULT '' COMMENT '关键词列表，各关键词使用英文的逗号分隔',
    ip            VARCHAR(32)      DEFAULT '' COMMENT 'IP',
    sort          TINYINT UNSIGNED DEFAULT 0 COMMENT '排序序号',
    up_count      INT UNSIGNED     DEFAULT 0 COMMENT '顶数量',
    down_count    INT UNSIGNED     DEFAULT 0 COMMENT '踩数量',
    click_count   INT UNSIGNED     DEFAULT 0 COMMENT '浏览量',
    comment_count INT UNSIGNED     DEFAULT 0 COMMENT '评论量',
    check_state   TINYINT UNSIGNED DEFAULT 0 COMMENT '审核状态，0=未审核，1=审核通过，2=拒绝审核',
    is_display    TINYINT UNSIGNED DEFAULT 0 COMMENT '显示状态，0=不显示，1=显示',
    is_recommend  TINYINT UNSIGNED DEFAULT 0 COMMENT '是否推荐，0=不推荐，1=推荐',
    gmt_create    DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified  DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '内容-文章' CHARSET = utf8mb4;

DROP TABLE IF EXISTS content_article_detail;
CREATE TABLE content_article_detail
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    article_id   BIGINT UNSIGNED DEFAULT 0 COMMENT '文章ID',
    detail       TEXT COMMENT '详情',
    gmt_create   DATETIME        DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified DATETIME        DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '内容-文章详情' CHARSET = utf8mb4;

DROP TABLE IF EXISTS content_up_down_log;
CREATE TABLE content_up_down_log
(
    id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    author_id      BIGINT UNSIGNED  DEFAULT 0 COMMENT '作者ID',
    author_name    VARCHAR(32)      DEFAULT '' COMMENT '作者名字',
    resource_type  TINYINT UNSIGNED DEFAULT 0 COMMENT '资源类型，0=文章，1=评论',
    resource_id    BIGINT UNSIGNED  DEFAULT 0 COMMENT '资源ID',
    resource_brief VARCHAR(255)     DEFAULT '' COMMENT '资源摘要，截取的文章标题或评论',
    op_type        TINYINT UNSIGNED DEFAULT 0 COMMENT '操作类型，0=踩，1=顶',
    gmt_create     DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified   DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '内容-顶踩日志' CHARSET = utf8mb4;

DROP TABLE IF EXISTS content_comment;
CREATE TABLE content_comment
(
    id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    author_id      BIGINT UNSIGNED  DEFAULT 0 COMMENT '作者ID',
    author_name    VARCHAR(32)      DEFAULT '' COMMENT '作者名字',
    resource_type  TINYINT UNSIGNED DEFAULT 0 COMMENT '资源类型，0=文章，1=评论',
    resource_id    BIGINT UNSIGNED  DEFAULT 0 COMMENT '资源ID',
    resource_brief VARCHAR(255)     DEFAULT '' COMMENT '资源摘要，截取的文章标题或评论',
    content        VARCHAR(512)     DEFAULT '' COMMENT '评论内容',
    ip             VARCHAR(32)      DEFAULT '' COMMENT '踩数量',
    floor          INT UNSIGNED     DEFAULT 0 COMMENT '楼层',
    up_count       INT UNSIGNED     DEFAULT 0 COMMENT '顶数量',
    down_count     INT UNSIGNED     DEFAULT 0 COMMENT '踩数量',
    check_state    TINYINT UNSIGNED DEFAULT 0 COMMENT '审核状态，0=未审核，1=审核通过，2=拒绝审核',
    is_display     TINYINT UNSIGNED DEFAULT 0 COMMENT '显示状态，0=不显示，1=显示',
    gmt_create     DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified   DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '内容-评论' CHARSET = utf8mb4;

DROP TABLE IF EXISTS content_check_log;
CREATE TABLE content_check_log
(
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    resource_type   TINYINT UNSIGNED DEFAULT 0 COMMENT '资源类型，0=文章，1=评论',
    resource_id     BIGINT UNSIGNED  DEFAULT 0 COMMENT '资源ID',
    resource_brief  VARCHAR(255)     DEFAULT '' COMMENT '资源摘要，截取的文章标题或评论',
    original_state  TINYINT UNSIGNED DEFAULT 0 COMMENT '原审核状态',
    new_state       TINYINT UNSIGNED DEFAULT 0 COMMENT '新审核状态',
    check_user_id   BIGINT UNSIGNED  DEFAULT 0 COMMENT '审核人ID',
    check_username VARCHAR(32)      DEFAULT '' COMMENT '审核人用户名',
    check_remarks   VARCHAR(64)      DEFAULT '' COMMENT '审核备注',
    gmt_check       DATETIME         DEFAULT NULL COMMENT '审核时间',
    gmt_create      DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified    DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '内容-审核日志' CHARSET = utf8mb4;