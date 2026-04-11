package com.fabien_gigante.this_bed_is_mine;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThisBedIsMine implements ModInitializer {
	public static final String MOD_ID = "this-bed-is-mine";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static @Nullable Set<GlobalPos> playerRespawns = null;	

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
	}

	public static void invalidatePlayerRespawns() {
		playerRespawns = null;
	}

	private static Set<GlobalPos> refreshPlayerRespawns(ServerLevel level) {
		Set<GlobalPos> respawns = playerRespawns;
		if (respawns != null) return respawns;
		synchronized (ThisBedIsMine.class) {
			if (playerRespawns == null)
				playerRespawns = level.getServer().getPlayerList().getPlayers().stream()
					.map(ServerPlayer::getRespawnConfig).filter(Objects::nonNull)
					.map(cfg -> cfg.respawnData().globalPos())
					.collect(Collectors.toUnmodifiableSet());
			return playerRespawns;
		}
	}
	
	public static boolean isPlayerRespawn(ServerLevel level, GlobalPos pos) {
		Set<GlobalPos> respawns = refreshPlayerRespawns(level);
		return respawns.contains(pos);
	}
}