package com.fabien_gigante.this_bed_is_mine.mixin;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.VillagerMakeLove;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.Villager;

import com.fabien_gigante.this_bed_is_mine.ThisBedIsMine;

@Mixin(VillagerMakeLove.class)
public abstract class VillagerMakeLoveMixin {
    @Redirect(
        method = "takeVacantBed",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/ai/village/poi/PoiManager;take(Ljava/util/function/Predicate;Ljava/util/function/BiPredicate;Lnet/minecraft/core/BlockPos;I)Ljava/util/Optional;"
        )
    )
    private Optional<BlockPos> redirectTake(
        PoiManager instance, Predicate<Holder<PoiType>> typePredicate, BiPredicate<Holder<PoiType>, BlockPos> posPredicate, BlockPos origin, int radius,
        ServerLevel level, Villager body
    ) {
        return instance.take(typePredicate,
            (poiType, pos) -> posPredicate.test(poiType, pos) && !ThisBedIsMine.isPlayerRespawn(level, GlobalPos.of(level.dimension(), pos)),
            origin, radius);
    }
}