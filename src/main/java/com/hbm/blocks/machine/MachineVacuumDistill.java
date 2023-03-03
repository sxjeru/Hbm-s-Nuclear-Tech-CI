package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.machine.oil.TileEntityMachineVacuumDistill;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineVacuumDistill extends BlockDummyable {

	public MachineVacuumDistill(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12) return new TileEntityMachineVacuumDistill();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {8, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
}
