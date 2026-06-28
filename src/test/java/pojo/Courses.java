package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Courses {
    private List<Api> api;
    private List<Mobile> mobile;
    private List<WebAutomation> webAutomation;
}
