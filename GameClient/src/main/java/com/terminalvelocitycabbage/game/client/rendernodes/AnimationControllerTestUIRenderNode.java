package com.terminalvelocitycabbage.game.client.rendernodes;

import com.terminalvelocitycabbage.engine.client.renderer.shader.ShaderProgramConfig;
import com.terminalvelocitycabbage.engine.client.ui.UI;
import com.terminalvelocitycabbage.engine.client.ui.UIRenderNode;
import com.terminalvelocitycabbage.engine.registry.Identifier;
import com.terminalvelocitycabbage.engine.util.Color;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.templates.ecs.components.VelocityComponent;
import com.terminalvelocitycabbage.templates.events.UICharInputEvent;
import com.terminalvelocitycabbage.templates.events.UIClickEvent;
import com.terminalvelocitycabbage.templates.events.UIScrollEvent;

import static com.terminalvelocitycabbage.engine.client.ui.UI.UIUnit.PIXELS;
import static com.terminalvelocitycabbage.game.client.registry.GameFonts.LEXEND_FONT;

public class AnimationControllerTestUIRenderNode extends UIRenderNode {

    public AnimationControllerTestUIRenderNode(ShaderProgramConfig shaderProgramConfig) {
        super(shaderProgramConfig);
    }

    @Override
    protected Identifier[] getInterestedEvents() {
        return new Identifier[] { UIClickEvent.EVENT, UIScrollEvent.EVENT, UICharInputEvent.EVENT };
    }

    @Override
    protected void declareUI() {
        container(props(UI.direction(UI.LayoutDirection.TOP_TO_BOTTOM)), () -> {
            speedVariable("Speed", 0);
            speedVariable("Left", 1);
            speedVariable("Right", 2);
        });
    }

    private void speedVariable(String label, int component) {

        //This will work until there is another entity, but that's fine for now
        var rexEntity = GameClient.getInstance().getManager().getFirstEntityWith(VelocityComponent.class);

        var value = useState(rexEntity.getComponent(VelocityComponent.class).getVelocity().get(component));

        float incrementAmount = 0.1f;

        var increaseID = id(label + "_increase");
        var decreaseID = id(label + "_decrease");

        if (heardEvent(increaseID, UIClickEvent.EVENT) instanceof UIClickEvent) {
            value.setValue(value.getValue() + incrementAmount);
            rexEntity.getComponent(VelocityComponent.class).getVelocity().setComponent(component, value.getValue());
        }
        if (heardEvent(decreaseID, UIClickEvent.EVENT) instanceof UIClickEvent) {
            value.setValue(value.getValue() - incrementAmount);
            rexEntity.getComponent(VelocityComponent.class).getVelocity().setComponent(component, value.getValue());
        }

        container(props(
                UI.direction(UI.LayoutDirection.LEFT_TO_RIGHT),
                UI.m(5, PIXELS)
        ), () -> {
            container(decreaseID, props(
                    UI.p(5, PIXELS),
                    UI.backgroundColor(new Color(0f, 0f, 0f, 1f)),
                    UI.width(60, PIXELS)
            ), () -> {
                text(label,
                        props(
                                UI.alignX(UI.HorizontalAlignment.RIGHT),
                                UI.textSize(12, PIXELS),
                                UI.font(LEXEND_FONT),
                                UI.textColor(new Color(1f, 1f, 1f, 1f)),
                                UI.width(20, PIXELS),
                                UI.height(20, PIXELS)
                        ));
            });
            container(decreaseID, props(
                    UI.p(5, PIXELS),
                    UI.backgroundColor(isHovered(decreaseID) ? new Color(0f, 0f, 0f, 1f) : new Color(0.2f, 0.2f, 0.2f, 1f))
            ), () -> {
                text("-",
                        props(
                                UI.textSize(12, PIXELS),
                                UI.font(LEXEND_FONT),
                                UI.textColor(new Color(1f, 1f, 1f, 1f)),
                                UI.width(20, PIXELS),
                                UI.height(20, PIXELS)
                        ));
            });
            container(id("current_" + label), props(
                    UI.p(5, PIXELS),
                    UI.backgroundColor(new Color(0f, 0f, 0f, 1f))
            ), () -> {
                text(String.valueOf((Math.round(value.getValue() * 10.0) / 10.0)),
                        props(
                                UI.textSize(12, PIXELS),
                                UI.font(LEXEND_FONT),
                                UI.textColor(new Color(1f, 1f, 1f, 1f))
                        ));
            });
            container(increaseID, props(
                    UI.p(5, PIXELS),
                    UI.backgroundColor(isHovered(increaseID) ? new Color(0f, 0f, 0f, 1f) : new Color(0.2f, 0.2f, 0.2f, 1f))
            ), () -> {
                text("+",
                        props(
                                UI.textSize(12, PIXELS),
                                UI.font(LEXEND_FONT),
                                UI.textColor(new Color(1f, 1f, 1f, 1f))
                        ));
            });
        });

    }
}
