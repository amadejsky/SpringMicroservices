package com.amadejsky.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Dog {
    private Long id;
    private String name;
    @Autowired
    private Toy toy;


    public Toy getToy() {
        return toy;
    }


    public void setToy(Toy toy) {
        this.toy = toy;
    }

    public Dog() {
    }


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

    public void sayHello(){
        System.out.println("Hi!");
        toy.play();
    }
}
