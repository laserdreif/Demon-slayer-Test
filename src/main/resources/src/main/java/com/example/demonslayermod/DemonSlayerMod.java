package com.example.demonslayermod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.example.demonslayermod.items.NichirinSwordItem;

public class DemonSlayerMod implements ModInitializer {

    public static final String MODID = "demonslayermod";

    // Das Nichirin Schwert wird hier registriert
    public static final Item NICHIRIN_SWORD = new NichirinSwordItem(new Item.Settings().group(ItemGroup.COMBAT));

    @Override
    public void onInitialize() {
        // Registriere das Schwert
        Registry.register(Registry.ITEM, new Identifier(MODID, "nichirin_sword"), NICHIRIN_SWORD);

        System.out.println("Demon Slayer Mod wurde geladen!");
    }
}
