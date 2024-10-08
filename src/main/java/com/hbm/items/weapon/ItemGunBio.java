package com.hbm.items.weapon;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.GunConfiguration;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemGunBio extends ItemGunBase {

	public ItemGunBio(GunConfiguration config) {
		super(config);
	}
	
	public static long lastShot;
	public static List<double[]> smokeNodes = new ArrayList();
	
	@Override
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) { }

	@Override
	@SideOnly(Side.CLIENT)
	protected void updateClient(ItemStack stack, World world, EntityPlayer entity, int slot, boolean isCurrentItem) {
		super.updateClient(stack, world, entity, slot, isCurrentItem);
		
		boolean smoking = lastShot + 2000 > System.currentTimeMillis();
		
		if(!smoking && !smokeNodes.isEmpty()) {
			smokeNodes.clear();
		}
		
		if(smoking) {
			
			Vec3 prev = Vec3.createVectorHelper(-entity.motionX, -entity.motionY, -entity.motionZ);
			prev.rotateAroundY((float) (entity.rotationYaw * Math.PI / 180D));
			double accel = 15D;
			double side = (entity.rotationYaw - entity.prevRotationYawHead) * 0.1D;
			double waggle = 0.025D;
			
			for(double[] node : smokeNodes) {
				node[0] += prev.xCoord * accel + world.rand.nextGaussian() * waggle + side;
				node[1] += prev.yCoord + 1.5D;
				node[2] += prev.zCoord * accel + world.rand.nextGaussian() * waggle;
			}
			
			double alpha = (System.currentTimeMillis() - ItemGunBio.lastShot) / 2000D;
			alpha = (1 - alpha) * 0.5D;
			
			if(this.getIsReloading(stack)) alpha = 0;
			
			smokeNodes.add(new double[] {0, 0, 0, alpha});
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BusAnimation getAnimation(ItemStack stack, AnimType type) {
		
		 if(type == AnimType.CYCLE) {
			lastShot = System.currentTimeMillis();
			return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addPos(0, 0, 0, 50)
						.addPos(0, 0, -3, 50)
						.addPos(0, 0, 0, 250)
						)
				.addBus("HAMMER", new BusAnimationSequence()
						.addPos(0, 0, 1, 50)
						.addPos(0, 0, 1, 300)
						.addPos(0, 0, 0, 200)
						)
				.addBus("DRUM", new BusAnimationSequence()
						.addPos(0, 0, 1, 50)
						);
		 }
		
		 if(type == AnimType.RELOAD) {
			return new BusAnimation()
				.addBus("LATCH", new BusAnimationSequence()
							.addPos(0, 0, 90, 300)
							.addPos(0, 0, 90, 2000)
							.addPos(0, 0, 0, 150)
						)
				.addBus("FRONT", new BusAnimationSequence()
							.addPos(0, 0, 0, 200)
							.addPos(0, 0, 45, 150)
							.addPos(0, 0, 45, 2000)
							.addPos(0, 0, 0, 75)
						)
				.addBus("RELOAD_ROT", new BusAnimationSequence()
							.addPos(0, 0, 0, 300)
							.addPos(60, 0, 0, 500)
							.addPos(60, 0, 0, 500)
							.addPos(0, -90, -90, 0)
							.addPos(0, -90, -90, 600)
							.addPos(0, 0, 0, 300)
							.addPos(0, 0, 0, 100)
							.addPos(-45, 0, 0, 50)
							.addPos(-45, 0, 0, 100)
							.addPos(0, 0, 0, 300)
						)
				.addBus("RELOAD_MOVE", new BusAnimationSequence()
							.addPos(0, 0, 0, 300)
							.addPos(0, -15, 0, 1000)
							.addPos(0, 0, 0, 450)
						)
				.addBus("DRUM_PUSH", new BusAnimationSequence()
							.addPos(0, 0, 0, 1600)
							.addPos(0, 0, -5, 0)
							.addPos(0, 0, 0, 300)
						);
		 }
		
		 return null;
	}
}
