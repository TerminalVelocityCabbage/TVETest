package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.input.control.*;
import com.terminalvelocitycabbage.engine.client.input.controller.ControlGroup;
import com.terminalvelocitycabbage.engine.client.input.types.GamepadInput;
import com.terminalvelocitycabbage.engine.client.input.types.KeyboardInput;
import com.terminalvelocitycabbage.engine.client.input.types.MouseInput;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.inputcontrollers.*;
import com.terminalvelocitycabbage.templates.events.InputHandlerRegistrationEvent;

public class GameInput {

    public static void init(InputHandlerRegistrationEvent event) {

        var inputHandler = event.getInputHandler();

        //Register Controls to listen to
        Control escapeControl = inputHandler.registerControlListener(new KeyboardKeyControl(KeyboardInput.Key.ESCAPE));
        Control rControl = inputHandler.registerControlListener(new KeyboardKeyControl(KeyboardInput.Key.R));
        Control pControl = inputHandler.registerControlListener(new KeyboardKeyControl(KeyboardInput.Key.P));
        Control leftJoystickForwardControl = inputHandler.registerControlListener(new GamepadAxisControl(GamepadInput.Axis.LEFT_JOYSTICK_UP, 1f));
        Control leftJoystickBackwardsControl = inputHandler.registerControlListener(new GamepadAxisControl(GamepadInput.Axis.LEFT_JOYSTICK_DOWN, 1f));
        Control leftJoystickLeftControl = inputHandler.registerControlListener(new GamepadAxisControl(GamepadInput.Axis.LEFT_JOYSTICK_LEFT, 1f));
        Control leftJoystickRightControl = inputHandler.registerControlListener(new GamepadAxisControl(GamepadInput.Axis.LEFT_JOYSTICK_RIGHT, 1f));
        Control rightJoystickForwardControl = inputHandler.registerControlListener(new GamepadAxisControl(GamepadInput.Axis.RIGHT_JOYSTICK_UP, 1f));
        Control rightJoystickBackwardsControl = inputHandler.registerControlListener(new GamepadAxisControl(GamepadInput.Axis.RIGHT_JOYSTICK_DOWN, 1f));
        Control rightJoystickLeftControl = inputHandler.registerControlListener(new GamepadAxisControl(GamepadInput.Axis.RIGHT_JOYSTICK_LEFT, 1f));
        Control rightJoystickRightControl = inputHandler.registerControlListener(new GamepadAxisControl(GamepadInput.Axis.RIGHT_JOYSTICK_RIGHT, 1f));
        Control gamepadAControl = inputHandler.registerControlListener(new GamepadButtonControl(GamepadInput.Button.A));
        Control leftTriggerControl = inputHandler.registerControlListener(new GamepadAxisControl(GamepadInput.Axis.LEFT_TRIGGER, 1f));
        Control wControl = inputHandler.registerControlListener(new KeyboardKeyControl(KeyboardInput.Key.W));
        Control sControl = inputHandler.registerControlListener(new KeyboardKeyControl(KeyboardInput.Key.S));
        Control aControl = inputHandler.registerControlListener(new KeyboardKeyControl(KeyboardInput.Key.A));
        Control dControl = inputHandler.registerControlListener(new KeyboardKeyControl(KeyboardInput.Key.D));
        Control spaceControl = inputHandler.registerControlListener(new KeyboardKeyControl(KeyboardInput.Key.SPACE));
        Control lShiftControl = inputHandler.registerControlListener(new KeyboardKeyControl(KeyboardInput.Key.LEFT_SHIFT));
        Control mouseUpControl = inputHandler.registerControlListener(new MouseMovementControl(MouseInput.MovementAxis.UP, 4f));
        Control mouseDownControl = inputHandler.registerControlListener(new MouseMovementControl(MouseInput.MovementAxis.DOWN, 4f));
        Control mouseLeftControl = inputHandler.registerControlListener(new MouseMovementControl(MouseInput.MovementAxis.LEFT, 4f));
        Control mouseRightControl = inputHandler.registerControlListener(new MouseMovementControl(MouseInput.MovementAxis.RIGHT, 4f));
        Control mouseScrollUpControl = inputHandler.registerControlListener(new MouseScrollControl(MouseInput.ScrollDirection.UP, 1f));
        Control mouseScrollDownControl = inputHandler.registerControlListener(new MouseScrollControl(MouseInput.ScrollDirection.DOWN, 1f));
        Control leftClickControl = inputHandler.registerControlListener(new MouseButtonControl(MouseInput.Button.LEFT_CLICK));

        //Register Controllers
        inputHandler.registerController(GameClient.ID, "ui_click", new UIClickController(MouseInput.Button.LEFT_CLICK, leftClickControl));
        inputHandler.registerController(GameClient.ID, "close_window_on_escape", new CloseWindowController(escapeControl));
        inputHandler.registerController(GameClient.ID, "move_around", new MoveController(
                new ControlGroup(wControl),
                new ControlGroup(sControl),
                new ControlGroup(aControl),
                new ControlGroup(dControl),
                new ControlGroup(spaceControl),
                new ControlGroup(lShiftControl)
        ));
        inputHandler.registerController(GameClient.ID, "look_around", new LookAroundController(
                new ControlGroup(mouseUpControl),
                new ControlGroup(mouseDownControl),
                new ControlGroup(mouseLeftControl),
                new ControlGroup(mouseRightControl)
        ));
        inputHandler.registerController(GameClient.ID, "scroll", new ScrollController(
                new ControlGroup(mouseScrollUpControl),
                new ControlGroup(mouseScrollDownControl)
        ));
        inputHandler.registerController(GameClient.ID, "reload_shaders", new RecompileShadersController(rControl));
        inputHandler.registerController(GameClient.ID, "pause_spinning", new PauseSpinningController(pControl));
    }
}
