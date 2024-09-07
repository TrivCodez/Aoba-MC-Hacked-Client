package net.aoba.gui.colors;

import net.aoba.event.events.TickEvent;

public class RandomColor extends AnimatedColor{
    public RandomColor() {
        super();
    }

    @Override
    public void OnUpdate(TickEvent event) {
        this.setHSV(((float) (Math.random() * 360f)), 1f, 1f);
    }
}
