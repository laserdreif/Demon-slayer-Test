package com.example.demonslayermod.items;

import com.example.demonslayermod.DemonSlayerMod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class NichirinSwordItem extends SwordItem {

    public NichirinSwordItem(Settings settings) {
        super(ToolMaterials.IRON, 3, -2.4f, settings); // Werte wie ein Eisenschwert
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            // Wenn kein Atemstil gewählt -> zufällig zuweisen
            if (!stack.hasNbt() || !stack.getNbt().contains("BreathingStyle")) {
                String[] styles = {"WATER", "MIST", "SUN"};
                String chosen = styles[new Random().nextInt(styles.length)];
                stack.getOrCreateNbt().putString("BreathingStyle", chosen);

                user.sendMessage(Text.literal("Du hast die " + chosen + "-Atmung erlangt!"), true);
            }
        }

        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt() && stack.getNbt().contains("BreathingStyle")) {
            tooltip.add(Text.literal("Atemstil: " + stack.getNbt().getString("BreathingStyle")));
        } else {
            tooltip.add(Text.literal("Rechtsklick um deinen Atemstil zu offenbaren"));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
