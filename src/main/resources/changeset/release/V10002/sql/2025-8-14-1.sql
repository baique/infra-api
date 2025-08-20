drop index idx_log_op_time on sys_log_;
create index idx_log_op_time on sys_log_ (op_time_ desc);