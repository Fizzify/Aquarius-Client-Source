package me.fizzify.aquariusclient.module.impl;

import me.fizzify.aquariusclient.module.Category;
import me.fizzify.aquariusclient.module.Module;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ClicksPerSecond extends Module {

    private final List<Long> leftClicks = new ArrayList<Long>();
    private final List<Long> rightClicks = new ArrayList<Long>();
    private boolean leftWasPressed;
    private boolean rightWasPressed;
    private long leftLastPress;
    private long rightLastPress;

    public ClicksPerSecond() {
        super("CPS", "Shows your CPS!", Category.HUD, 50, 50);
    }

    @Override
    public void draw() {
        final boolean leftPressed = Mouse.isButtonDown(0);
        final boolean rightPressed = Mouse.isButtonDown(1);
        if (leftPressed != this.leftWasPressed) {
            this.leftWasPressed = leftPressed;
            this.leftLastPress = System.currentTimeMillis();
            if (leftPressed) {
                this.leftClicks.add(this.leftLastPress);
            }
        }
        final int leftCps = this.getLeftCPS();
        if (rightPressed != this.rightWasPressed) {
            this.rightWasPressed = rightPressed;
            this.rightLastPress = System.currentTimeMillis();
            if (rightPressed) {
                this.rightClicks.add(this.rightLastPress);
            }
        }
        final int rightCps = this.getRightCPS();
        fr.drawStringWithShadow("[" + leftCps + " §8|§f " + rightCps + " " + "CPS" + "]", getX(), getY(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        fr.drawStringWithShadow("[0 | 0 CPS]", getX(), getY(), -1);
        super.renderDummy(mouseX, mouseY);
    }

    @Override
    public int getWidth() {
        return fr.getStringWidth("[0 | 0 CPS]");
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }

    private int getLeftCPS() {
        final long time = System.currentTimeMillis();
        this.leftClicks.removeIf(aLong -> aLong + 1000L < time);
        return this.leftClicks.size();
    }

    private int getRightCPS() {
        final long time = System.currentTimeMillis();
        this.rightClicks.removeIf(aLong -> aLong + 1000L < time);
        return this.rightClicks.size();
    }

}
