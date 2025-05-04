package tech.hljzj.activiti.pojo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int status = 200;
    private Object data;
    private String message;

    public int getStatus() {
        return status == 0 ? 200 : status;
    }
}
