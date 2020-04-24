package mod.saxaphonewalrus.meta;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;
import java.util.List;

@Mod(modid = EffectiveTool.MODID, name = EffectiveTool.NAME, version = EffectiveTool.VERSION)
public class EffectiveTool {

	public static final String MODID = "meta";
	public static final String NAME = "Most Effective Tool for Annihilation";
	public static final String VERSION = "1.0";
	private final Minecraft mc = Minecraft.getMinecraft();
	private final GuiDrawer GuiDrawer = new GuiDrawer();
	public Block previousBlock;
	public ItemStack previousTool;

	public List<Block> unbreakable;
	public Block[] prefersToolAxe;
	public Block[] prefersToolPickaxe;
	public Block[] prefersToolShears;

	public List<Block> prefersToolSword;


	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(GuiDrawer);
		GuiDrawer.changeText("", 0xAAAAAAAA);

		unbreakable = Arrays.asList(Blocks.END_PORTAL_FRAME, Blocks.BEDROCK);

		// Why are there so many blocks that don't have the proper tool assigned in MC? Fucking Mojang.
		prefersToolPickaxe = new Block[]{Blocks.DISPENSER, Blocks.STONE_STAIRS, Blocks.BRICK_STAIRS, Blocks.STONE_BRICK_STAIRS, Blocks.NETHER_BRICK_STAIRS, Blocks.SANDSTONE_STAIRS,
				Blocks.QUARTZ_STAIRS, Blocks.RED_SANDSTONE_STAIRS, Blocks.PURPUR_STAIRS, Blocks.QUARTZ_BLOCK, Blocks.BRICK_BLOCK, Blocks.FURNACE, Blocks.LIT_FURNACE,
				Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.IRON_TRAPDOOR, Blocks.IRON_DOOR, Blocks.STONEBRICK,
				Blocks.IRON_BARS, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK_FENCE, Blocks.CAULDRON, Blocks.END_BRICKS, Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR, Blocks.PURPUR_SLAB,
				Blocks.PURPUR_DOUBLE_SLAB, Blocks.DOUBLE_STONE_SLAB2, Blocks.STONE_SLAB2, Blocks.COBBLESTONE_WALL, Blocks.END_STONE, Blocks.ENDER_CHEST, Blocks.BONE_BLOCK, Blocks.CONCRETE,
				Blocks.MAGMA, Blocks.PRISMARINE, Blocks.RED_NETHER_BRICK, Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY, Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA,
				Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.LIME_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA,
				Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, Blocks.SILVER_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA,
				Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.DROPPER, Blocks.PISTON, Blocks.PISTON_HEAD, Blocks.PISTON_EXTENSION, Blocks.STICKY_PISTON, Blocks.BREWING_STAND, Blocks.LEVER};

		prefersToolAxe = new Block[]{Blocks.NOTEBLOCK, Blocks.OAK_STAIRS, Blocks.SPRUCE_STAIRS, Blocks.BIRCH_STAIRS, Blocks.JUNGLE_STAIRS, Blocks.ACACIA_STAIRS, Blocks.DARK_OAK_STAIRS,
				Blocks.CRAFTING_TABLE, Blocks.STANDING_SIGN, Blocks.WALL_SIGN, Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.BIRCH_DOOR, Blocks.JUNGLE_DOOR, Blocks.ACACIA_DOOR, Blocks.DARK_OAK_DOOR,
				Blocks.JUKEBOX, Blocks.ACACIA_FENCE, Blocks.BIRCH_FENCE, Blocks.DARK_OAK_FENCE, Blocks.JUNGLE_FENCE, Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE, Blocks.ACACIA_FENCE_GATE,
				Blocks.ACACIA_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.TRAPDOOR,
				Blocks.WOODEN_SLAB, Blocks.DOUBLE_WOODEN_SLAB, Blocks.TRAPPED_CHEST, Blocks.CHORUS_FLOWER, Blocks.CHORUS_PLANT};

		prefersToolShears = new Block[]{Blocks.WEB, Blocks.WOOL, Blocks.CARPET, Blocks.LEAVES, Blocks.LEAVES2};
		prefersToolSword = Arrays.asList(Blocks.WEB, Blocks.LEAVES, Blocks.LEAVES2);

		SetCorrectTool();


	}

	public void SetCorrectTool() {
		for (Block block : prefersToolPickaxe) {
			block.setHarvestLevel("pickaxe", 0);
		}
		for (Block block : prefersToolAxe) {
			block.setHarvestLevel("axe", 0);
		}
		for (Block block : prefersToolShears) {
			block.setHarvestLevel("shears", 0);
		}
		for (Block block : unbreakable) {
			block.setHarvestLevel("unbreakable", 9001);
		}
	}

	@SubscribeEvent
	public void clientTick(TickEvent.ClientTickEvent event) {

		clientTick();
	}

	public void clientTick() {
		if (mc.player == null)
			return;
		RayTraceResult lookingAt = mc.objectMouseOver;
		if (lookingAt == null || lookingAt.typeOfHit != RayTraceResult.Type.BLOCK || mc.currentScreen != null) {
			GuiDrawer.changeText(null, 0xFFFFFFFF);
			previousBlock = null;
			return;
		}
		World world = mc.player.world;
		Block block = world.getBlockState(lookingAt.getBlockPos()).getBlock();
		ItemStack tool = mc.player.getHeldItemMainhand();
		if (block == previousBlock && tool == previousTool || block == Blocks.AIR)
			return;
		previousBlock = block;
		previousTool = tool;
		float toolSpeed = tool.getDestroySpeed(block.getDefaultState());
		String harvestTool = block.getHarvestTool(block.getDefaultState());
		if (prefersToolSword.contains(block)) {
			harvestTool = "sword or shears";
			System.out.println(harvestTool);
		}
		if (harvestTool == null)
			GuiDrawer.changeText("any tool", 0xFF00CC00);
		else if (toolSpeed == 1.0)
			GuiDrawer.changeText(harvestTool, 0xFFCC0000);
		else
			GuiDrawer.changeText(harvestTool, 0xFF00CC00);

	}
}
