
set names utf8;

drop table if exists t_resume_info;
create TABLE t_resume_info(
	`id`  bigint(16) NOT NULL AUTO_INCREMENT ,
`name`  varchar(255) NULL DEFAULT NULL COMMENT '名字' ,
`age`  varchar(255)  NULL DEFAULT NULL COMMENT '年龄' ,
`birth_date`  varchar(255)  NULL DEFAULT NULL COMMENT '生日' ,
`gender`  enum('f','m') NULL DEFAULT NULL COMMENT '性别' ,
`position`  varchar(500)  NULL DEFAULT NULL COMMENT '职位' ,
`phone`  varchar(100)  NULL DEFAULT NULL COMMENT '手机号' ,
`citizenship`  varchar(100)  NULL DEFAULT NULL COMMENT '国籍' ,
`education`  varchar(500) NULL DEFAULT NULL COMMENT '教育背景' ,
`major`  varchar(500) NULL DEFAULT NULL COMMENT '主修课' ,
`country`  varchar(500) NULL DEFAULT NULL COMMENT '教育所在国家' ,
`certification`  varchar(500) NULL DEFAULT NULL COMMENT '证书' ,
`specialized`  varchar(500) NULL DEFAULT NULL COMMENT '专业技能' ,
`experience_length`  varchar(500) NULL DEFAULT NULL COMMENT '工作年限' ,
`other_positions`  varchar(500) NULL DEFAULT NULL COMMENT '其他职位' ,
`salary`  varchar(500) NULL DEFAULT NULL COMMENT '期望薪资' ,
`location`  varchar(500) NULL DEFAULT NULL COMMENT '期望地点' ,
`recommended`  varchar(500) NULL DEFAULT NULL COMMENT '推荐人' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`creator_id`  bigint(16)  NULL DEFAULT NULL COMMENT '新建用户' ,
`updater_id`  bigint(16)  NULL DEFAULT NULL COMMENT '更新用户' ,
PRIMARY KEY (`id`),
KEY `idx_creator_id` (`creator_id`)
);


drop table if exists t_resume_file;
create TABLE t_resume_file(
	`id`  bigint(16) NOT NULL AUTO_INCREMENT ,
  `resume_id`  bigint(16) NOT NULL COMMENT '简历id', 
  `user_id`  bigint(16) NOT NULL COMMENT '用户id', 
  `file_address`  varchar(500)  NULL DEFAULT NULL COMMENT '文件地址' ,
  `file_type`  varchar(500)  NULL DEFAULT NULL COMMENT '文件类型' ,
  `file_name`  varchar(500)  NULL DEFAULT NULL COMMENT '文件名称' ,
  `type`  int  NULL DEFAULT 0 COMMENT '业务类型 0:简历，1:自荐视频' ,
  `deleted` enum('0','1')  NULL DEFAULT '0' COMMENT '是否被删除' ,
  `downloaded` enum('0','1')  NULL DEFAULT '0' COMMENT '是否下载过' ,
  PRIMARY KEY (`id`),
  KEY `idx_resume_id_type` (`resume_id`,`type`)
);

drop table if exists t_interview_flow;
create TABLE t_interview_flow(
	`id`  bigint(16) NOT NULL AUTO_INCREMENT ,
	`resume_id` bigint(16) NULL DEFAULT NULL COMMENT '简历id' ,
	`step`  int(11) NULL DEFAULT NULL COMMENT '状态' ,
	`received`  enum('0','1')  NULL DEFAULT NULL COMMENT '是否收到offer 0：未收到 1：收到' ,
	`accepted`  enum('0','1')  NULL DEFAULT NULL COMMENT '是否接受offer 0：不接受 1：接受' ,
	`refused`  enum('0','1')  NULL DEFAULT NULL COMMENT '是否被拒绝 0：没有 1：拒绝' ,
	`arrived_date`  datetime  NULL DEFAULT NULL COMMENT '抵达中国日期' ,
	`visa_date`  datetime  NULL DEFAULT NULL COMMENT '签证日期' ,
	`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
	`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`creator_id`  bigint(16)  NULL DEFAULT NULL COMMENT '新建用户' ,
	`updater_id`  bigint(16)  NULL DEFAULT NULL COMMENT '更新用户' ,
	PRIMARY KEY (`id`)
);

drop table if exists t_comment;
create TABLE t_comment(
	`id`  bigint(16) NOT NULL AUTO_INCREMENT ,
	`code`  varchar(255) NULL DEFAULT NULL COMMENT '唯一编码' ,
	`content`  text NULL DEFAULT NULL COMMENT '内容' ,
	PRIMARY KEY (`id`)
);

drop table if exists t_user;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `email` varchar(255) default NULL,
  `username` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `nickname` varchar(255) default NULL,
  `locked` smallint(6) default NULL,
  `phone` varchar(255) default NULL,
  `role` enum('MEMEBER','EMPLOYEE','ADMIN') default 'MEMEBER',
  PRIMARY KEY  (`id`)
);

drop table if exists t_role;
create table t_role(
 `id` bigint(20) NOT NULL auto_increment,
  `role` varchar(255) default NULL,
  `role_name` varchar(255) default NULL,
  `role_key` varchar(255) default NULL,
   PRIMARY KEY  (`id`)
);
drop table if exists t_user_role;
create table t_user_role(
	`id` bigint(20) NOT NULL auto_increment,
	`user_id` bigint(20) NOT NULL ,
	`role_id` bigint(20) NOT NULL ,
	 PRIMARY KEY  (`id`)
 );
 drop table if exists t_resources;
 create table t_resources(
	`id` bigint(20) NOT NULL auto_increment,
	`name` varchar(255) NULL default null ,
	`parent_id` bigint(20) NOT NULL default 0 ,
	`url`  varchar(255)  default NULL,
	`res_type`  int(11)  NOT NULL default 0 COMMENT '资源类型 0：菜单 1：事件' ,
	 PRIMARY KEY  (`id`)
 );
 drop table if exists t_res_role;
create table t_res_role(
	`id` bigint(20) NOT NULL auto_increment,
	`user_id` bigint(20) NOT NULL ,
	`role_id` bigint(20) NOT NULL ,
	 PRIMARY KEY  (`id`)
 )
 ;
  drop table if exists t_feedback;
 create table t_feedback(
 	`id` bigint(20) NOT NULL auto_increment,
 	`name` varchar(255) NULL default null ,
 	`email` varchar(255) NULL default null ,
 	`title` text NULL default null ,
 	`message` text NULL default null ,
 	`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
	 PRIMARY KEY  (`id`)
 );
 
 drop table if exists t_massage;
 create table t_massage(
 	`id` bigint(20) NOT NULL auto_increment,
 	`from_user_id` bigint(20) NOT NULL ,
 	`to_user_id` bigint(20) NOT NULL ,
 	`resume_id` bigint(20) NOT NULL ,
 	`title` text NULL default null ,
 	`message` text NULL default null ,
 	`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
 	
	 PRIMARY KEY  (`id`)
 );









