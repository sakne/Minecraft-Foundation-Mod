package net.sakne.foundation.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.sakne.foundation.block.entity.ModBlockEntities;
import net.sakne.foundation.block.entity.Pressurized_Heat_Chamber_Entity;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class PressurizedHeatChamberBlock extends BlockWithEntity implements BlockEntityProvider {

    public static final DirectionProperty FACING = Properties.HOPPER_FACING;

    public static final BooleanProperty LIT = Properties.LIT;



    public PressurizedHeatChamberBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
    }

    private static VoxelShape SHAPE_N = Stream.of(
            Block.createCuboidShape(0, 3, 0, 16, 17, 5),
            Block.createCuboidShape(0, 0, 0, 16, 3, 16),
            Block.createCuboidShape(3, 3, 5, 13, 14, 16),
            Block.createCuboidShape(6, 14, 5, 10, 17, 9),
            Block.createCuboidShape(6, 14, 11, 10, 17, 15),
            Block.createCuboidShape(0, 3, 6, 3, 12, 14),
            Block.createCuboidShape(13, 3, 6, 16, 12, 14)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static VoxelShape SHAPE_S = Stream.of(
            Block.createCuboidShape(0, 3, 11, 16, 17, 16),
            Block.createCuboidShape(0, 0, 0, 16, 3, 16),
            Block.createCuboidShape(3, 3, 0, 13, 14, 11),
            Block.createCuboidShape(6, 14, 7, 10, 17, 11),
            Block.createCuboidShape(6, 14, 1, 10, 17, 5),
            Block.createCuboidShape(13, 3, 2, 16, 12, 10),
            Block.createCuboidShape(0, 3, 2, 3, 12, 10)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static VoxelShape SHAPE_E = Stream.of(
            Block.createCuboidShape(11, 3, 0, 16, 17, 16),
            Block.createCuboidShape(0, 0, 0, 16, 3, 16),
            Block.createCuboidShape(0, 3, 3, 11, 14, 13),
            Block.createCuboidShape(7, 14, 6, 11, 17, 10),
            Block.createCuboidShape(1, 14, 6, 5, 17, 10),
            Block.createCuboidShape(2, 3, 0, 10, 12, 3),
            Block.createCuboidShape(2, 3, 13, 10, 12, 16)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static VoxelShape SHAPE_W = Stream.of(
            Block.createCuboidShape(0, 3, 0, 5, 17, 16),
            Block.createCuboidShape(0, 0, 0, 16, 3, 16),
            Block.createCuboidShape(5, 3, 3, 16, 14, 13),
            Block.createCuboidShape(5, 14, 6, 9, 17, 10),
            Block.createCuboidShape(11, 14, 6, 15, 17, 10),
            Block.createCuboidShape(6, 3, 13, 14, 12, 16),
            Block.createCuboidShape(6, 3, 0, 14, 12, 3)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch(state.get(FACING)) {
            case NORTH:
                return SHAPE_N;
            case SOUTH:
                return SHAPE_S;
            case EAST:
                return SHAPE_E;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
        builder.add(FACING);
    }




    /* BLOCK ENTITY STUFF OWO :3*/

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Pressurized_Heat_Chamber_Entity) {
                ItemScatterer.spawn(world, pos, (Pressurized_Heat_Chamber_Entity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((Pressurized_Heat_Chamber_Entity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new Pressurized_Heat_Chamber_Entity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.PRESSURIZED_HEAT_CHAMBER, Pressurized_Heat_Chamber_Entity::tick);
    }




}
