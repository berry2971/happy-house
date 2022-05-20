package com.ssafy.happyhouse.domain.entity;

public class Role {

    private Long id;
    private EnumRole name;

    public Role(Long id, EnumRole name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumRole getName() {
        return name;
    }

    public void setName(EnumRole name) {
        this.name = name;
    }

}
