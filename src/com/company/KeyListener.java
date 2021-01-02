package com.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;

public class KeyListener extends KeyAdapter {

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.isControlDown()){
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_O)
                Main.open();
            else if(key == KeyEvent.VK_S)
                Main.checkSave();
            else if(e.isShiftDown() && key == KeyEvent.VK_S)
                Main.save();
            else if(key == KeyEvent.VK_N)
                Main.newFile();
            else if(key == KeyEvent.VK_W)
                Main.close();
            else if(key == KeyEvent.VK_ENTER){
                new PredictWord();
            }
            else if(key == KeyEvent.VK_E){
                PredictNextWord.showPopup();
            }
            else if(key == KeyEvent.VK_Z){
                UndoRedo.Undo();
            }
            else if(key == KeyEvent.VK_Y){
                UndoRedo.Redo();
            }
            else if(key == KeyEvent.VK_F){
                OperationPanel.ff.setVisible(true);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE){

            new Store();
        }
    }
}
