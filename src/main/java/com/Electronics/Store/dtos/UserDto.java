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


    public ConstantDynamic userDto;
    private String userId;

    private String name;

    private String Email;

    private String password;

    private String gender;

    private String about;

    private String imageName;


}
