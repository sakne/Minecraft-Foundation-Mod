package net.sakne.foundation.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.sakne.foundation.Foundation;

public class ModScreenHandlers {
    public static ScreenHandlerType<PressurizedHeatChamberScreenHandler> PHC_HANDLER =
            new ExtendedScreenHandlerType<>(PressurizedHeatChamberScreenHandler::new);

    public static void registerAllScreenHandlers() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(Foundation.MOD_ID, "pressurized_heat_chamber"),
                PHC_HANDLER);
    }
}
