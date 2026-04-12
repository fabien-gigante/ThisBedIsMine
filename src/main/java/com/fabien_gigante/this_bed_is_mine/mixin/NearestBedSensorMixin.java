package com.fabien_gigante.this_bed_is_mine.mixin;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import net.minecraft.world.entity.ai.sensing.NearestBedSensor;

import com.fabien_gigante.this_bed_is_mine.ThisBedIsMine;

@Mixin(NearestBedSensor.class)
public abstract class NearestBedSensorMixin {

    @Redirect(
        method = "doTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/ai/village/poi/PoiManager;findAllWithType(Ljava/util/function/Predicate;Ljava/util/function/Predicate;Lnet/minecraft/core/BlockPos;ILnet/minecraft/world/entity/ai/village/poi/PoiManager$Occupancy;)Ljava/util/stream/Stream;"
        )
    )
    private Stream<Pair<Holder<PoiType>, BlockPos>> redirectFindAllWithType(
        PoiManager instance, Predicate<Holder<PoiType>> typePredicate, Predicate<BlockPos> posPredicate, BlockPos origin, int radius, PoiManager.Occupancy occupancy,
        ServerLevel level, Mob body
    ) {
        return instance.findAllWithType(typePredicate,
            pos -> posPredicate.test(pos) && !ThisBedIsMine.isPlayerRespawn(level,GlobalPos.of(level.dimension(), pos)),
            origin, radius, occupancy);
    }
}