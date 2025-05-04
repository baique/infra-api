package tech.hljzj.infrastructure.compatible.controller.bsae;

import tech.hljzj.framework.base.BaseController;

import java.util.Collection;

public class MController extends BaseController {
    @Override
    protected <T> R<T> success(T data) {
        R<T> d = new R<>();
        d.setCode(200);
        d.setData(data);
        if (data instanceof Collection<?>) {
            d.addProperty("count", ((Collection<?>) data).size());
        }
        return d;
    }
}
