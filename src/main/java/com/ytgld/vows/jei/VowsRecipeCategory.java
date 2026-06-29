package com.ytgld.vows.jei;

import com.ytgld.vows.Vows;
import com.ytgld.vows.items.VowsItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.common.Internal;
import mezz.jei.common.gui.textures.Textures;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class VowsRecipeCategory  implements IRecipeCategory<VowsRecipeCategory.GuidePage> {

    public static final IRecipeType<GuidePage> TYPE =
            IRecipeType.create(
                    Vows.MODID,
                    "guide",
                    GuidePage.class
            );
    private final IDrawable theRecipeArrow;
    private final IDrawable theSlot;
    public VowsRecipeCategory(){
        Textures textures = Internal.getTextures();
        this.theRecipeArrow = textures.getRecipeArrow();
        this.theSlot = textures.getSlot();
    }
    @Override
    public IRecipeType<GuidePage> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("vows.vows");
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 100;
    }


    @Override
    public void draw(GuidePage recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        theRecipeArrow.draw(guiGraphics,30,45);
        int offset = 0;
        for (ItemStack itemStack : recipe.inputs()){
            offset += 16;
            theSlot.draw(guiGraphics,offset - 16,20);
        }
        theSlot.draw(guiGraphics,80, 40);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return new ItemIDrawable();
    }

    @Override
    public void setRecipe(
            IRecipeLayoutBuilder builder,
            GuidePage page,
            IFocusGroup focuses) {


        int offset = 0;
        for (ItemStack itemStack : page.inputs()){
            offset += 16;
            builder.addSlot(RecipeIngredientRole.INPUT, offset - 16, 20)
                    .add(itemStack);
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 40)
                .add(page.outputs());
    }

    public record GuidePage(
            Component title,
            List<ItemStack> inputs,
            ItemStack outputs,
            Component description
    ) {}
    public static class ItemIDrawable implements IDrawable {

        @Override
        public int getWidth() {
            return 16;
        }

        @Override
        public int getHeight() {
            return 16;
        }

        @Override
        public void draw(GuiGraphicsExtractor guiGraphicsExtractor, int i, int i1) {
            guiGraphicsExtractor.item(VowsItems.VowsBlockItem.get().getDefaultInstance(),i,i1);
        }
    }
}