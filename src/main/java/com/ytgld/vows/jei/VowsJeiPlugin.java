package com.ytgld.vows.jei;

import com.ytgld.vows.Vows;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.RecipePluginFinder;
import com.ytgld.vows.tool.RegisterRecipeConfig;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.List;

@JeiPlugin
public class VowsJeiPlugin implements IModPlugin {
    @Override
    public @NonNull Identifier getPluginUid() {
        return Vows.fromNamespaceAndPath("vows");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
                new VowsRecipeCategory());
    }
    @Override
    public void registerRecipes(@NonNull IRecipeRegistration registration) {
        for (RegisterRecipeConfig config  : RecipePluginFinder.getModPlugins()){
            registration.addRecipes(
                    VowsRecipeCategory.TYPE,
                    List.of(
                            new VowsRecipeCategory.GuidePage(
                                    Component.translatable("item.vows." + config.output()),
                                    config.itemList(),
                                    new ItemStack(Handler.getVowsItemForName(config.output())),
                                    Component.translatable("jei.vows"),
                                    config.doOffItem()
                            )
                    )
            );
        }
    }
}
