package net.sakne.foundation.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sakne.foundation.Foundation;

public class ModItemGroup {

    public static final ItemGroup THE_FOUNDATION = FabricItemGroup.builder(new Identifier(Foundation.MOD_ID))
            .displayName(Text.literal("The Foundation"))
            .icon(() -> new ItemStack(ModItems.RAW_SILICONE))
            .build();
}
