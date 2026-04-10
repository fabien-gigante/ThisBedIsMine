package com.fabien_gigante.this_bed_is_mine.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.SleepInBed;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import com.fabien_gigante.this_bed_is_mine.ThisBedIsMine;

@Mixin(SleepInBed.class)
public class SleepInBedMixin {
    @ModifyReturnValue(method = "checkExtraStartConditions", at = @At("RETURN"))
    private boolean checkExtraStartConditions(boolean original, ServerLevel level, LivingEntity body) {
        return original && !ThisBedIsMine.isPlayerRespawnPos(level, body.getBrain().getMemory(MemoryModuleType.HOME).get());
    }
}