package net.sakne.foundation.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.sakne.foundation.Foundation;
import net.sakne.foundation.item.ModItemGroup;
import net.sakne.foundation.block.custom.PressurizedHeatChamberBlock;

import java.util.function.ToIntFunction;

public class ModBlocks {

    public static final Block SILICONE_BLOCK = registerBlock("silicone_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(4f).requiresTool()), ModItemGroup.THE_FOUNDATION);
    public static final Block SILICONE_CASTING = registerBlock("silicone_casting",
            new Block(FabricBlockSettings.of(Material.STONE).strength(2f).requiresTool()), ModItemGroup.THE_FOUNDATION);
    public static final Block PRESSURIZED_HEAT_CHAMBER = registerBlock("pressurized_heat_chamber",
            new PressurizedHeatChamberBlock(FabricBlockSettings.of(Material.METAL)
                    .strength(3.5f).requiresTool()
                    .luminance(ModBlocks.createLightLevelFromLitBlockState(13))), ModItemGroup.THE_FOUNDATION);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(Foundation.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(block));
        return Registry.register(Registries.ITEM, new Identifier(Foundation.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    public static void registerModBlocks() {
        Foundation.LOGGER.debug("Registering ModBlocks for " + Foundation.MOD_ID);
    }
}
