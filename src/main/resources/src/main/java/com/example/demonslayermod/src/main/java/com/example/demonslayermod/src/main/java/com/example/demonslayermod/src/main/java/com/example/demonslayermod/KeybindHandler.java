package com.example.demonslayermod;

import com.example.demonslayermod.items.NichirinSwordItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeybindHandler implements ClientModInitializer {

    private static KeyBinding switchTechniqueKey;

    @Override
    public void onInitializeClient() {
        switchTechniqueKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.demonslayermod.switch_technique",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.demonslayermod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (switchTechniqueKey.wasPressed()) {
                if (client.player != null) {
                    if (client.player.getMainHandStack().getItem() instanceof NichirinSwordItem sword) {
                        sword.cycleTechnique(client.player.getMainHandStack(), client.player);
                    }
                }
            }
        });
    }
}
