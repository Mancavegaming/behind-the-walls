package com.beyondthewalls.client;

import com.beyondthewalls.BeyondTheWalls;
import com.beyondthewalls.init.ModItems;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GasMeterOverlay implements LayeredDraw.Layer {

    private static final ResourceLocation ICON_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BeyondTheWalls.MODID, "textures/gui/gas_meter.png");

    private static final int ICON_SIZE = 16;
    private static final int BG_PADDING = 3;
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        // Only show when holding Sky Rigging in either hand
        boolean holdingRigging = isHoldingSkyRigging(player);
        if (!holdingRigging) return;

        // Count total thrust cartridges in inventory
        int cartridgeCount = countCartridges(player);

        // Position: above hotbar, right side
        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        String countText = "x" + cartridgeCount;
        int textWidth = mc.font.width(countText);

        int totalWidth = ICON_SIZE + 2 + textWidth;
        int bgWidth = totalWidth + BG_PADDING * 2;
        int bgHeight = ICON_SIZE + BG_PADDING * 2;

        // Position to the right of the hotbar center area
        int bgX = screenWidth / 2 + 95;
        int bgY = screenHeight - 22 - bgHeight;

        // Draw semi-transparent dark background
        guiGraphics.fill(bgX, bgY, bgX + bgWidth, bgY + bgHeight, 0xAA000000);

        // Draw cartridge icon
        int iconX = bgX + BG_PADDING;
        int iconY = bgY + BG_PADDING;
        guiGraphics.blit(ICON_TEXTURE, iconX, iconY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);

        // Draw count text
        int textX = iconX + ICON_SIZE + 2;
        int textY = iconY + (ICON_SIZE - mc.font.lineHeight) / 2;
        int textColor = cartridgeCount > 0 ? 0xFFD4A840 : 0xFFFF4444;
        guiGraphics.drawString(mc.font, countText, textX, textY, textColor);
    }

    private boolean isHoldingSkyRigging(Player player) {
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        return mainHand.is(ModItems.SKY_RIGGING.get()) || offHand.is(ModItems.SKY_RIGGING.get());
    }

    private int countCartridges(Player player) {
        int count = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(ModItems.THRUST_CARTRIDGE.get())) {
                count += stack.getCount();
            }
        }
        return count;
    }
}
