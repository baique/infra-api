package tech.hljzj.infrastructure.compatible.controller.bsae;

import cn.hutool.core.util.StrUtil;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class R<T> extends tech.hljzj.framework.bean.R<T> {
    private int subCode;
    private String subMsg;

    public int getSub_code() {
        if (subCode == 0) {
            return super.getCode();
        }
        return subCode;
    }

    public String getSub_msg() {
        if (StrUtil.isBlank(subMsg)) {
            return super.getMsg();
        }
        return subMsg;
    }
}
