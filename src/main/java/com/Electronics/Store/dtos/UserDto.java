package com.Electronics.Store.dtos;

import aj.org.objectweb.asm.ConstantDynamic;
import com.Electronics.Store.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {


    private String userId;

    @Size(min = 3,max = 20, message = "invalid Name !!")
    private String name;

   // @Email(message = "invalid user Email !!")
    //regexp mail ex:someone@gmail.com
    @Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}",message = "invalid user email !!")
    @NotBlank(message = "email is not blank !!")
    private String email;

    @NotBlank(message = "password is invalid")
    private String password;

    @Size(min = 4, max = 6, message = "invalid gender !!")
    private String gender;

    @NotBlank(message = "Write something about yourself")
    private String about;

    //@pattern
    //Custom validator

    @ImageNameValid
    private String imageName;


}
