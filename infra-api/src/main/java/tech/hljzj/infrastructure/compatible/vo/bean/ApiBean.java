package tech.hljzj.infrastructure.compatible.vo.bean;

import cn.hutool.json.JSONUtil;

import java.sql.Timestamp;

/**
 * 基础请求返回Bean
 * 版本：1.0
 * 日期：2019年4月3日
 * 署名：王澳
 */
public class ApiBean<R> {
    private int code;
    private String msg;
    private int sub_code;
    private String sub_msg;
    private Timestamp timestamp;
    private String token;
    private String ticket;
    private R data;
    private Long count;
    private Object notice;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getSub_code() {
        return sub_code;
    }

    public void setSub_code(int sub_code) {
        this.sub_code = sub_code;
    }

    public String getSub_msg() {
        return sub_msg;
    }

    public void setSub_msg(String sub_msg) {
        this.sub_msg = sub_msg;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public R getData() {
        return data;
    }

    public void setData(R data) {
        this.data = data;
    }

    public Object getNotice() {
        return notice;
    }

    public void setNotice(Object notice) {
        this.notice = notice;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
