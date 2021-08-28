package me.fizzify.aquariusclient.module.impl;

import me.fizzify.aquariusclient.module.Category;
import me.fizzify.aquariusclient.module.Module;

public class OldAnimations extends Module {

    public OldAnimations()
    {
        super("Old Animations", "Changes your animations to legacy", Category.WORLD, 0, 0);
    }

    @Override
    public int getWidth()
    {
        return 0;
    }

    @Override
    public int getHeight()
    {
        return 0;
    }
}
