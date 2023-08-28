package com.Electronics.Store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Min(value = 4, message = "title must be present !!")
    private String title;

    @NotBlank(message = "description must be required !!")
    private String description;

    private String coverImage;
}
