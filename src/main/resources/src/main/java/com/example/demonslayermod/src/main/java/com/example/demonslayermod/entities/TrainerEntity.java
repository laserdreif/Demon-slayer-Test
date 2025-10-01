package com.example.demonslayermod.entities;

import com.example.demonslayermod.DemonSlayerMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;

public class TrainerEntity extends VillagerEntity {

    public static final EntityType<TrainerEntity> TYPE = EntityType.Builder
            .create(TrainerEntity::new, SpawnGroup.CREATURE)
            .setDimensions(0.6f, 1.95f)
            .build("trainer");

    public TrainerEntity(EntityType<? extends VillagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void fillRecipes() {
        TradeOfferList offers = new TradeOfferList();

        // 20 Smaragde -> 1 Nichirin-Schwert
        offers.add(new TradeOffer(
                new ItemStack(Items.EMERALD, 20),
                new ItemStack(DemonSlayerMod.NICHIRIN_SWORD),
                1, 5, 0.05f
        ));

        this.getOffers().addAll(offers);
    }
}
