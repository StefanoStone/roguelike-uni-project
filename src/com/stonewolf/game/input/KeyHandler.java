package com.stonewolf.game.input;

import com.stonewolf.game.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key defend = new Key();
    public Key esc = new Key();
    public Key enter = new Key();
    public Key control = new Key();

    public KeyHandler(GamePanel game) {
        game.addKeyListener(this);
    }

    public void toggle(KeyEvent e, boolean pressed) {
        if(e.getKeyCode() == KeyEvent.VK_W)
            up.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_S)
            down.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_A)
            left.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_D)
            right.toggle(pressed);

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            esc.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            enter.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_CONTROL)
            control.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            defend.toggle(pressed);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }
}
