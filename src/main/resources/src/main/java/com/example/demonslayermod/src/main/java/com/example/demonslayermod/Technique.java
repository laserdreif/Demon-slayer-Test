package com.example.demonslayermod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@FunctionalInterface
public interface TechniqueAction {
    void execute(World world, PlayerEntity user);
}

public class Technique {
    private final String formName;
    private final String displayName;
    private final TechniqueAction action;

    public Technique(String formName, String displayName, TechniqueAction action) {
        this.formName = formName;
        this.displayName = displayName;
        this.action = action;
    }

    public String getFormName() {
        return formName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void use(World world, PlayerEntity user) {
        action.execute(world, user);
    }
}
