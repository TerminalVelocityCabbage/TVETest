package com.terminalvelocitycabbage.game.client;

import com.terminalvelocitycabbage.editor.Editor;

public class GameEditor extends Editor<GameClient> {

    public GameEditor() {
        super(new GameClient());
    }

    public static void main(String[] args) {
        GameEditor editor = new GameEditor();
        editor.start();
    }

}
