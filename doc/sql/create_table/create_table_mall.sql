-- 商城相关数据表

DROP TABLE IF EXISTS mall_category;
CREATE TABLE mall_category
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    name         VARCHAR(32)      DEFAULT '' COMMENT '类别名称',
    parent_id    BIGINT UNSIGNED  DEFAULT 0 COMMENT '父级类别ID，如果无父级，则为0',
    depth        TINYINT UNSIGNED DEFAULT 0 COMMENT '深度，最顶级类别的深度为1，次级为2，以此类推',
    keywords     VARCHAR(255)     DEFAULT '' COMMENT '关键词列表，各关键词使用英文的逗号分隔',
    sort         TINYINT UNSIGNED DEFAULT 0 COMMENT '排序序号',
    icon         VARCHAR(255)     DEFAULT '' COMMENT '图标图片的URL',
    enable       TINYINT UNSIGNED DEFAULT 0 COMMENT '是否启用，1=启用，0=未启用',
    is_parent    TINYINT UNSIGNED DEFAULT 0 COMMENT '是否为父级（是否包含子级），1=是父级，0=不是父级',
    is_display   TINYINT UNSIGNED DEFAULT 0 COMMENT '是否显示在导航栏中，1=启用，0=未启用',
    gmt_create   DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '商城-类别' CHARSET = utf8mb4;

DROP TABLE IF EXISTS mall_goods;
CREATE TABLE mall_goods
(
    id                     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    category_id            BIGINT UNSIGNED  DEFAULT 0 COMMENT '类别ID',
    category_name          VARCHAR(32)      DEFAULT '' COMMENT '类别名称',
    bar_code               VARCHAR(32)      DEFAULT '' COMMENT '商品条形码',
    title                  VARCHAR(255)     DEFAULT '' COMMENT '标题',
    brief                  VARCHAR(255)     DEFAULT '' COMMENT '摘要',
    cover_url              VARCHAR(255)     DEFAULT '' COMMENT '封面图',
    sale_price             DECIMAL(10, 2)   DEFAULT 0 COMMENT '价格',
    keywords               VARCHAR(255)     DEFAULT '' COMMENT '关键词列表，各关键词使用英文的逗号分隔',
    sort                   TINYINT UNSIGNED DEFAULT 0 COMMENT '排序序号',
    is_recommend           TINYINT UNSIGNED DEFAULT 0 COMMENT '是否推荐，0=不推荐，1=推荐',
    check_state            TINYINT UNSIGNED DEFAULT 0 COMMENT '审核状态，0=未审核，1=审核通过，2=拒绝审核',
    is_put_on              TINYINT UNSIGNED DEFAULT 0 COMMENT '是否上架，0=下架，1=上架',
    sales_count            INT UNSIGNED     DEFAULT 0 COMMENT '销量',
    comment_count          INT UNSIGNED     DEFAULT 0 COMMENT '评论数量',
    positive_comment_count INT UNSIGNED     DEFAULT 0 COMMENT '好评数量',
    negative_comment_count INT UNSIGNED     DEFAULT 0 COMMENT '差评数量',
    gmt_create             DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified           DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '商城-商品' CHARSET = utf8mb4;

DROP TABLE IF EXISTS mall_goods_detail;
CREATE TABLE mall_goods_detail
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    goods_id     BIGINT UNSIGNED DEFAULT 0 COMMENT '商品ID',
    detail       TEXT COMMENT '详情',
    gmt_create   DATETIME        DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified DATETIME        DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '商城-商品详情' CHARSET = utf8mb4;

DROP TABLE IF EXISTS mall_comment;
CREATE TABLE mall_comment
(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    author_id    BIGINT UNSIGNED  DEFAULT 0 COMMENT '作者ID',
    author_name  VARCHAR(32)      DEFAULT '' COMMENT '作者名字',
    goods_id     BIGINT UNSIGNED  DEFAULT 0 COMMENT '商品ID',
    type         TINYINT UNSIGNED DEFAULT 0 COMMENT '评论类型：0=好评，1=中评，2=差评',
    content      VARCHAR(512)     DEFAULT '' COMMENT '评论内容',
    check_state  TINYINT UNSIGNED DEFAULT 0 COMMENT '审核状态，0=未审核，1=审核通过，2=拒绝审核',
    gmt_create   DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '商城-评论' CHARSET = utf8mb4;

DROP TABLE IF EXISTS mall_check_log;
CREATE TABLE mall_check_log
(
    id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    resource_type  TINYINT UNSIGNED DEFAULT 0 COMMENT '资源类型，0=商品，1=评论',
    resource_id    BIGINT UNSIGNED  DEFAULT 0 COMMENT '资源ID',
    resource_brief VARCHAR(255)     DEFAULT '' COMMENT '资源摘要，截取的商品标题或评论',
    original_state TINYINT UNSIGNED DEFAULT 0 COMMENT '原审核状态',
    new_state      TINYINT UNSIGNED DEFAULT 0 COMMENT '新审核状态',
    check_user_id  BIGINT UNSIGNED  DEFAULT 0 COMMENT '审核人ID',
    check_username VARCHAR(32)      DEFAULT '' COMMENT '审核人用户名',
    check_remarks  VARCHAR(64)      DEFAULT '' COMMENT '审核备注',
    gmt_check      DATETIME         DEFAULT NULL COMMENT '审核时间',
    gmt_create     DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified   DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '商城-审核日志' CHARSET = utf8mb4;

DROP TABLE IF EXISTS mall_receiver_address;
CREATE TABLE mall_receiver_address
(
    id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    user_id        BIGINT UNSIGNED  DEFAULT 0 COMMENT '用户ID',
    receiver_name  VARCHAR(32)      DEFAULT '' COMMENT '收货人',
    receiver_phone VARCHAR(32)      DEFAULT '' COMMENT '收货电话',
    province       VARCHAR(256)     DEFAULT '' COMMENT '省名称',
    province_code  VARCHAR(32)      DEFAULT '' COMMENT '省编码',
    city           VARCHAR(256)     DEFAULT '' COMMENT '市名称',
    city_code      VARCHAR(32)      DEFAULT '' COMMENT '市编码',
    area           VARCHAR(256)     DEFAULT '' COMMENT '区名称',
    area_code      VARCHAR(32)      DEFAULT '' COMMENT '区编码',
    detail         VARCHAR(128)     DEFAULT '' COMMENT '详细地址',
    is_default     TINYINT UNSIGNED DEFAULT 0 COMMENT '是否默认',
    gmt_create     DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified   DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '商城-收货地址' CHARSET = utf8mb4;

DROP TABLE IF EXISTS mall_order;
CREATE TABLE mall_order
(
    id               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    buyer_id         BIGINT UNSIGNED  DEFAULT 0 COMMENT '用户ID',
    buyer_username   VARCHAR(50)      DEFAULT '' COMMENT '用户名',
    order_no         VARCHAR(50)      DEFAULT '' COMMENT '订单编号',
    receiver_name    VARCHAR(32)      DEFAULT '' COMMENT '收货人',
    receiver_phone   VARCHAR(32)      DEFAULT '' COMMENT '收货电话',
    receiver_address VARCHAR(255)     DEFAULT '' COMMENT '收货地址',
    goods_num        INT UNSIGNED     DEFAULT 0 COMMENT '商品数量',
    total_price      DECIMAL(10, 2)   DEFAULT 0 COMMENT '商品销售总价',
    logistics_no     VARCHAR(50)      DEFAULT '' COMMENT '物流单号',
    pay_channel      INT UNSIGNED     DEFAULT 0 COMMENT '支付渠道：1=支付宝，2=微信',
    pay_method       INT UNSIGNED     DEFAULT 0 COMMENT '支付方式：1=在线支付，2=货到付款',
    order_state      TINYINT UNSIGNED DEFAULT 0 COMMENT '订单状态: 0=待支付，1=已支付，待发货, 2=已发货/待收货，3=确认收货/已完成，4=用户关闭，5=平台关闭(商家)，6=系统调度关闭',
    gmt_pay          DATETIME         DEFAULT NULL COMMENT '支付时间',
    gmt_create       DATETIME         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified     DATETIME         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '商城-订单' CHARSET = utf8mb4;

DROP TABLE IF EXISTS mall_order_item;
CREATE TABLE mall_order_item
(
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据ID',
    order_id        BIGINT UNSIGNED DEFAULT 0 COMMENT '订单ID',
    goods_id        BIGINT UNSIGNED DEFAULT 0 COMMENT '商品ID',
    goods_cover_url VARCHAR(100)    DEFAULT '' COMMENT '商品图片',
    goods_title     VARCHAR(100)    DEFAULT '' COMMENT '商品标题',
    goods_num       INT UNSIGNED    DEFAULT 0 COMMENT '商品数量',
    sale_unit_price DECIMAL(10, 2)  DEFAULT 0 COMMENT '商品单价',
    gmt_create      DATETIME        DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified    DATETIME        DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) COMMENT '商城-订单商品明细' CHARSET = utf8mb4;