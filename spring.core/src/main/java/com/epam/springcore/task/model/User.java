package com.epam.springcore.task.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class User {

    private long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean isActive;

}
