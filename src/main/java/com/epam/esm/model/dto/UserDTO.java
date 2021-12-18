package com.epam.esm.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends RepresentationModel<UserDTO> {
    private long id;

    @NonNull
    @NotBlank
    @Size(min = 2, max = 25)
    private String firstName;

    @NonNull
    @NotBlank
    @Size(min = 2, max = 25)
    private String lastName;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    private String login;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    @Size(min = 5, max = 15)
    private String password;
}