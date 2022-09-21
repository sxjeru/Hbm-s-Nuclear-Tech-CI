package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCrucible;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityCrucible;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICrucible extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_crucible.png");
	private TileEntityCrucible crucible;

	public GUICrucible(InventoryPlayer invPlayer, TileEntityCrucible tedf) {
		super(new ContainerCrucible(invPlayer, tedf));
		crucible = tedf;
		
		this.xSize = 176;
		this.ySize = 214;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.crucible.hasCustomInventoryName() ? this.crucible.getInventoryName() : I18n.format(this.crucible.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int pGauge = crucible.progress * 33 / crucible.processTime;
		if(pGauge > 0) drawTexturedModalRect(guiLeft + 126, guiTop + 82, 176, 0, pGauge, 5);
		int hGauge = crucible.heat * 33 / crucible.maxHeat;
		if(hGauge > 0) drawTexturedModalRect(guiLeft + 126, guiTop + 91, 176, 5, hGauge, 5);

		if(!crucible.recipeStack.isEmpty()) drawStack(crucible.recipeStack, 62, 97);
		if(!crucible.wasteStack.isEmpty()) drawStack(crucible.wasteStack, 17, 97);
	}
	
	protected void drawStack(List<MaterialStack> stack, int x, int y) {
		
	}
}
