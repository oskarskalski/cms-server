package com.oskarskalski.cms.global.model.dto;

import com.oskarskalski.cms.content.user.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest extends UserDto {
    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;
}
