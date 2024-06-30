package com.terminalvelocitycabbage.game.client.registry;

import com.terminalvelocitycabbage.engine.client.input.InputHandler;
import com.terminalvelocitycabbage.engine.client.input.control.*;
import com.terminalvelocitycabbage.engine.client.input.controller.ControlGroup;
import com.terminalvelocitycabbage.engine.client.input.types.GamepadInput;
import com.terminalvelocitycabbage.engine.client.input.types.KeyboardInput;
import com.terminalvelocitycabbage.engine.client.input.types.MouseInput;
import com.terminalvelocitycabbage.game.client.GameClient;
import com.terminalvelocitycabbage.game.client.inputcontrollers.CloseWindowController;
import com.terminalvelocitycabbage.game.client.inputcontrollers.DirectionalController;
import com.terminalvelocitycabbage.game.client.inputcontrollers.ScrollController;

public class GameInput {

    public static void init(GameClient client) {

        var inputHandler = client.getInputHandler();

        //Register Controls to listen to
        Control escapeControl = inputHandler.registerControlListener(new KeyboardKeyControl(KeyboardInput.Key.ESCAPE));
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
        Control mouseUpControl = inputHandler.registerControlListener(new MouseMovementControl(MouseInput.MovementAxis.UP, 100f));
        Control mouseDownControl = inputHandler.registerControlListener(new MouseMovementControl(MouseInput.MovementAxis.DOWN, 100f));
        Control mouseLeftControl = inputHandler.registerControlListener(new MouseMovementControl(MouseInput.MovementAxis.LEFT, 100f));
        Control mouseRightControl = inputHandler.registerControlListener(new MouseMovementControl(MouseInput.MovementAxis.RIGHT, 100f));
        Control mouseScrollUpControl = inputHandler.registerControlListener(new MouseScrollControl(MouseInput.ScrollDirection.UP, 1f));
        Control mouseScrollDownControl = inputHandler.registerControlListener(new MouseScrollControl(MouseInput.ScrollDirection.DOWN, 1f));

        //Register Controllers
        inputHandler.registerController(client.identifierOf("closeWindowOnEscapeController"), new CloseWindowController(escapeControl));
        inputHandler.registerController(client.identifierOf("movementController"), new DirectionalController(
                new ControlGroup(leftJoystickForwardControl, wControl),
                new ControlGroup(leftJoystickBackwardsControl, sControl),
                new ControlGroup(leftJoystickLeftControl, aControl),
                new ControlGroup(leftJoystickRightControl, dControl),
                new ControlGroup(gamepadAControl, spaceControl),
                new ControlGroup(leftTriggerControl, lShiftControl)
        ));
//        inputHandler.registerController(client.identifierOf("lookAroundController"), new LookAroundController(
//                new ControlGroup(mouseUpControl, rightJoystickForwardControl),
//                new ControlGroup(mouseDownControl, rightJoystickBackwardsControl),
//                new ControlGroup(mouseLeftControl, rightJoystickLeftControl),
//                new ControlGroup(mouseRightControl, rightJoystickRightControl)
//        ));
        inputHandler.registerController(client.identifierOf("scrollController"), new ScrollController(
                new ControlGroup(mouseScrollUpControl),
                new ControlGroup(mouseScrollDownControl)
        ));
    }
}
