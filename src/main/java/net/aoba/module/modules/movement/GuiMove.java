package net.aoba.module.modules.movement;

import net.aoba.Aoba;
import net.aoba.event.events.TickEvent;
import net.aoba.event.listeners.TickListener;
import net.aoba.module.Category;
import net.aoba.module.Module;
import net.aoba.settings.types.KeybindSetting;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class GuiMove extends Module implements TickListener {
    public GuiMove() {
        super(new KeybindSetting("key.guimove", "GuiMove Key", InputUtil.fromKeyCode(GLFW.GLFW_KEY_UNKNOWN, 0)));

        this.setName("GuiMove");
        this.setCategory(Category.of("Movement"));
        this.setDescription("Lets the player move while inside of menus using arrow keys..");
    }

    @Override
    public void onDisable() {
        Aoba.getInstance().eventManager.RemoveListener(TickListener.class, this);
    }

    @Override
    public void onEnable() {
        Aoba.getInstance().eventManager.AddListener(TickListener.class, this);
    }

    @Override
    public void onToggle() {

    }

    @Override
    public void OnUpdate(TickEvent tickEvent) {
        if (MC.currentScreen != null && !(MC.currentScreen instanceof ChatScreen)) {
            for (KeyBinding k : new KeyBinding[]{MC.options.forwardKey, MC.options.backKey, MC.options.leftKey, MC.options.rightKey, MC.options.jumpKey, MC.options.sprintKey})
                k.setPressed(isKeyPressed(InputUtil.fromTranslationKey(k.getBoundKeyTranslationKey()).getCode()));

            float deltaX = 0;
            float deltaY = 0;

            if (isKeyPressed(264))
                deltaY += 10f;

            if (isKeyPressed(265))
                deltaY -= 10f;

            if (isKeyPressed(262))
                deltaX += 10f;

            if (isKeyPressed(263))
                deltaX -= 10f;

            if (deltaX != 0 || deltaY != 0)
                MC.player.changeLookDirection(deltaX, deltaY);
        }
    }

}
