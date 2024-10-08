/*
 * Aoba Hacked Client
 * Copyright (C) 2019-2024 coltonk9043
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Glide Module
 */
package net.aoba.module.modules.movement;

import net.aoba.Aoba;
import net.aoba.event.events.TickEvent;
import net.aoba.event.listeners.TickListener;
import net.aoba.module.Category;
import net.aoba.module.Module;
import net.aoba.settings.types.FloatSetting;
import net.aoba.settings.types.KeybindSetting;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Glide extends Module implements TickListener {

    private FloatSetting fallSpeed;

    public Glide() {
        super(new KeybindSetting("key.glide", "Glide Key", InputUtil.fromKeyCode(GLFW.GLFW_KEY_UNKNOWN, 0)));

        this.setName("Glide");
        this.setCategory(Category.of("Movement"));
        this.setDescription("Allows the player to glide down when in the air. Does not prevent fall damage.");

        fallSpeed = new FloatSetting("glide_fallspeed", "Fall Speed", "The speed at which the player will fall.", 0.25f, 0.1f, 2.0f, 0.1f);

        this.addSetting(fallSpeed);
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
    public void OnUpdate(TickEvent event) {
        ClientPlayerEntity player = MC.player;
        if (player.getVelocity().y < 0 && (!player.isOnGround() || !player.isInLava() || !player.isSubmergedInWater() || !player.isHoldingOntoLadder())) {
            player.setVelocity(player.getVelocity().x, Math.max(player.getVelocity().y, -fallSpeed.getValue()), player.getVelocity().z);
        }
    }
}
