package me.fizzify.aquariusclient.module.impl.itemphysics;

import me.fizzify.aquariusclient.module.Category;
import me.fizzify.aquariusclient.module.Module;

public class ItemPhysics extends Module {

    public ItemPhysics() {
        super("Item Physics", "Changes the animation of the item.", Category.WORLD,0, 0);
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }
}
