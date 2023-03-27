package net.sakne.foundation.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class CopperKilnBlock extends Block {
    public static final DirectionProperty FACING = Properties.HOPPER_FACING;

    public static final BooleanProperty LIT = Properties.LIT;


    public CopperKilnBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
    }

    private static VoxelShape SHAPE_N = Stream.of(
            Block.createCuboidShape(3, 13, 3, 13, 18, 5),
            Block.createCuboidShape(0, 0, 0, 16, 4, 16),
            Block.createCuboidShape(1, 4, 1, 15, 13, 15),
            Block.createCuboidShape(3, 13, 11, 13, 18, 13),
            Block.createCuboidShape(3, 13, 5, 5, 18, 11),
            Block.createCuboidShape(11, 13, 5, 13, 18, 11)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static VoxelShape SHAPE_S = Stream.of(
            Block.createCuboidShape(3, 13, 11, 13, 18, 13),
            Block.createCuboidShape(0, 0, 0, 16, 4, 16),
            Block.createCuboidShape(1, 4, 1, 15, 13, 15),
            Block.createCuboidShape(3, 13, 3, 13, 18, 5),
            Block.createCuboidShape(11, 13, 5, 13, 18, 11),
            Block.createCuboidShape(3, 13, 5, 5, 18, 11)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static VoxelShape SHAPE_E = Stream.of(
            Block.createCuboidShape(11, 13, 3, 13, 18, 13),
            Block.createCuboidShape(0, 0, 0, 16, 4, 16),
            Block.createCuboidShape(1, 4, 1, 15, 13, 15),
            Block.createCuboidShape(3, 13, 3, 5, 18, 13),
            Block.createCuboidShape(5, 13, 3, 11, 18, 5),
            Block.createCuboidShape(5, 13, 11, 11, 18, 13)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static VoxelShape SHAPE_W = Stream.of(
            Block.createCuboidShape(3, 13, 3, 5, 18, 13),
            Block.createCuboidShape(0, 0, 0, 16, 4, 16),
            Block.createCuboidShape(1, 4, 1, 15, 13, 15),
            Block.createCuboidShape(11, 13, 3, 13, 18, 13),
            Block.createCuboidShape(5, 13, 11, 11, 18, 13),
            Block.createCuboidShape(5, 13, 3, 11, 18, 5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
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
}
