package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnaceLarge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineArcFurnaceLarge extends Container {
	
	private TileEntityMachineArcFurnaceLarge furnace;

	public ContainerMachineArcFurnaceLarge(InventoryPlayer playerInv, TileEntityMachineArcFurnaceLarge tile) {
		furnace = tile;

		//Electrodes
		for(int i = 0; i < 3; i++) this.addSlotToContainer(new SlotNonRetarded(tile, i, 62 + i * 18, 22));
		//Battery
		this.addSlotToContainer(new Slot(tile, 3, 8, 108));
		//Upgrade
		this.addSlotToContainer(new Slot(tile, 4, 152, 108));
		//Inputs
		for(int i = 0; i < 4; i++) for(int j = 0; j < 5; j++) this.addSlotToContainer(new SlotArcFurnace(tile, 5 + j + i * 5, 44 + j * 18, 54 + i * 18));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 158 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 216));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return furnace.isUseableByPlayer(player);
	}
	
	public static class SlotArcFurnace extends SlotNonRetarded {

		public SlotArcFurnace(IInventory inventory, int id, int x, int y) {
			super(inventory, id, x, y);
		}
		
		@Override
		public int getSlotStackLimit() {
			return this.getHasStack() ? this.getStack().stackSize : 1;
		}
	}
}
