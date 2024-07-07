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

package net.aoba.gui.screens;

import net.aoba.Aoba;
import net.aoba.altmanager.Alt;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class EditAltScreen extends Screen {

    private final AltScreen parent;
    private Alt alt;

    private ButtonWidget buttonSaveAlt;
    private CheckboxWidget toggleMicrosoft;
    private TextFieldWidget textFieldAltUsername;
    private TextFieldWidget textFieldAltPassword;

    public EditAltScreen(AltScreen parentScreen, Alt alt) {
        super(Text.of("Alt Manager"));
        this.parent = parentScreen;
        this.alt = alt;
    }

    public void init() {
        super.init();
        this.textFieldAltUsername = new TextFieldWidget(textRenderer, this.width / 2 - 100, height / 2 - 76, 200, 20,
                Text.of("Enter Name"));
        this.textFieldAltUsername.setText(this.alt == null ? "" : alt.getEmail());
        this.addDrawableChild(this.textFieldAltUsername);

        this.textFieldAltPassword = new TextFieldWidget(textRenderer, this.width / 2 - 100, height / 2 - 36, 200, 20,
                Text.of("Enter Password"));
        this.textFieldAltPassword.setText(this.alt == null ? "" : alt.getPassword());
        textFieldAltPassword.setRenderTextProvider((text, n) -> {
            String str = "";
            for (int i = 0; i < text.length(); i++)
                str += "*";
            return OrderedText.styledForwardsVisitedString(str, Style.EMPTY);
        });
        this.addDrawableChild(this.textFieldAltPassword);

        this.toggleMicrosoft = CheckboxWidget.builder(Text.of("Microsoft Account?"), textRenderer).pos(this.width / 2 - 100, height / 2 + -12).build();
        this.addDrawableChild(this.toggleMicrosoft);


        this.buttonSaveAlt = ButtonWidget.builder(Text.of("Save Alt"), b -> this.onButtonAltEditPressed())
                .dimensions(this.width / 2 - 100, this.height / 2 + 24, 200, 20).build();
        this.addDrawableChild(this.buttonSaveAlt);
        this.addDrawableChild(ButtonWidget.builder(Text.of("Cancel"), b -> this.onButtonCancelPressed())
                .dimensions(this.width / 2 - 100, this.height / 2 + 46, 200, 20).build());
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(drawContext, mouseX, mouseY, partialTicks);
        drawContext.drawCenteredTextWithShadow(textRenderer, "Edit Alternate Account", this.width / 2, 20, 16777215);
        drawContext.drawTextWithShadow(textRenderer, "Username:", this.width / 2 - 100, height / 2 - 90, 16777215);
        drawContext.drawTextWithShadow(textRenderer, "Password:", this.width / 2 - 100, height / 2 - 50, 16777215);
        super.render(drawContext, mouseX, mouseY, partialTicks);
    }

    private void onButtonAltEditPressed() {
        alt.setEmail(this.textFieldAltUsername.getText());
        alt.setPassword(this.textFieldAltPassword.getText());
        Aoba.getInstance().altManager.saveAlts();
        this.parent.refreshAltList();
    }

    public void onButtonCancelPressed() {
        client.setScreen(this.parent);
    }
}