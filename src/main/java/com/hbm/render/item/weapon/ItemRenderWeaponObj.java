package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponObj implements IItemRenderer {
	
	public ItemRenderWeaponObj() { }

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			return true;
		case INVENTORY:
			return item.getItem() == ModItems.gun_hk69
					|| item.getItem() == ModItems.gun_deagle
					|| item.getItem() == ModItems.gun_flechette; 
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if(item.getItem() == ModItems.gun_hk69)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.hk69_tex);

		if(item.getItem() == ModItems.gun_deagle)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.universal_bright);

		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			

			if(item.getItem() == ModItems.gun_hk69) {
				GL11.glTranslatef(1.0F, 0.5F, 0.0F);
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-25F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-10F, 0.0F, 1.0F, 0.0F);
				GL11.glScaled(0.75, 0.75, 0.75);
				
				if(player.isSneaking()) {
					GL11.glTranslatef(1.16F, 0.35F, -0.8F);
					GL11.glRotatef(5.5F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(4F, 1.0F, 0.0F, 0.0F);
				}
			}

			if(item.getItem() == ModItems.gun_deagle) {
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(25F, -1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.2F, 0.2F, 0.8F);
				GL11.glScaled(0.2, 0.2, 0.2);
				
				if(player.isSneaking()) {
					GL11.glTranslatef(3.7F, 1.7F, 0F);
					GL11.glRotatef(-5F, 0.0F, 1.0F, 0.0F);
				}
			}

			if(item.getItem() == ModItems.gun_flechette) {
				
				GL11.glRotatef(25F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(1.0F, -1.0F, -0.0F);
				GL11.glRotatef(170F, 0.0F, 1.0F, 0.0F);
				GL11.glScaled(0.25, 0.25, 0.25);
				
				if(player.isSneaking()) {
					GL11.glRotatef(5F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-2F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(-2.0F, 1.2F, 3.7F);
				}

				double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
				GL11.glTranslated(recoil[0], recoil[1], recoil[2]);
			}
			
			break;
			
		case EQUIPPED:

			if(item.getItem() == ModItems.gun_hk69) {
				GL11.glRotatef(20F, 1.0F, 0.0F, 1.0F);
				GL11.glRotatef(10F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.4F, 0.0F, 0.55F);
				GL11.glScaled(0.75, 0.75, 0.75);
			}

			if(item.getItem() == ModItems.gun_deagle) {
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(35F, -1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.1F, 0.8F);
				GL11.glScaled(0.15, 0.15, 0.15);
			}

			if(item.getItem() == ModItems.gun_flechette) {
				GL11.glRotatef(35F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-1.4F, -0.55F, 0.0F);
				GL11.glScaled(0.125, 0.125, 0.125);
			}
			
			break;
			
		case ENTITY:
			
			if(item.getItem() == ModItems.gun_hk69) {
				GL11.glTranslatef(0.0F, 0.2F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glScaled(0.75, 0.75, 0.75);
			}

			if(item.getItem() == ModItems.gun_deagle) {
				GL11.glTranslatef(0.0F, 0.2F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glScaled(0.25, 0.25, 0.25);
			}
			
			if(item.getItem() == ModItems.gun_flechette) {
				GL11.glTranslatef(-0.25F, 0.0F, 0.0F);
				GL11.glScaled(0.125, 0.125, 0.125);
			}
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			if(item.getItem() == ModItems.gun_hk69) {
				GL11.glScaled(7.5, 7.5, -7.5);
				GL11.glTranslatef(0.85F, 1.2F, 0.0F);
				GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-45F, 1.0F, 0.0F, 0.0F);
			}
			
			if(item.getItem() == ModItems.gun_deagle) {
				GL11.glScaled(2.5, 2.5, -2.5);
				GL11.glTranslatef(3.5F, 3F, 0.0F);
				GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-45F, 1.0F, 0.0F, 0.0F);
			}
			
			if(item.getItem() == ModItems.gun_flechette) {
				GL11.glScaled(1.2, 1.2, -1.2);
				GL11.glTranslatef(2.5F, 8.0F, 0.0F);
				GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-45F, 0.0F, 0.0F, 1.0F);
			}
			
			break;
			
		default: break;
		}

		if(item.getItem() == ModItems.gun_hk69) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.hk69.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}

		if(item.getItem() == ModItems.gun_deagle) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.deagle.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}
		
		if(item.getItem() == ModItems.gun_flechette) {
			renderFlechette();
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
	
	public void renderFlechette() {
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flechette_body);
		ResourceManager.flechette.renderPart("body");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flechette_chamber);
		ResourceManager.flechette.renderPart("chamber");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flechette_barrel);
		ResourceManager.flechette.renderPart("barrel");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flechette_gren_tube);
		ResourceManager.flechette.renderPart("gren_tube");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flechette_grenades);
		ResourceManager.flechette.renderPart("grenades");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flechette_pivot);
		ResourceManager.flechette.renderPart("pivot");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flechette_top);
		ResourceManager.flechette.renderPart("top");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flechette_drum);
		ResourceManager.flechette.renderPart("drum");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flechette_base);
		ResourceManager.flechette.renderPart("base");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flechette_trigger);
		ResourceManager.flechette.renderPart("trigger");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flechette_stock);
		ResourceManager.flechette.renderPart("stock");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
