package tech.hljzj.infrastructure.compatible.vo.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页参数类
 *
 * @author wa
 */
@Getter
@Setter
public class PageUtil {
    int page;
    int limit;
    boolean usePage = true;

    public PageUtil() {

    }

    public PageUtil(int page, int limit) {
        if (page < 1) {
            page = 1;
        }
        if (limit < 1) {
            limit = 10;
        }
        this.page = page;
        this.limit = limit;
    }

    public boolean isPage() {
        if (this.page == 0 && this.limit == 0) {
            this.usePage = false;
        }
        return this.usePage;
    }
}
