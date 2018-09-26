package io.github.rxcats.jwtdemo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class User {

    String userId;

    String email;

}
