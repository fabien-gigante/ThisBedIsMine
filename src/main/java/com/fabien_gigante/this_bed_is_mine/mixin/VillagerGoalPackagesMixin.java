package com.fabien_gigante.this_bed_is_mine.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import com.fabien_gigante.this_bed_is_mine.ThisBedIsMine;

@Mixin(VillagerGoalPackages.class)
public class VillagerGoalPackagesMixin {
	@ModifyReturnValue(method = "validateBedPoi", at = @At("RETURN"))
    private static boolean validateBedPoi(boolean original, ServerLevel level, BlockPos blockPos) {
		return original && !ThisBedIsMine.isPlayerRespawn(level, GlobalPos.of(level.dimension(), blockPos));
	}
}