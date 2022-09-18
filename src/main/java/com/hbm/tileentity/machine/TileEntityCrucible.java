package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.container.ContainerCrucible;
import com.hbm.inventory.gui.GUICrucible;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.inventory.recipes.CrucibleRecipes.CrucibleRecipe;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityCrucible extends TileEntityMachineBase implements IGUIProvider {

	public int heat;
	public static final int maxHeat = 100_000;
	public int progress;
	public static final int processTime = 20_000;
	public static final double diffusion = 0.25D;

	public final int recipeCapacity = MaterialShapes.BLOCK.q(16);
	public final int wasteCapacity = MaterialShapes.BLOCK.q(16);
	public List<MaterialStack> recipeStack = new ArrayList();
	public List<MaterialStack> wasteStack = new ArrayList();

	public TileEntityCrucible() {
		super(10);
	}

	@Override
	public String getName() {
		return "container.machineCrucible";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1; //prevents clogging
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			tryPullHeat();
			
			if(!trySmelt()) {
				this.progress = 0;
			}
		}
	}
	
	protected void tryPullHeat() {
		TileEntity con = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int diff = source.getHeatStored() - this.heat;
			
			if(diff == 0) {
				return;
			}
			
			if(diff > 0) {
				diff = (int) Math.ceil(diff * diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > this.maxHeat)
					this.heat = this.maxHeat;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}
	
	protected boolean trySmelt() {
		
		if(this.heat < maxHeat / 2) return false;
		
		int slot = this.getFirstSmeltableSlot();
		if(slot == -1) return false;
		
		int delta = this.heat - (maxHeat / 2);
		delta *= 0.05;
		
		this.progress += delta;
		this.heat -= delta;
		
		if(this.progress >= processTime) {
			this.progress = 0;
			
			List<MaterialStack> materials = Mats.getMaterialsFromItem(slots[slot]);
			CrucibleRecipe recipe = getLoadedRecipe();
			
			for(MaterialStack material : materials) {
				boolean mainStack = recipe != null && getQuantaFromType(recipe.input, material.material) > 0;
				
				if(mainStack) {
					this.addToStack(this.recipeStack, material);
				} else {
					this.addToStack(this.wasteStack, material);
				}
			}
			
			this.decrStackSize(slot, 1);
		}
		
		return true;
	}
	
	protected int getFirstSmeltableSlot() {
		
		for(int i = 1; i < 10; i++) {
			
			ItemStack stack = slots[i];
			
			if(stack != null && isItemSmeltable(stack)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public boolean isItemSmeltable(ItemStack stack) {
		
		List<MaterialStack> materials = Mats.getMaterialsFromItem(stack);
		
		//if there's no materials in there at all, don't smelt
		if(materials.isEmpty())
			return false;
		
		CrucibleRecipe recipe = getLoadedRecipe();
		
		//needs to be true, will always be true if there's no recipe loaded
		boolean matchesRecipe = recipe == null;
		
		//the amount of material in the entire recipe input
		int recipeContent = recipe != null ? recipe.getInputAmount() : 0;
		//the total amount of the current waste stack, used for simulation
		int wasteAmount = getQuantaFromType(this.wasteStack, null);
		
		for(MaterialStack mat : materials) {
			//if no recipe is loaded, everything will land in the waste stack
			int recipeInputRequired = recipe != null ? getQuantaFromType(recipe.input, mat.material) : 0;
			
			if(recipeInputRequired == 0) {
				//if this type isn't required by the recipe, add it to the waste stack
				wasteAmount += mat.amount;
			} else {
				
				//the maximum is the recipe's ratio scaled up to the recipe stack's capacity
				int matMaximum = recipeInputRequired * this.recipeCapacity / recipeContent;
				int amountStored = getQuantaFromType(recipeStack, mat.material);
				
				matchesRecipe = true;
				
				//if the amount of that input would exceed the amount dictated by the recipe, return false
				if(recipe != null && amountStored + mat.amount > matMaximum)
					return false;
			}
		}
		
		//if the waste amount doesn't exceed the capacity and the recipe matches (or isn't null), return true
		return wasteAmount <= this.wasteCapacity && matchesRecipe;
	}
	
	public void addToStack(List<MaterialStack> stack, MaterialStack matStack) {
		
		for(MaterialStack mat : stack) {
			if(mat.material == matStack.material) {
				mat.amount += matStack.amount;
				return;
			}
		}
		
		stack.add(matStack.copy());
	}
	
	public CrucibleRecipe getLoadedRecipe() {
		
		if(slots[0] != null && slots[0].getItem() == ModItems.crucible_template) {
			return CrucibleRecipes.indexMapping.get(slots[0].getItemDamage());
		}
		
		return null;
	}
	
	/* "Arrays and Lists don't have a common ancestor" my fucking ass */
	public int getQuantaFromType(MaterialStack[] stacks, NTMMaterial mat) {
		for(MaterialStack stack : stacks) {
			if(mat == null || stack.material == mat) {
				return stack.amount;
			}
		}
		return 0;
	}
	
	public int getQuantaFromType(List<MaterialStack> stacks, NTMMaterial mat) {
		for(MaterialStack stack : stacks) {
			if(mat == null || stack.material == mat) {
				return stack.amount;
			}
		}
		return 0;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCrucible(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICrucible(player.inventory, this);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2,
					zCoord + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
