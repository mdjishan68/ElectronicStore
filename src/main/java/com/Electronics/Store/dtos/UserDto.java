package com.Electronics.Store.dtos;

import aj.org.objectweb.asm.ConstantDynamic;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {


    private String userId;

    private String name;

    private String email;

    private String password;

    private String gender;

    private String about;

    private String imageName;


}
