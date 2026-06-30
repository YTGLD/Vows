package com.ytgld.vows.block.base;

import com.ytgld.vows.block.VowsBlockEntitys;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.client.partclie.ColorOption;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class VowsBlockEntity extends BlockEntity {

    public int tickTIme = 0;
    public VowsBlockEntity(BlockPos pos, BlockState state) {
        super(VowsBlockEntitys.VowsBlockEntity_.get(), pos, state);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, VowsBlockEntity blockEntity) {
        blockEntity.tickTIme ++;
        if (blockEntity.tickTIme % 4 == 1) {
            String name = blockEntity.getData(PlayerDataHandler.trueVowsBlock);
            Item item = BuiltInRegistries.ITEM.getValue(Identifier.parse(name));
            if (!state.getValue(VowsBlock.doOffVows)) {
                if (item instanceof BaseVows baseVows) {
                    for (RenderVowsItem.ColorAndImage colorAndImage : baseVows.colorAndImage()) {
                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ColorOption.createColorOption(Vec3.ZERO, true, colorAndImage.color(), 1), pos.getX() + 0.5f, pos.getY() + 0.8F, pos.getZ() + 0.5f, 1, 0.03F, 0.03F, 0.03F, 0);
                        }
                        break;
                    }
                }
            }else {
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ColorOption.createColorOption(Vec3.ZERO, true, 0xffffffff, 1), pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f, 1, 0.03F, 0.03F, 0.03F, 0);
                }
            }
        }
        if (blockEntity.tickTIme % 20 == 1) {
            if (blockEntity.getData(PlayerDataHandler.ineAlpha.get()) > 0) {
                if (blockEntity.getData(PlayerDataHandler.ineAlpha.get()) < 240) {
                    blockEntity.setData(PlayerDataHandler.ineAlpha.get(), blockEntity.getData(PlayerDataHandler.ineAlpha.get()) + 15);
                }
            }
        }
    }

    public void other(Level level, BlockPos pos, VowsBlockEntity blockEntity){
        blockEntity.setData(PlayerDataHandler.ineAlpha.get(),15);
        level.playSound(null,pos.getX() + 0.5f, pos.getY() + 0.8F, pos.getZ(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS,1,1);
        String name = blockEntity.getData(PlayerDataHandler.trueVowsBlock);
        Item item = BuiltInRegistries.ITEM.getValue(Identifier.parse(name));
        if (item instanceof BaseVows baseVows) {
            for (RenderVowsItem.ColorAndImage colorAndImage : baseVows.colorAndImage()) {
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ColorOption.createColorOption(Vec3.ZERO, true, colorAndImage.color(), 1), pos.getX() + 0.5f, pos.getY() + 0.8F, pos.getZ() + 0.5f, 25, 0.03F, 0.03F, 0.03F, 0.5f);
                    break;
                }
            }
        }
    }
    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);
        IntAndStringSyncHandler.ISClass strings = this .getData(PlayerDataHandler.theIntAndStringSyncHandler);
        for (String string : strings.map().keySet()) {
            Item item = BuiltInRegistries.ITEM.getValue(Identifier.parse(string));
            ItemStack stack = new ItemStack(item,strings.map().get(string));
            if (this.level != null) {
                this.level.addFreshEntity(new ItemEntity(this.level,pos.getX()  + 0.5f,pos.getY()  + 0.5f,pos.getZ()  + 0.5f,stack));
            }
        }
    }
}
