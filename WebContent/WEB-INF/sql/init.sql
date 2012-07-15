-------------archivedb 初始化数据 --------------
-------------适用于Hsqldb 2.0

--sys_config 系统配置表
INSERT INTO SYS_CONFIG (CONFIGID, CONFIGKEY, CONFIGVALUE, CONFIGMEMO,CONFIGNAME ) VALUES ('1','PASSWORD', 'password','创建账户时，默认的初始化密码','初始密码' );


--sys_ACCOUNT 帐户表
INSERT INTO SYS_ACCOUNT (ACCOUNTID, ACCOUNTCODE, PASSWORD, ACCOUNTSTATE,ACCOUNTMEMO ) VALUES ('1','admin', '5f4dcc3b5aa765d61d8327deb882cf99',1,'超级帐户' );

--sys_org 帐户组
INSERT INTO SYS_ORG (ORGID,ORGNAME,PARENTID,ORGORDER) VALUES ('1','档案资料管理系统',0,1);
INSERT INTO SYS_ORG (ORGID,ORGNAME,PARENTID,ORGORDER) VALUES ('2','默认组',1,2);

--sys_role 角色表
INSERT INTO SYS_role (ROLEID,ROLENAME,ROLEMEMO) VALUES ('1','一般用户','系统初始化基础角色');

--sys_function 功能菜单表
insert into sys_function(FUNCTIONID,FUNCHINESENAME,FUNENGLISHNAME,FUNPATH,FUNORDER,FUNSYSTEM,FUNPARENT,FUNICON) values ('1','系统维护','SYSTEMP','',-1,1,0,,'icon-sysmanage');
insert into sys_function(FUNCTIONID,FUNCHINESENAME,FUNENGLISHNAME,FUNPATH,FUNORDER,FUNSYSTEM,FUNPARENT,FUNICON) values ('2','系统配置','CONFIG','webpage/system/config/ConfigMgr.html',1,1,1,'');
insert into sys_function(FUNCTIONID,FUNCHINESENAME,FUNENGLISHNAME,FUNPATH,FUNORDER,FUNSYSTEM,FUNPARENT,FUNICON) values ('3','帐户管理','ACCOUNT','webpage/system/account/AccountMgr.html',2,1,1.'icon-user');
insert into sys_function(FUNCTIONID,FUNCHINESENAME,FUNENGLISHNAME,FUNPATH,FUNORDER,FUNSYSTEM,FUNPARENT,FUNICON) values ('4','档案库维护','TEMPLET','webpage/archive/templet/TempletMgr.html',3,1,1.'icon-table');
insert into sys_function(FUNCTIONID,FUNCHINESENAME,FUNENGLISHNAME,FUNPATH,FUNORDER,FUNSYSTEM,FUNPARENT,FUNICON) values ('5','档案管理','ARCHIVEMANAGE','',-1,1,0,'icon-page-gear');
insert into sys_function(FUNCTIONID,FUNCHINESENAME,FUNENGLISHNAME,FUNPATH,FUNORDER,FUNSYSTEM,FUNPARENT,FUNICON) values ('6','档案管理','ARCHIVE','webpage/archive/archive/ArchiveMgr.html',1,1,5,'icon-page');


--sys_TEMPLET 模板表
INSERT INTO SYS_TEMPLET (TEMPLETid,TEMPLETNAME, TEMPLETTYPE) VALUES ('1','基础标准模板','CA');
INSERT INTO SYS_TEMPLET (TEMPLETid,TEMPLETNAME, TEMPLETTYPE) VALUES ('2','基础文件模板','CF');

--sys_table 表名管理表
INSERT INTO sys_table (TABLEID,TABLENAME, TEMPLETID,TABLELABEL,TABLETYPE) VALUES ('1','C_0001_01','1','基础标准模板_案卷级','01');
INSERT INTO sys_table (TABLEID,TABLENAME, TEMPLETID,TABLELABEL,TABLETYPE) VALUES ('2','C_0001_02','1','基础标准模板_文件级','02');
INSERT INTO sys_table (TABLEID,TABLENAME, TEMPLETID,TABLELABEL,TABLETYPE) VALUES ('3','C_0002_01','2','基础文件模板_文件级','01');

--sys_TEMPLETFIELD 模板字段管理表  案卷级
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('1', 'ID', '表ID',40, 'VARCHAR',1 ,'',1 , 0,0 , 0, -1, 0,0 , 1, '','1');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('2', 'TREEID', '节点ID',40, 'VARCHAR',0 ,'',0 , 0,0 , 0,-1, 0,0 , 1, '','1');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('3', 'ISDOC', '是否有全文',11, 'INT',0 ,'',0 , 0,0 , 0,-1, 0,0 , 1, '','1');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('4', 'AJH', '案卷号',100, 'VARCHAR',0 ,'',0 , 0,1 , 1, 1, 0,0 , 1, '','1');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('5', 'GDDW', '归档单位',80, 'VARCHAR',0 ,'',0 , 0,1 , 1,2, 0,0 , 1, '','1');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('6', 'ZRZ', '责任者',100, 'VARCHAR',0 ,'',0 , 0,1 , 1,3, 0,0 , 1, '','1');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('7', 'TM', '题名',200, 'VARCHAR',0 ,'',0 , 0,1 , 1,4	, 0,0 , 1, '','1');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('8', 'FS', '份数',11, 'INT',0 ,'0',0 , 0,0 , 1,5	, 0,0 , 1, '','1');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('9', 'YS', '页数',11, 'INT',0 ,'0',0 , 0,0 , 1,6	, 0,0 , 1, '','1');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('10','GDRQ','归档日期',60,'VARCHAR',0,'',0,0,1,1,7,0,0,1,'','1');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('11','BZ','备注',200,'VARCHAR',0,'',0,0,1,1,8,0,0,1,'','1');
--文件级
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('21', 'ID', '表ID',40, 'VARCHAR',1 ,'',1 , 0,0 , 0, -1, 0,0 , 1, '','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('22', 'PARENTID', '父节点ID',40, 'VARCHAR',0 ,'',0 , 0,0 , 0, -1, 0,0 , 1, '','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('23', 'TREEID', '节点ID',40, 'VARCHAR',0 ,'',0 , 0,0 , 0,-1, 0,0 , 1, '','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('24', 'ISDOC', '是否有全文',11, 'INT',0 ,'',0 , 0,0 , 0,-1, 0,0 , 1, '','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('25', 'WJH', '文件号',100, 'VARCHAR',0 ,'',0 , 0,1 , 1, 1, 0,0 , 1, '','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('26', 'AJH', '案卷号',100, 'VARCHAR',0 ,'',0 , 0,1 , 1, 2, 0,0 , 1, '','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('27', 'GDDW', '归档单位',80, 'VARCHAR',0 ,'',0 , 0,1 , 1,2, 0,0 , 1, '','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('28', 'ZRZ', '责任者',100, 'VARCHAR',0 ,'',0 , 0,1 , 1,3, 0,0 , 1, '','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('29', 'TM', '题名',200, 'VARCHAR',0 ,'',0 , 0,1 , 1,4	, 0,0 , 1, '','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('30', 'FS', '份数',11, 'INT',0 ,'0',0 , 0,0 , 1,5	, 0,0 , 1, '','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('31', 'YS', '页数',11, 'INT',0 ,'0',0 , 0,0 , 1,6	, 0,0 , 1, '','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('32','GDRQ','归档日期',60,'VARCHAR',0,'',0,0,1,1,7,0,0,1,'','2');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('33','BZ','备注',200,'VARCHAR',0,'',0,0,1,1,8,0,0,1,'','2');

--纯文件级
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('51', 'ID', '表ID',40, 'VARCHAR',1 ,'',1 , 0,0 , 0, -1, 0,0 , 1, '','3') ;
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('52', 'TREEID', '节点ID',40, 'VARCHAR',0 ,'',0 , 0,0 , 0,-1, 0,0 , 1, '','3');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('53', 'ISDOC', '是否有全文',11, 'INT',0 ,'',0 , 0,0 , 0,-1, 0,0 , 1, '','3');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('54', 'WJH', '文件号',100, 'VARCHAR',0 ,'',0 , 0,1 , 1, 1, 0,0 , 1, '','3');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('55', 'GDDW', '归档单位',80, 'VARCHAR',0 ,'',0 , 0,1 , 1,2, 0,0 , 1, '','3');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('56', 'ZRZ', '责任者',100, 'VARCHAR',0 ,'',0 , 0,1 , 1,3, 0,0 , 1, '','3');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('57', 'TM', '题名',200, 'VARCHAR',0 ,'',0 , 0,1 , 1,4	, 0,0 , 1, '','3');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('58', 'FS', '份数',11, 'INT',0 ,'0',0 , 0,0 , 1,5	, 0,0 , 1, '','3');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('59', 'YS', '页数',11, 'INT',0 ,'0',0 , 0,0 , 1,6	, 0,0 , 1, '','3');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('60','GDRQ','归档日期',60,'VARCHAR',0,'',0,0,1,1,7,0,0,1,'','3');
INSERT INTO SYS_TEMPLETFIELD
(TEMPLETFIELDID, ENGLISHNAME, CHINESENAME, FIELDSIZE, FIELDTYPE, ISPK, DEFAULTVALUE, ISREQUIRE, ISUNIQUE, ISSEARCH, ISGRIDSHOW, SORT, ISEDIT, ISCODE, ISSYSTEM, FIELDCSS,TABLEID )
VALUES ('61','BZ','备注',200,'VARCHAR',0,'',0,0,1,1,8,0,0,1,'','3');
