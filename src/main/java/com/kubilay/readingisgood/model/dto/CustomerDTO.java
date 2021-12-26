package com.kubilay.readingisgood.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @Null
    private Long id;

    @NotNull
    private Long customerNo;

    @NotBlank(message = "{validation.customer.name.notBlank}")
    private String name;

    @NotBlank(message = "{validation.customer.surname.notBlank}")
    private String surname;

    @Email
    private String email;
}
