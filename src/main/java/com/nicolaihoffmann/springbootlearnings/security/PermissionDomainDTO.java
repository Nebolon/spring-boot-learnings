package com.nicolaihoffmann.springbootlearnings.security;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermissionDomainDTO {

    @JsonAlias("id")
    String key;
    boolean read       = false;
    boolean write      = false;
    boolean execute    = false;
    boolean supervisor = false;
    String  groupKey;
}
