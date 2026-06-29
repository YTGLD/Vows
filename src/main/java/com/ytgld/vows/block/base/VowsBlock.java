package com.ytgld.vows.block.base;

import com.ytgld.vows.block.VowsBlockEntitys;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.PlayerDataHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class VowsBlock extends Block implements EntityBlock {

    public static final BooleanProperty ISHasVowsItem = BooleanProperty.create("has");


    public VowsBlock(Properties properties) {
        super(properties
                .strength(4)
                .lightLevel((state)->12)
                .sound(SoundType.NETHER_BRICKS)
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(ISHasVowsItem, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ISHasVowsItem);
    }
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(
                Block.column(8,0,12),
                Block.column(10,0,4)
        );
    }

    @Override
    protected @NonNull InteractionResult useItemOn(ItemStack stack, BlockState state,
                                                   Level level, BlockPos pos, Player player,
                                                   InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof VowsBlockEntity vowsBlockEntity) {
            if (stack.isEmpty()) {
                if (state.getValue(ISHasVowsItem)) {
                    if (player.isShiftKeyDown()) {
                        if (Handler.addVows(player, vowsBlockEntity.getData(PlayerDataHandler.trueVowsBlock.get()))) {
                            level.playSound(null, pos.getX() + 0.5f, pos.getY() + 0.8F, pos.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.BLOCKS, 1, 1);
                            vowsBlockEntity.setData(PlayerDataHandler.trueVowsBlock.get(), "");
                            return InteractionResult.PASS;
                        }else {
                            if (player.getData(PlayerDataHandler.vVowsSet).contains(vowsBlockEntity.getData(PlayerDataHandler.trueVowsBlock.get()))) {
                                player.sendOverlayMessage(Component.translatable("vows.vows.has").withStyle(Style.EMPTY.withColor(0xffff0000)));
                            }else {
                                player.sendOverlayMessage(Component.translatable("vows.vows.max").withStyle(Style.EMPTY.withColor(0xffff0000)));
                            }
                            return InteractionResult.FAIL;
                        }
                    }
                }
                return InteractionResult.FAIL;
            }else {
                Item item = stack.getItem();
                Identifier identifier = BuiltInRegistries.ITEM.getKey(item);
                Set<String> strings = vowsBlockEntity.getData(PlayerDataHandler.vVowsSet);
                if (strings.size() < 6) {
                    strings.add(identifier.toString());
                    stack.shrink(1);
                }
                return InteractionResult.PASS;
            }
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new VowsBlockEntity(blockPos,blockState);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return List.of(this.asItem().getDefaultInstance());
    }

    private static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker
    ) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, VowsBlockEntitys.VowsBlockEntity_.get(), VowsBlockEntity::tick);
    }
}
