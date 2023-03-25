package net.sakne.foundation.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sakne.foundation.Foundation;
import net.sakne.foundation.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<Pressurized_Heat_Chamber_Entity> PRESSURIZED_HEAT_CHAMBER;

    public static void registerBlockEntities() {
        PRESSURIZED_HEAT_CHAMBER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Foundation.MOD_ID, "pressurized_heat_chamber"),
                FabricBlockEntityTypeBuilder.create(Pressurized_Heat_Chamber_Entity::new,
                        ModBlocks.PRESSURIZED_HEAT_CHAMBER).build(null));
    }
}
