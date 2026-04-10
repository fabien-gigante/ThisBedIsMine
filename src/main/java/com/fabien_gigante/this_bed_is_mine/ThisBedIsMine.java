package com.fabien_gigante.this_bed_is_mine;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThisBedIsMine implements ModInitializer {
	public static final String MOD_ID = "this-bed-is-mine";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
	}

	public static boolean isPlayerRespawnPos(ServerLevel level, GlobalPos pos) {
		for (var player : level.players()) {
			var respawn = player.getRespawnConfig();
			if (respawn != null && respawn.respawnData().globalPos().equals(pos)) return true;
		}
		return false;
	}
}