package me.inao.dbbp.processing.lentil;


import me.inao.dbbp.processing.interfaces.ILentilContainer;

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
