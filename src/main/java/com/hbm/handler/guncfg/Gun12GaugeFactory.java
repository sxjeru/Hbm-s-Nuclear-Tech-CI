package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHurtBehavior;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums.Ammo12Gauge;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.potion.HbmPotion;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;

public class Gun12GaugeFactory {
	
	private static final CasingEjector EJECTOR_SPAS, EJECTOR_SPAS_ALT, EJECTOR_BENELLI, EJECTOR_UBOINIK, EJECTOR_SSG;
	private static final SpentCasing CASING12GAUGE;

	static {
		EJECTOR_SPAS = new CasingEjector().setMotion(Vec3.createVectorHelper(-0.4, 0.1, 0)).setOffset(Vec3.createVectorHelper(-0.35, 0, 0.5)).setAngleRange(0.01F, 0.03F).setDelay(10);
		EJECTOR_SPAS_ALT = new CasingEjector().setMotion(Vec3.createVectorHelper(-0.4, 0.1, 0)).setOffset(Vec3.createVectorHelper(-0.35, 0, 0.5)).setAngleRange(0.01F, 0.03F).setDelay(10).setAmount(2);
		EJECTOR_BENELLI = new CasingEjector().setMotion(Vec3.createVectorHelper(-0.4, 0.1, 0)).setOffset(Vec3.createVectorHelper(-0.3, 1, 0)).setAngleRange(0.01F, 0.03F);
		EJECTOR_UBOINIK = new CasingEjector().setMotion(Vec3.createVectorHelper(-0.4, 0.1, 0)).setOffset(Vec3.createVectorHelper(-0.35, -0.3, 0.5)).setAngleRange(0.01F, 0.03F);
		EJECTOR_SSG = new CasingEjector().setMotion(Vec3.createVectorHelper(0.2, 0, -0.2)).setOffset(Vec3.createVectorHelper(0.8, 0, 0)).setAngleRange(0.05F, 0.02F).setDelay(20).setAmount(2);
		
		CASING12GAUGE = new SpentCasing(CasingType.SHOTGUN).setScale(1.5F).setBounceMotion(0.05F, 0.02F);
	}
	
	public static GunConfiguration getSpas12Config() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 25;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 5;
		config.ammoCap = 8;
		config.durability = 2500;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.shotgunPump";
		
		config.name = "spas12";
		config.manufacturer = EnumGunManufacturer.BLACK_MESA;
		config.comment.add("\"Here, I have a more suitable gun for you. You'll need it - Catch!\"");
		config.comment.add("Alt-fire with Mouse 2 (Right-click) to fire 2 shells at once");
		
		config.config = HbmCollection.twelveGauge;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("SPAS_RECOIL_TRANSLATE", new BusAnimationSequence()
					.addKeyframe(new BusAnimationKeyframe(0, 0, -2, 100))
					.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 200))
					)
				.addBus("SPAS_RECOIL_ROT", new BusAnimationSequence()
					.addKeyframe(new BusAnimationKeyframe(-1, 0, 1, 100))
					.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 200))
					)
				.addBus("SPAS_PUMP", new BusAnimationSequence()
					.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 450))
					.addKeyframe(new BusAnimationKeyframe(0, 0, -1.8, 200))
					.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 200))
					)
				);
		
		config.ejector = EJECTOR_SPAS;
		
		return config;
	}
	
	public static GunConfiguration getSpas12AltConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 35;
		config.roundsPerCycle = 2;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.firingDuration = 10;
		config.ammoCap = 8;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.shotgunPump";
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G12_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G12_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.G12_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G12_DU);
		config.config.add(BulletConfigSyncingUtil.G12_AM);
		config.config.add(BulletConfigSyncingUtil.G12_SLEEK);
		
		config.ejector = EJECTOR_SPAS_ALT;

		return config;
	}
	
	public static GunConfiguration getUboinikConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 8;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.durability = 1500;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = "hbm:weapon.shotgunShoot";
		
		config.name = "uboinik";
		config.manufacturer = EnumGunManufacturer.METRO;

		config.config = HbmCollection.twelveGauge;
		
		config.ejector = EJECTOR_UBOINIK;
		
		return config;
	}
	
	public static GunConfiguration getShottyConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 20;
		config.roundsPerCycle = 2;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.durability = 3000;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = "hbm:weapon.shottyShoot";
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("SHOTTY_RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0.5, 0, 0, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 50))
						)
				.addBus("SHOTTY_BREAK", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 100))	//do nothing for 100ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 60, 200))	//open
						.addKeyframe(new BusAnimationKeyframe(0, 0, 60, 500))	//do nothing for 500ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 200))	//close
						)
				.addBus("SHOTTY_EJECT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))	//do nothing for 300ms
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 700))	//fling!
						)
				.addBus("SHOTTY_INSERT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))	//do nothing for 300ms
						.addKeyframe(new BusAnimationKeyframe(1, 0, 1, 0))		//reposition
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 350))	//come in from the side
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 150))	//push
						)
				);
		
		config.name = "supershotty";
		config.manufacturer = EnumGunManufacturer.UAC;
		config.comment.add("God-damned ARCH-VILES!");
		
		config.config = HbmCollection.twelveGauge;
		
		config.ejector = EJECTOR_SSG;
		
		return config;
	}
	
	public static BulletConfiguration get12GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge.stackFromEnum(Ammo12Gauge.STOCK));
		bullet.dmgMin = 5;
		bullet.dmgMax = 7;
		
		bullet.spentCasing = CASING12GAUGE.clone().register("12GaStock").setColor(0x2847FF, SpentCasing.COLOR_CASE_12GA);
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeFireConfig() {
		
		BulletConfiguration bullet = get12GaugeConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge.stackFromEnum(Ammo12Gauge.INCENDIARY));
		bullet.wear = 15;
		bullet.dmgMin = 5;
		bullet.dmgMax = 7;
		bullet.incendiary = 5;
		
		bullet.spentCasing = CASING12GAUGE.clone().register("12GaInc").setColor(0xFF6329, SpentCasing.COLOR_CASE_12GA);
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeShrapnelConfig() {
		
		BulletConfiguration bullet = get12GaugeConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge.stackFromEnum(Ammo12Gauge.SHRAPNEL));
		bullet.wear = 15;
		bullet.dmgMin = 10;
		bullet.dmgMax = 17;
		bullet.ricochetAngle = 15;
		bullet.HBRC = 80;
		bullet.LBRC = 95;
		
		bullet.spentCasing = CASING12GAUGE.clone().register("12GaShrap").setColor(0xF0E800, SpentCasing.COLOR_CASE_12GA);
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge.stackFromEnum(Ammo12Gauge.DU));
		bullet.wear = 20;
		bullet.dmgMin = 18;
		bullet.dmgMax = 22;
		bullet.doesPenetrate = true;
		bullet.leadChance = 50;
		
		bullet.spentCasing = CASING12GAUGE.clone().register("12GaDU").setColor(0x62A362, SpentCasing.COLOR_CASE_12GA);
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeAMConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge.stackFromEnum(Ammo12Gauge.MARAUDER));
		bullet.wear = 20;
		bullet.dmgMin = 100;
		bullet.dmgMax = 500;
		bullet.leadChance = 50;
		
		bullet.bHurt = new IBulletHurtBehavior() {

			@Override
			public void behaveEntityHurt(EntityBulletBase bullet, Entity hit) {
				
				if(hit instanceof EntityLivingBase)
					((EntityLivingBase)hit).addPotionEffect(new PotionEffect(HbmPotion.bang.id, 20, 0));
			}
			
		};
		
		bullet.spentCasing = CASING12GAUGE.clone().register("12GaAM").setColor(0x416645, SpentCasing.COLOR_CASE_12GA);
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardAirstrikeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge.stackFromEnum(Ammo12Gauge.SLEEK));
		
		bullet.spentCasing = CASING12GAUGE.clone().register("12GaIF").setColor(0x2A2A2A, SpentCasing.COLOR_CASE_12GA);
		
		return bullet;
	}

}
