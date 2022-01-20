package me.inao.dbbp.lentils;

import me.inao.dbbp.processing.annotations.Stateful;

@Stateful
public class TestStatefulLentil {
    int i = 0;
    public String getHelloWorld(){
        i++;
        return "Hello World.." + i;
    }
}
