package net.sakne.foundation.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.sakne.foundation.block.custom.PressurizedHeatChamberBlock;
import net.sakne.foundation.item.ModItems;
import net.sakne.foundation.screen.PressurizedHeatChamberScreenHandler;
import org.jetbrains.annotations.Nullable;

public class Pressurized_Heat_Chamber_Entity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);



    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public Pressurized_Heat_Chamber_Entity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PRESSURIZED_HEAT_CHAMBER, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return Pressurized_Heat_Chamber_Entity.this.progress;
                    case 1: return Pressurized_Heat_Chamber_Entity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: Pressurized_Heat_Chamber_Entity.this.progress = value; break;
                    case 1: Pressurized_Heat_Chamber_Entity.this.maxProgress = value; break;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("PHC");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new PressurizedHeatChamberScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("phc.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("phc.progress");
    }


    private void resetProgress(BlockPos blockPos, BlockState state) {
        this.progress = 0;
    }

    public static void tick(World world, BlockPos blockPos ,BlockState state, Pressurized_Heat_Chamber_Entity entity) {
        if(world.isClient()) {
            return;
        }

        if(hasRecipe(entity)) {
            entity.progress++;
            state = state.with(PressurizedHeatChamberBlock.LIT, true);
            world.setBlockState(blockPos, state);
            markDirty(world, blockPos, state);
            if(entity.progress >= entity.maxProgress) {
                craftItem(entity, blockPos, state);
            }
        } else {
            state = state.with(PressurizedHeatChamberBlock.LIT, false);
            world.setBlockState(blockPos, state);
            entity.resetProgress(blockPos, state);
            markDirty(world, blockPos, state);
        }
    }


    private static void craftItem(Pressurized_Heat_Chamber_Entity entity, BlockPos blockPos, BlockState state) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        if(hasRecipe(entity)) {
            entity.removeStack(0, 1);

            entity.setStack(1, new ItemStack(ModItems.SILICONE_BLEND,
                    entity.getStack(1).getCount() + 1));

            entity.resetProgress(blockPos, state);
        }
    }

    private static boolean hasRecipe(Pressurized_Heat_Chamber_Entity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        boolean hasRawGemInFirstSlot = entity.getStack(0).getItem() == Items.SAND;

        return hasRawGemInFirstSlot && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, ModItems.SILICONE_BLEND);
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(1).getItem() == output || inventory.getStack(1).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(1).getMaxCount() > inventory.getStack(1).getCount();
    }

    private boolean CanInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(PressurizedHeatChamberBlock.FACING);

        if(side == Direction.UP || side == Direction.DOWN) {
            return false;
        }

        return switch (localDir) {
            default ->
                side.getOpposite() == Direction.NORTH && slot == 0 ||
                        side.getOpposite() == Direction.EAST && slot == 0 ||
                        side.getOpposite() == Direction.WEST && slot == 0;
            case EAST ->
                side.rotateYClockwise() == Direction.NORTH && slot == 0 ||
                        side.rotateYClockwise() == Direction.EAST && slot == 0 ||
                        side.rotateYClockwise() == Direction.WEST && slot == 0;
            case SOUTH ->
                side == Direction.NORTH && slot == 0 ||
                        side == Direction.EAST && slot == 0 ||
                        side == Direction.WEST && slot == 0;
            case WEST ->
                side.rotateYCounterclockwise() == Direction.NORTH && slot == 0 ||
                        side.rotateYCounterclockwise() == Direction.EAST && slot == 0 ||
                        side.rotateYCounterclockwise() == Direction.WEST && slot == 0;
        };
    }

    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(PressurizedHeatChamberBlock.FACING);

        if(side == Direction.UP) {
            return false;
        }

        // Down extract 1
        if(side == Direction.DOWN) {
            return slot == 1;
        }

        // bottom extract 1
        // right extract 1
        return switch (localDir) {
            default -> side.getOpposite() == Direction.SOUTH && slot == 1 ||
                    side.getOpposite() == Direction.EAST && slot == 1;
            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == 1 ||
                    side.rotateYClockwise() == Direction.EAST && slot == 1;
            case SOUTH -> side == Direction.SOUTH && slot == 1 ||
                    side == Direction.EAST && slot == 1;
            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == 1 ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == 1;
        };
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {

    }
}
