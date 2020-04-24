package mod.saxaphonewalrus.meta;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiDrawer extends Gui {

	private final Minecraft mc = Minecraft.getMinecraft();
	private int mcDisplayWidth = -1, mcDisplayHeight = -1;
	private int rectX1, rectX2;
	private int rectColour, textColour;
	private int rectSnY1, rectSnY2, rectSpY1, rectSpY2;
	private String toolText;

	public GuiDrawer() {
		super();
	}

	public void changeText(String toolText, int textColour) {

		this.toolText = toolText;
		this.rectColour = 0xBB1D1D1D;
		this.textColour = textColour;
		mcDisplayWidth = -1;
		mcDisplayHeight = -1;
	}

	@SubscribeEvent
	public void afterDraw (RenderGameOverlayEvent.Post event) {

		if (event.getType() != ElementType.ALL || this.toolText == null) return;
		computeDrawPosIfChanged();
		drawRect(rectX1, rectSpY1, rectX2, rectSpY2, this.rectColour);
		drawString(mc.fontRenderer , this.toolText, rectX1 + 2, rectSpY1 + 2, this.textColour);
	}

	public void computeDrawPosIfChanged() {

		if ((mcDisplayWidth == mc.displayWidth) && (mcDisplayHeight == mc.displayHeight)) return;

		ScaledResolution scaledresolution = new ScaledResolution(mc);

		int displayWidth = scaledresolution.getScaledWidth();
		int textWidth = mc.fontRenderer .getStringWidth(this.toolText);
		rectX1 = (displayWidth / 2) - (textWidth / 2) - 2;
		rectX2 = rectX1 + 2 + textWidth + 2;

		int displayHeight = scaledresolution.getScaledHeight();
		int textHeight = mc.fontRenderer .FONT_HEIGHT;
		rectSnY1 = 2;
		rectSnY2 = rectSnY1 + textHeight + 2;
		rectSpY1 = rectSnY2;
		rectSpY2 = rectSpY1 + textHeight + 2;

		mcDisplayWidth = mc.displayWidth;
		mcDisplayHeight = mc.displayHeight;
	}

	public void computeTextPos(String displayTxt) {

		ScaledResolution scaledresolution = new ScaledResolution(mc);

		int displayWidth = scaledresolution.getScaledWidth();
		int textWidth = mc.fontRenderer .getStringWidth(displayTxt);
		rectX1 = 2;
		rectX2 = rectX1 + 2 + textWidth + 2;

		int displayHeight = scaledresolution.getScaledHeight();
		rectSnY1 = displayHeight - 2;
	}

}
