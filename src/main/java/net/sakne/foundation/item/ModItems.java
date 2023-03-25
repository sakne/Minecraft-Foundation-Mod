package net.sakne.foundation.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sakne.foundation.Foundation;
import net.sakne.foundation.item.custom.SiliconeAlloyItem;

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

    private static Item registerItem(String name, Item item, ItemGroup group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, new Identifier(Foundation.MOD_ID, name), item);
    }

    public static void registerModItems(){
        Foundation.LOGGER.debug("Registering Mod Items for" + Foundation.MOD_ID);
    }
}
