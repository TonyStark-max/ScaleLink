package com.URL.ScaleLink.ValidationDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqDTO {
    @NotBlank(message = "URL must not be empty")
    @URL(message = "Invalid URL format")
    private String originalUrl;
}
