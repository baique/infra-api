alter table sys_app_ add column verify_ip_ char(1) comment '是否校验授信IP';
update sys_app_ set verify_ip_ = '0';
alter table sys_app_ add column trust_ip_ text comment '信任IP';