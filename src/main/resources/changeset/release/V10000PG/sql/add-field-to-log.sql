ALTER TABLE sys_log_ ADD COLUMN cost_time_ INTEGER;
COMMENT ON COLUMN sys_log_.cost_time_ IS '操作耗时';
