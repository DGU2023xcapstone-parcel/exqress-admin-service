package com.exqress.adminservice.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestAdmin {
    @NotBlank(message = "아이디를 입력해주세요")
    private String loginId;
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
