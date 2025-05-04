package tech.hljzj.activiti.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends UserSelector {
    private String name;
    private String roleName;

    public static User id(String userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }
}