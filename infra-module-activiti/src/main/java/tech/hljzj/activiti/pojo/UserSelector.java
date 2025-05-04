package tech.hljzj.activiti.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserSelector {
    private Serializable id;
    private Serializable roleId;
}