package tech.hljzj.infrastructure.compatible.vo.department;

import java.io.Serializable;
import java.util.Objects;

public class ManagerDept  implements Serializable {
    private String id;
    private boolean allUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAllUser() {
        return allUser;
    }

    public void setAllUser(boolean allUser) {
        this.allUser = allUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagerDept that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
