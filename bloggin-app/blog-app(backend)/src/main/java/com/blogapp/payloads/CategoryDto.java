package com.blogapp.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Integer categoryId;

    @NotBlank(message = "Category name must not be blank")
    @Size(min = 3, max = 100, message = "Category name must be between 3 and 100 characters")
    private String categoryName;

    @NotBlank(message = "Category description must not be blank")
    private String categoryDescription;
}
