package net.sakne.foundation.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sakne.foundation.Foundation;
import net.sakne.foundation.item.custom.*;

public class ModItems {

    public static final Item RAW_SILICONE = registerItem("raw_silicone",
            new Item(new Item.Settings()), ModItemGroup.THE_FOUNDATION);
    public static final Item SILICONE = registerItem("silicone",
            new Item(new Item.Settings()), ModItemGroup.THE_FOUNDATION);
    public static final Item SILICONE_BLEND = registerItem("silicone_blend",
            new Item(new Item.Settings()), ModItemGroup.THE_FOUNDATION);
    public static final Item SILICONE_ALLOY = registerItem("silicone_alloy",
            new SiliconeAlloyItem(new Item.Settings()), ModItemGroup.THE_FOUNDATION);
    public static final Item COPPER_GEAR = registerItem("copper_gear",
            new Item(new Item.Settings()), ModItemGroup.THE_FOUNDATION);
    public static final Item PRESSURE_CHAMBER = registerItem("pressure_chamber",
            new Item(new Item.Settings()), ModItemGroup.THE_FOUNDATION);
    public static final Item REDSTONE_COIL = registerItem("redstone_coil",
            new Item(new Item.Settings()), ModItemGroup.THE_FOUNDATION);

    // ITEMS FOR OWO WEAPONS AND UWU THINGIES HIHI

    public static final Item COPPER_SWORD = registerItem("copper_sword",
            new ModSwordItem(ModToolMaterials.COPPER, 6, -1.4f, new Item.Settings()),
            ModItemGroup.THE_FOUNDATION);
    public static final Item COPPER_AXE = registerItem("copper_axe",
            new ModAxeItem(ModToolMaterials.COPPER, 9, -1.6f, new Item.Settings()),
            ModItemGroup.THE_FOUNDATION);
    public static final Item COPPER_PICKAXE = registerItem("copper_pickaxe",
            new ModPickaxeItem(ModToolMaterials.COPPER, 1, -2.8f, new Item.Settings()),
            ModItemGroup.THE_FOUNDATION);
    public static final Item COPPER_SHOVEL = registerItem("copper_shovel",
            new ModShovelItem(ModToolMaterials.COPPER, 2, -3f, new Item.Settings()),
            ModItemGroup.THE_FOUNDATION);
    public static final Item COPPER_HOE = registerItem("copper_hoe",
            new ModHoeItem(ModToolMaterials.COPPER, 0, -3f, new Item.Settings()),
            ModItemGroup.THE_FOUNDATION);


    // OWO THINGIES IS DONE


    private static Item registerItem(String name, Item item, ItemGroup group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, new Identifier(Foundation.MOD_ID, name), item);
    }

    public static void registerModItems(){
        Foundation.LOGGER.debug("Registering Mod Items for" + Foundation.MOD_ID);
    }
}
