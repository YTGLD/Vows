package com.ytgld.vows.mixin.common;

import com.ytgld.vows.items.vows.hunger.Alms;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Shadow
    private int tickTimer;

    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void tick(ServerPlayer player, CallbackInfo ci) {
        tickTimer =Alms.healTime(player,tickTimer);
    }
}
