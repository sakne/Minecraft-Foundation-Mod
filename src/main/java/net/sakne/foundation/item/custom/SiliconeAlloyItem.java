package net.sakne.foundation.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sakne.foundation.block.ModBlocks;


public class SiliconeAlloyItem extends Item {
    public SiliconeAlloyItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        //server: giving/removing items, affects entities/blocks
        //client: rendering, particle, music/sound
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState state = ModBlocks.SILICONE_CASTING.getDefaultState();
        BlockState stonestate = Blocks.SMOOTH_STONE.getDefaultState();
        BlockState blockState = world.getBlockState(blockPos);

        if (blockState != stonestate) {
            return ActionResult.FAIL;
        }
        if(!world.isClient) {
            world.setBlockState(blockPos, state);
            int slot = player.getInventory().selectedSlot;
            player.getInventory().removeStack(slot, 1);
        }
        for(int i = 0; i < 360; i++) {
            if(i % 45 == 0) {
                world.addParticle(ParticleTypes.CLOUD,
                        blockPos.getX() + 0.5d,blockPos.getY(), blockPos.getZ() + 0.5d,
                        Math.cos(i) * 0.15d, 0.10d, Math.sin(i) * 0.15d);
            }
        }
        world.playSound(player, blockPos, SoundEvents.BLOCK_BASALT_PLACE, SoundCategory.BLOCKS, 1f, 1f);

        Hand hand = player.getActiveHand();
        player.swingHand(hand);







        return super.useOnBlock(context);
    }
}
