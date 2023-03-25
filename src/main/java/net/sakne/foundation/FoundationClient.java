package net.sakne.foundation;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.sakne.foundation.screen.ModScreenHandlers;
import net.sakne.foundation.screen.PressurizedHeatChamberScreen;
import net.sakne.foundation.screen.PressurizedHeatChamberScreenHandler;

public class FoundationClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {



    HandledScreens.register(ModScreenHandlers.PHC_HANDLER, PressurizedHeatChamberScreen::new);
    }
}
