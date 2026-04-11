package com.fabien_gigante.this_bed_is_mine.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.fabien_gigante.this_bed_is_mine.ThisBedIsMine;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayer.RespawnConfig;
import net.minecraft.world.level.storage.ValueInput;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
        ThisBedIsMine.invalidatePlayerRespawns();
    }
    @Inject(method = "setRespawnPosition", at = @At("TAIL"))
    private void onSetRespawnPosition(@Nullable RespawnConfig respawn, boolean showMessage, CallbackInfo ci) {
        ThisBedIsMine.invalidatePlayerRespawns();
    }
    @Inject(method = "disconnect", at = @At("TAIL"))
    private void onDisconnect(CallbackInfo ci) {
        ThisBedIsMine.invalidatePlayerRespawns();
    }
}
