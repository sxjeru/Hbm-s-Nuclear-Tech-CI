package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

public class ItemCoilgun extends ItemGunBase {

	public ItemCoilgun(GunConfiguration config) {
		super(config);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BusAnimation getAnimation(ItemStack stack, AnimType type) {
		
		if(type == AnimType.CYCLE) {
			return new BusAnimation()
					.addBus("RECOIL", new BusAnimationSequence()
							.addPos(1, 0, 0, 100)
							.addPos(0, 0, 0, 200));
		}
		
		if(type == AnimType.RELOAD) {
			return new BusAnimation()
					.addBus("RELOAD", new BusAnimationSequence()
							.addPos(1, 0, 0, 250)
							.addPos(1, 0, 0, 500)
							.addPos(0, 0, 0, 250));
		}
		
		GunConfiguration config = ((ItemGunBase) stack.getItem()).mainConfig;
		return config.animations.get(type);
	}
}
