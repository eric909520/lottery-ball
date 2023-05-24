package com.backend.project.system.domain.openApiVo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录授权vo
 */
@Data
public class AuthVo implements Serializable {

    private String mobile;

    private String password;

}
