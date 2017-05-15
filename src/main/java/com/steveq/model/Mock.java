package com.steveq.model;

import org.springframework.context.annotation.Bean;

/**
 * Created by Adam on 2017-05-15.
 */
public class Mock {
    private String name;

    public Mock(){
        name = "my mock";
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
