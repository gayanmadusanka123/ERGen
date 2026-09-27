package com.ergen.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Postgres {
    private String name;
    private String username;
    private String password;
    private String host;
    private String port;
}
