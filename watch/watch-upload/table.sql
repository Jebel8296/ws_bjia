create table UPLOAD_FILE(
  ID bigint not null auto_increment,
  UID bigint not null,
  URI varchar(255) not null comment '路径',
  MIME varchar(255) not null comment 'mime type',
  NAME varchar(255) comment 'label 文件名称',
  SIZE bigint not null comment '大小',
  TYPE varchar(255) comment '类型,各子系统自己定义',
  WEBSITE varchar(255) comment '子系统标识，需要先在文件服务器上注册权限验证地址',
  RIGHT_TYPE enum('PUBLIC', 'PRIVATE') comment '权限类型, PUBLIC: 公开访问, PRIVATE: 需要权限校验',
  UPDATED_AT timestamp not null default current_timestamp on update current_timestamp,
  CREATED_AT datetime not null,
  primary key (ID)
) engine=innodb default charset=utf8 comment '上传文件';