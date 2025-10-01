package com.example.demonslayermod.items;

import com.example.demonslayermod.BreathingTechniques;
import com.example.demonslayermod.Technique;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class NichirinSwordItem extends SwordItem {

    public NichirinSwordItem(Settings settings) {
        super(ToolMaterials.IRON, 3, -2.4f, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            // Atemstil bestimmen
            if (!stack.hasNbt() || !stack.getNbt().contains("BreathingStyle")) {
                String[] styles = {"WATER", "MIST", "SUN"};
                String chosen = styles[new Random().nextInt(styles.length)];
                stack.getOrCreateNbt().putString("BreathingStyle", chosen);
                stack.getNbt().putInt("TechniqueIndex", 0);
                user.sendMessage(Text.literal("Du hast die " + chosen + "-Atmung erlangt!"), true);
            } else {
                // aktuelle Technik ausführen
                String styleName = stack.getNbt().getString("BreathingStyle");
                BreathingTechniques.BreathingStyle style = BreathingTechniques.BreathingStyle.valueOf(styleName);
                int index = stack.getNbt().getInt("TechniqueIndex");

                List<Technique> list = BreathingTechniques.getTechniques(style);
                if (!list.isEmpty()) {
                    Technique t = list.get(index % list.size());
                    t.use(world, user);
                    user.sendMessage(Text.literal("Du setzt ein: " + t.getFormName() + " - " + t.getDisplayName()), true);
                }
            }
        }

        return TypedActionResult.success(stack);
    }

    public void cycleTechnique(ItemStack stack, PlayerEntity user) {
        if (!stack.hasNbt() || !stack.getNbt().contains("BreathingStyle")) return;

        String styleName = stack.getNbt().getString("BreathingStyle");
        BreathingTechniques.BreathingStyle style = BreathingTechniques.BreathingStyle.valueOf(styleName);

        int current = stack.getNbt().getInt("TechniqueIndex");
        int next = (current + 1) % BreathingTechniques.getTechniques(style).size();
        stack.getNbt().putInt("TechniqueIndex", next);

        Technique t = BreathingTechniques.getTechniques(style).get(next);
        user.sendMessage(Text.literal("Technik gewechselt: " + t.getFormName() + " - " + t.getDisplayName()), true);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt() && stack.getNbt().contains("BreathingStyle")) {
            String style = stack.getNbt().getString("BreathingStyle");
            int index = stack.getNbt().getInt("TechniqueIndex");
            List<Technique> list = BreathingTechniques.getTechniques(BreathingTechniques.BreathingStyle.valueOf(style));
            String techName = list.isEmpty() ? "-" : list.get(index).getDisplayName();
            tooltip.add(Text.literal("Atemstil: " + style));
            tooltip.add(Text.literal("Technik: " + techName));
        } else {
            tooltip.add(Text.literal("Rechtsklick: Atemstil offenbaren"));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
