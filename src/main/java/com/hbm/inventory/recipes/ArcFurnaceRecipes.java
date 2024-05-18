package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;

import static com.hbm.inventory.OreDictManager.*;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class ArcFurnaceRecipes extends SerializableRecipe {
	
	public static HashMap<AStack, ArcFurnaceRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		recipes.put(new OreDictStack(KEY_SAND),				new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(1))));
		recipes.put(new OreDictStack(NETHERQUARTZ.gem()),	new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 3))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(3))));
		recipes.put(new OreDictStack(NETHERQUARTZ.dust()),	new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 3))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(3))));
		recipes.put(new OreDictStack(NETHERQUARTZ.block()),	new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 12))	.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.NUGGET.q(12))));
		recipes.put(new OreDictStack(FIBER.ingot()),		new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 4))		.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.INGOT.q(1, 2))));
		recipes.put(new OreDictStack(FIBER.block()),		new ArcFurnaceRecipe().solid(new ItemStack(ModItems.nugget_silicon, 40))	.fluid(new MaterialStack(Mats.MAT_SILICON, MaterialShapes.INGOT.q(9, 2))));
	}

	@Override
	public String getFileName() {
		return "hbmArcFurnace.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		
	}

	public static class ArcFurnaceRecipe {

		public MaterialStack[] fluidOutput;
		public ItemStack solidOutput;
		
		public ArcFurnaceRecipe fluid(MaterialStack... outputs) {
			this.fluidOutput = outputs;
			return this;
		}
		
		public ArcFurnaceRecipe solid(ItemStack output) {
			this.solidOutput = output;
			return this;
		}
	}
}
