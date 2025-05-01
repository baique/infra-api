package tech.hljzj.infrastructure.compatible.controller.bsae;

public class R<T> extends tech.hljzj.framework.bean.R<T> {
    public int getSub_code() {
        return super.getCode();
    }

    public String getSub_msg() {
        return super.getMsg();
    }
}
