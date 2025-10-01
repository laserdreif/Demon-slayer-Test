package com.example.demonslayermod;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;

import java.util.*;

public class BreathingTechniques {

    public enum BreathingStyle {
        WATER, MIST, SUN
    }

    private static final Map<BreathingStyle, List<Technique>> STYLES = new HashMap<>();

    static {
        // Wasser
        STYLES.put(BreathingStyle.WATER, List.of(
            new Technique("First Form", "Water Surface Slash", (w, u) -> {
                Box area = new Box(u.getBlockPos()).expand(3);
                for (LivingEntity e : w.getEntitiesByClass(LivingEntity.class, area, e -> e != u)) {
                    e.damage(u.getDamageSources().playerAttack(u), 6.0f);
                }
                for (int i = 0; i < 20; i++) {
                    w.addParticle(ParticleTypes.SPLASH, u.getX(), u.getY() + 1, u.getZ(), 0, 0.1, 0);
                }
                u.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
            }),
            new Technique("Second Form", "Water Wheel", (w, u) -> {
                u.addVelocity(0, 1, 0);
                Box area = new Box(u.getBlockPos()).expand(2);
                for (LivingEntity e : w.getEntitiesByClass(LivingEntity.class, area, e -> e != u)) {
                    e.damage(u.getDamageSources().playerAttack(u), 8.0f);
                    e.takeKnockback(1.5, u.getX() - e.getX(), u.getZ() - e.getZ());
                }
                w.addParticle(ParticleTypes.WATER_SPLASH, u.getX(), u.getY(), u.getZ(), 0, 0.2, 0);
                u.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, 1f, 0.9f);
            })
        ));

        // Nebel
        STYLES.put(BreathingStyle.MIST, List.of(
            new Technique("First Form", "Obscuring Clouds", (w, u) -> {
                u.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(
                        net.minecraft.entity.effect.StatusEffects.INVISIBILITY, 100, 0));
                u.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(
                        net.minecraft.entity.effect.StatusEffects.SPEED, 100, 1));
                for (int i = 0; i < 30; i++) {
                    w.addParticle(ParticleTypes.CLOUD,
                            u.getX() + (w.random.nextDouble() - 0.5) * 3,
                            u.getY() + 1,
                            u.getZ() + (w.random.nextDouble() - 0.5) * 3,
                            0, 0.05, 0);
                }
                u.playSound(SoundEvents.ENTITY_PHANTOM_FLAP, 1f, 1.2f);
            })
        ));

        // Sonne
        STYLES.put(BreathingStyle.SUN, List.of(
            new Technique("First Form", "Dance", (w, u) -> {
                Box area = new Box(u.getBlockPos()).expand(3);
                for (LivingEntity e : w.getEntitiesByClass(LivingEntity.class, area, e -> e != u)) {
                    e.damage(u.getDamageSources().playerAttack(u), 10.0f);
                    e.setOnFireFor(4);
                }
                for (int i = 0; i < 40; i++) {
                    w.addParticle(ParticleTypes.FLAME,
                            u.getX() + (w.random.nextDouble() - 0.5) * 2,
                            u.getY() + 1,
                            u.getZ() + (w.random.nextDouble() - 0.5) * 2,
                            0, 0.1, 0);
                }
                u.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1f, 1f);
            })
        ));
    }

    public static List<Technique> getTechniques(BreathingStyle style) {
        return STYLES.getOrDefault(style, Collections.emptyList());
    }
}
