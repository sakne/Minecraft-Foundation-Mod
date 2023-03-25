package net.sakne.foundation;

import net.fabricmc.api.ModInitializer;
import net.sakne.foundation.block.ModBlocks;
import net.sakne.foundation.block.entity.ModBlockEntities;
import net.sakne.foundation.item.ModItems;
import net.sakne.foundation.screen.ModScreenHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Foundation implements ModInitializer {
	public static final String MOD_ID = "foundation";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// REGISTERING OWO THINGIES
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerAllScreenHandlers();
	}
}
