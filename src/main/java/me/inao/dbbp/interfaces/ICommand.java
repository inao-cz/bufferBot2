package me.inao.dbbp.interfaces;

import java.util.HashMap;

public interface ICommand {
    boolean process(HashMap<Class<? extends IArgument>, Object> arguments);
}
