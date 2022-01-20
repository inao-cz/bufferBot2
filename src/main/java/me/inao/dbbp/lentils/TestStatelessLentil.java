package me.inao.dbbp.lentils;

import me.inao.dbbp.processing.annotations.Stateless;

@Stateless
public class TestStatelessLentil {
    int i = 0;
    public String getHelloWorld(){
        i++;
        return "Hello World.." + i;
    }
}
