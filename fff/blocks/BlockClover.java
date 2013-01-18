package fff.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.creativetab.CreativeTabs;
import fff.FFFMOD;
import fff.proxy.ClientProxy;

public class BlockClover extends BlockFlower {
	public BlockClover(int par1) {
		super(par1, 18); // ��ߵڶ���������ͼ������ 
		
		setBlockName("block_clover");
		setTextureFile(ClientProxy.ITEMS_PNG_PATH);
		setCreativeTab(CreativeTabs.tabDecorations);
		
		setLightValue(1);
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int block_id) {
		// ���ǵ���Ҷ����Ҫ������ �ݣ������ʯ��ɳ
		
		return block_id == Block.grass.blockID || 
				block_id == Block.dirt.blockID || 
				block_id == Block.tilledField.blockID ||
				block_id == Block.stone.blockID ||
				block_id == Block.sand.blockID;
	}
	
	@Override
	public int getRenderType() {
		return FFFMOD.RENDER_TYPE_BLOCK_CLOVER;
	}
}
