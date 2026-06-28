package com.ytgld.vows.mixin.common;

import com.ytgld.vows.items.vows.hunger.Disaster;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "hasEnoughFoodToDoExhaustiveManoeuvres", at = @At(value = "RETURN"), cancellable = true)
    private void tick(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        Disaster.can(player,cir);
    }
}
