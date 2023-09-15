package com.hbm.tileentity.machine;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidUser;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityChimneyBase extends TileEntityLoadedBase implements IFluidUser, INBTPacketReceiver {
	
	public int onTicks;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				FluidType[] types = new FluidType[] {Fluids.SMOKE, Fluids.SMOKE_LEADED, Fluids.SMOKE_POISON};
				
				for(FluidType type : types) {
					this.trySubscribe(type, worldObj, xCoord + 2, yCoord, zCoord, Library.POS_X);
					this.trySubscribe(type, worldObj, xCoord - 2, yCoord, zCoord, Library.NEG_X);
					this.trySubscribe(type, worldObj, xCoord, yCoord, zCoord + 2, Library.POS_Z);
					this.trySubscribe(type, worldObj, xCoord, yCoord, zCoord - 2, Library.NEG_Z);
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("onTicks", onTicks);
			INBTPacketReceiver.networkPack(this, data, 150);
			
			if(onTicks > 0) onTicks--;
			
		} else {
			
			if(onTicks > 0) {
				this.spawnParticles();
			}
		}
	}
	
	public void spawnParticles() { }
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.onTicks = nbt.getInteger("onTicks");
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH || dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) &&
				(type == Fluids.SMOKE || type == Fluids.SMOKE_LEADED || type == Fluids.SMOKE_POISON);
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {
		onTicks = 20;
		
		fluid *= getPollutionMod();

		if(type == Fluids.SMOKE) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, fluid / 100F);
		if(type == Fluids.SMOKE_LEADED) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.HEAVYMETAL, fluid / 100F);
		if(type == Fluids.SMOKE_POISON) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.POISON, fluid / 100F);
		
		return 0;
	}
	
	public abstract double getPollutionMod();

	@Override
	public long getDemand(FluidType type, int pressure) {
		return 1_000_000;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {};
	}
}
