SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`
(
    `id`         bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`      varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '标题',
    `content`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NULL COMMENT '内容',
    `tags`       varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '标签列表（json 数组）',
    `thumbNum`   int                                                            NOT NULL DEFAULT 0 COMMENT '点赞数',
    `favourNum`  int                                                            NOT NULL DEFAULT 0 COMMENT '收藏数',
    `userId`     bigint                                                         NOT NULL COMMENT '创建用户 id',
    `createTime` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`   tinyint                                                        NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_userId` (`userId` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '帖子'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for post_favour
-- ----------------------------
DROP TABLE IF EXISTS `post_favour`;
CREATE TABLE `post_favour`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `postId`     bigint   NOT NULL COMMENT '帖子 id',
    `userId`     bigint   NOT NULL COMMENT '创建用户 id',
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_postId` (`postId` ASC) USING BTREE,
    INDEX `idx_userId` (`userId` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci COMMENT = '帖子收藏'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for post_thumb
-- ----------------------------
DROP TABLE IF EXISTS `post_thumb`;
CREATE TABLE `post_thumb`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `postId`     bigint   NOT NULL COMMENT '帖子 id',
    `userId`     bigint   NOT NULL COMMENT '创建用户 id',
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_postId` (`postId` ASC) USING BTREE,
    INDEX `idx_userId` (`userId` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci COMMENT = '帖子点赞'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`
(
    `id`          bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`       varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '标题',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NULL COMMENT '内容',
    `tags`        varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '标签列表（json 数组）',
    `difficulty`  int                                                            NOT NULL COMMENT '题目难度（1:简单, 2:中等, 3:困难）',
    `answer`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NULL COMMENT '题目答案',
    `subMitNum`   int                                                            NOT NULL DEFAULT 0 COMMENT '题目提交数',
    `acceptedNum` int                                                            NOT NULL DEFAULT 0 COMMENT '题目通过数',
    `judgeCase`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NULL COMMENT '判题用例（json 数组）',
    `judgeConfig` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NULL COMMENT '判题配置（json 对象）',
    `thumbNum`    int                                                            NOT NULL DEFAULT 0 COMMENT '点赞数',
    `favourNum`   int                                                            NOT NULL DEFAULT 0 COMMENT '收藏数',
    `userId`      bigint                                                         NOT NULL COMMENT '创建用户 id',
    `createTime`  datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime`  datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`    tinyint                                                        NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_userId` (`userId` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1828299505034358786
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '题目'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for question_submit
-- ----------------------------
DROP TABLE IF EXISTS `question_submit`;
CREATE TABLE `question_submit`
(
    `id`         bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `language`   varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '编程语言',
    `code`       text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci         NOT NULL COMMENT '用户代码',
    `judgeInfo`  text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci         NULL COMMENT '判题信息（json 对象）',
    `status`     int                                                           NOT NULL DEFAULT 0 COMMENT '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
    `questionId` bigint                                                        NOT NULL COMMENT '题目 id',
    `userId`     bigint                                                        NOT NULL COMMENT '提交用户 id',
    `createTime` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`   tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_postId` (`questionId` ASC) USING BTREE,
    INDEX `idx_userId` (`userId` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1828304541860868097
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci COMMENT = '题目提交'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for solution_details
-- ----------------------------
DROP TABLE IF EXISTS `solution_details`;
CREATE TABLE `solution_details`
(
    `id`             bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键 id',
    `userId`         bigint                                                       NOT NULL COMMENT '用户 id',
    `problemId`      bigint                                                       NOT NULL COMMENT '题目 id',
    `code`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci        NOT NULL COMMENT '用户代码',
    `codeLanguage`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '代码语言',
    `content`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci        NULL COMMENT '题解描述',
    `likesCount`     int                                                          NOT NULL DEFAULT 0 COMMENT '点赞数',
    `favoritesCount` int                                                          NOT NULL DEFAULT 0 COMMENT '收藏数',
    `commentsCount`  int                                                          NOT NULL DEFAULT 0 COMMENT '评论数',
    `viewsCount`     int                                                          NOT NULL DEFAULT 0 COMMENT '查看数',
    `publishTime`    datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `modifyTime`     datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `isDeleted`      tinyint(1)                                                   NOT NULL DEFAULT 0 COMMENT '是否删除 1-删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_userId_problemId` (`userId` ASC, `problemId` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '题解'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`           bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `userAccount`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '账号',
    `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '密码',
    `unionId`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '微信开放平台id',
    `mpOpenId`     varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '公众号openId',
    `userName`     varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '用户昵称',
    `userAvatar`   varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '用户头像',
    `userProfile`  varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '用户简介',
    `userRole`     varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
    `createTime`   datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime`   datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`     tinyint                                                        NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_unionId` (`unionId` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1811011229393649666
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户'
  ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
