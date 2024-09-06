package com.epam.springcore.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    private long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    @JsonProperty("isActive")
    private boolean isActive;

}
