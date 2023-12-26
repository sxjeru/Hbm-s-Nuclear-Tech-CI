package com.hbm.blocks.rail;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public interface IRenderRail {

	@SideOnly(Side.CLIENT) public void renderInventory(Tessellator tessellator, Block block, int metadata);
	@SideOnly(Side.CLIENT) public void renderWorld(Tessellator tessellator, Block block, int meta, IBlockAccess world, int x, int y, int z);
}
