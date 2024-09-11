package com.terminalvelocitycabbage.game.common.events;

import com.terminalvelocitycabbage.engine.event.Event;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.translation.Localizer;

public class ModLocalizedTextRegistryEvent extends Event {

    public static final Identifier EVENT = new Identifier("game", "modLocalizedTextRegistryEvent");

    Localizer localizer;

    public ModLocalizedTextRegistryEvent(Identifier name, Localizer localizer) {
        super(name);
        this.localizer  = localizer;
    }

    public Localizer getLocalizer() {
        return localizer;
    }
}
