package com.isc.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSearchRequest implements Serializable {
    private Integer page;
}
