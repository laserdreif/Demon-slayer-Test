package com.example.demonslayermod.items;

import com.example.demonslayermod.DemonSlayerMod;
import com.example.demonslayermod.entities.TrainerEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item TRAINER_SPAWN_EGG = new SpawnEggItem(
            TrainerEntity.TYPE,
            0xFFFFFF, // Ei-Farbe
            0x0000FF, // Fleckenfarbe
            new FabricItemSettings().group(ItemGroup.MISC));

    public static void register() {
        Registry.register(Registry.ITEM,
                new Identifier(DemonSlayerMod.MODID, "trainer_spawn_egg"),
                TRAINER_SPAWN_EGG);
    }
}
