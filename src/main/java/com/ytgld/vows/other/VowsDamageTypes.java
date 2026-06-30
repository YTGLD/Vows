package com.ytgld.vows.other;

import com.ytgld.vows.Vows;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;

public class VowsDamageTypes {
    public static final ResourceKey<DamageType> thePlayerMagic = ResourceKey.create(Registries.DAMAGE_TYPE,
            Vows.fromNamespaceAndPath( "player_magic"));

    public static DamageSource playerMagic(Player player){
        RegistryAccess registryAccess = player.level().registryAccess();
        Registry<DamageType> damageTypes= registryAccess.lookupOrThrow(Registries.DAMAGE_TYPE);
        return new DamageSource(damageTypes.getOrThrow(thePlayerMagic), player);
    }

}
