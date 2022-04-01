package me.inao.dbbp.lentil;


import me.inao.dbbp.interfaces.ILentilContainer;

public class StatefulLentilContainer implements ILentilContainer {

    private Object lentil;

    @Override
    public Object getLentil() {
        return lentil;
    }

    @Override
    public void setLentil(Object lentil) {
        this.lentil = lentil;
    }
}
