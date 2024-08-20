package com.amadejsky.demo;

import org.springframework.stereotype.Component;

@Component
public class Toy {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void play(){
        System.out.println("test toy");
    }
}
