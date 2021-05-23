package top.safun.miaosha.vo;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import top.safun.miaosha.validator.IsMobile;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
