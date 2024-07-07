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

package net.aoba.cmd.commands;

import net.aoba.Aoba;
import net.aoba.cmd.Command;
import net.aoba.cmd.CommandManager;
import net.aoba.cmd.InvalidSyntaxException;
import net.aoba.module.modules.movement.NoSlowdown;

public class CmdNoSlowdown extends Command {

    public CmdNoSlowdown() {
        super("noslowdown", "Disables webs from slowing the player down", "[toggle] [value]");
    }

    @Override
    public void runCommand(String[] parameters) throws InvalidSyntaxException {
        if (parameters.length != 2)
            throw new InvalidSyntaxException(this);

        NoSlowdown module = (NoSlowdown) Aoba.getInstance().moduleManager.noslowdown;

        switch (parameters[0]) {
            case "toggle":
                String state = parameters[1].toLowerCase();
                if (state.equals("on")) {
                    module.setState(true);
                    CommandManager.sendChatMessage("NoSlowDown toggled ON");
                } else if (state.equals("off")) {
                    module.setState(false);
                    CommandManager.sendChatMessage("NoSlowDown toggled OFF");
                } else {
                    CommandManager.sendChatMessage("Invalid value. [ON/OFF]");
                }
                break;
            default:
                throw new InvalidSyntaxException(this);
        }
    }

    @Override
    public String[] getAutocorrect(String previousParameter) {
        switch (previousParameter) {
            case "toggle":
                return new String[]{"on", "off"};
            default:
                return new String[]{"toggle"};
        }
    }

}
