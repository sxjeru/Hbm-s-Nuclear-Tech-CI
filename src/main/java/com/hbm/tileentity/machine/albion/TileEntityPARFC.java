package com.hbm.tileentity.machine.albion;

import com.hbm.inventory.container.ContainerPARFC;
import com.hbm.inventory.gui.GUIPARFC;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPARFC extends TileEntityCooledBase implements IGUIProvider {
	
	public TileEntityPARFC() {
		super(1);
	}

	@Override
	public long getMaxPower() {
		return 10_000_000;
	}

	@Override
	public String getName() {
		return "container.paRFC";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
		}
		
		super.updateEntity();
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord - 1,
					zCoord - 4,
					xCoord + 5,
					yCoord + 2,
					zCoord + 5
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 3, yCoord + 2, zCoord + dir.offsetZ * 3, Library.POS_Y),
				new DirPos(xCoord - dir.offsetX * 3, yCoord + 2, zCoord - dir.offsetZ * 3, Library.POS_Y),
				new DirPos(xCoord, yCoord + 2, zCoord, Library.POS_Y),
				new DirPos(xCoord + dir.offsetX * 3, yCoord - 2, zCoord + dir.offsetZ * 3, Library.NEG_Y),
				new DirPos(xCoord - dir.offsetX * 3, yCoord - 2, zCoord - dir.offsetZ * 3, Library.NEG_Y),
				new DirPos(xCoord, yCoord - 2, zCoord, Library.NEG_Y)
		};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPARFC(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPARFC(player.inventory, this);
	}
}
