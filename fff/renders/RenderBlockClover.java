package fff.renders;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import fff.FFFMOD;
import fff.proxy.ClientProxy;

public class RenderBlockClover implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		ForgeHooksClient.bindTexture(ClientProxy.BLOCKS_PNG_PATH, 0); // 调用底层的方法变更绑定的贴图
		block.blockIndexInTexture = 2; // 临时修改贴图 index
//		renderCrossedSquares(block, x, y, z, renderer);
		renderer.renderCrossedSquares(block, x, y, z);
		block.blockIndexInTexture = 18;
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return FFFMOD.RENDER_TYPE_BLOCK_CLOVER;
	}
	
    public boolean renderCrossedSquares(Block block, int x, int y, int z, RenderBlocks renderer)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        float var6 = 1.0F;
        int var7 = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
            float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
            float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }

        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double new_x = (double)x;
        double new_y = (double)y;
        double new_z = (double)z;

        if (block == Block.tallGrass)
        {
            long var17 = (long)(x * 3129871) ^ (long)z * 116129781L ^ (long)y;
            var17 = var17 * var17 * 42317861L + var17 * 11L;
            new_x += ((double)((float)(var17 >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
            new_y += ((double)((float)(var17 >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
            new_z += ((double)((float)(var17 >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
        }

        drawCrossedSquares(block, renderer.blockAccess.getBlockMetadata(x, y, z), new_x, new_y, new_z, 1.0F, renderer);
        return true;
    }
    
    public void drawCrossedSquares(Block block, int metadata, double new_x, double new_y, double new_z, float scale, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        int var11 = block.getBlockTextureFromSideAndMetadata(0, metadata);

        if (renderer.overrideBlockTexture >= 0)
        {
            var11 = renderer.overrideBlockTexture;
        }

        int var12 = (var11 & 15) << 4;
        int var13 = var11 & 240;
        
        double icon_left = (double)((float)var12 / 256.0F);
        double icon_right = (double)(((float)var12 + 15.99F) / 256.0F);
        double icon_x_center = (icon_left + icon_right) / 2;
        
        double icon_top = (double)((float)var13 / 256.0F);
        double icon_bottom = (double)(((float)var13 + 15.99F) / 256.0F);
                
        double mid_x = new_x + 0.5D;
        double mid_z = new_z + 0.5D;
        
        double sin60 = 0.866D; //Math.sin(60 * 3.14159265D / 180.0D);
        double sin30 = 0.5D; //Math.sin(30 * 3.14159265D / 180.0D);
        
        double x1 = mid_x - 0.5D * sin60;
        double x2 = mid_x;
        double x3 = mid_x + 0.5D * sin60;
        
        double z1 = mid_z + 0.5D * sin30;
        double z2 = new_z;
        double z3 = z1;
        
        double y1 = new_y + (double)scale;
        double y2 = new_y;
        
		double off = 0.03125D;

		double x1_off = off * sin60;
		double x2_off = 0;
		double x3_off = -x1_off;

		double z1_off = -off * sin30;
		double z2_off = off;
		double z3_off = z1_off;
        
        tessellator.addVertexWithUV(x1, y1, z1, icon_left, icon_top);
        tessellator.addVertexWithUV(x1, y2, z1, icon_left, icon_bottom);
        tessellator.addVertexWithUV(mid_x + x1_off, y2, mid_z + z1_off, icon_x_center, icon_bottom);
        tessellator.addVertexWithUV(mid_x + x1_off, y1, mid_z + z1_off, icon_x_center, icon_top);
        
        tessellator.addVertexWithUV(mid_x + x1_off, y1, mid_z + z1_off, icon_x_center, icon_top);
        tessellator.addVertexWithUV(mid_x + x1_off, y2, mid_z + z1_off, icon_x_center, icon_bottom);
        tessellator.addVertexWithUV(x1, y2, z1, icon_left, icon_bottom);
        tessellator.addVertexWithUV(x1, y1, z1, icon_left, icon_top);
        
        tessellator.addVertexWithUV(x2, y1, z2, icon_left, icon_top);
        tessellator.addVertexWithUV(x2, y2, z2, icon_left, icon_bottom);
        tessellator.addVertexWithUV(mid_x + x2_off, y2, mid_z + z2_off, icon_x_center, icon_bottom);
        tessellator.addVertexWithUV(mid_x + x2_off, y1, mid_z + z2_off, icon_x_center, icon_top);
        
        tessellator.addVertexWithUV(mid_x + x2_off, y1, mid_z + z2_off, icon_x_center, icon_top);
        tessellator.addVertexWithUV(mid_x + x2_off, y2, mid_z + z2_off, icon_x_center, icon_bottom);
        tessellator.addVertexWithUV(x2, y2, z2, icon_left, icon_bottom);
        tessellator.addVertexWithUV(x2, y1, z2, icon_left, icon_top);
        
        tessellator.addVertexWithUV(x3, y1, z3, icon_left, icon_top);
        tessellator.addVertexWithUV(x3, y2, z3, icon_left, icon_bottom);
        tessellator.addVertexWithUV(mid_x + x3_off, y2, mid_z + z3_off, icon_x_center, icon_bottom);
        tessellator.addVertexWithUV(mid_x + x3_off, y1, mid_z + z3_off, icon_x_center, icon_top);
        
        tessellator.addVertexWithUV(mid_x + x3_off, y1, mid_z + z3_off, icon_x_center, icon_top);
        tessellator.addVertexWithUV(mid_x + x3_off, y2, mid_z + z3_off, icon_x_center, icon_bottom);
        tessellator.addVertexWithUV(x3, y2, z3, icon_left, icon_bottom);
        tessellator.addVertexWithUV(x3, y1, z3, icon_left, icon_top);
    }
    
//    public void drawCrossedSquares(Block block, int metadata, double new_x, double new_y, double new_z, float scale, RenderBlocks renderer)
//    {
//        Tessellator var10 = Tessellator.instance;
//        int var11 = block.getBlockTextureFromSideAndMetadata(0, metadata);
//
//        if (renderer.overrideBlockTexture >= 0)
//        {
//            var11 = renderer.overrideBlockTexture;
//        }
//
//        int var12 = (var11 & 15) << 4;
//        int var13 = var11 & 240;
//        double icon_left = (double)((float)var12 / 256.0F);
//        double icon_right = (double)(((float)var12 + 15.99F) / 256.0F);
//        double icon_top = (double)((float)var13 / 256.0F);
//        double icon_bottom = (double)(((float)var13 + 15.99F) / 256.0F);
//        
//        double horizontal_off = 0.45D * (double)scale;
//        
//        double x1 = new_x + 0.5D - horizontal_off;
//        double x2 = new_x + 0.5D + horizontal_off;
//        
//        double z1 = new_z + 0.5D - horizontal_off;
//        double z2 = new_z + 0.5D + horizontal_off;
//        
//        double y1 = new_y + (double)scale;
//        double y2 = new_y;
//        
//        var10.addVertexWithUV(x1, y1, z1, icon_left, icon_top);
//        var10.addVertexWithUV(x1, y2, z1, icon_left, icon_bottom);
//        var10.addVertexWithUV(x2, y2, z2, icon_right, icon_bottom);
//        var10.addVertexWithUV(x2, y1, z2, icon_right, icon_top);
//        
//        var10.addVertexWithUV(x2, y1, z2, icon_left, icon_top);
//        var10.addVertexWithUV(x2, y2, z2, icon_left, icon_bottom);
//        var10.addVertexWithUV(x1, y2, z1, icon_right, icon_bottom);
//        var10.addVertexWithUV(x1, y1, z1, icon_right, icon_top);
//        
//        var10.addVertexWithUV(x1, y1, z2, icon_left, icon_top);
//        var10.addVertexWithUV(x1, y2, z2, icon_left, icon_bottom);
//        var10.addVertexWithUV(x2, y2, z1, icon_right, icon_bottom);
//        var10.addVertexWithUV(x2, y1, z1, icon_right, icon_top);
//        
//        var10.addVertexWithUV(x2, y1, z1, icon_left, icon_top);
//        var10.addVertexWithUV(x2, y2, z1, icon_left, icon_bottom);
//        var10.addVertexWithUV(x1, y2, z2, icon_right, icon_bottom);
//        var10.addVertexWithUV(x1, y1, z2, icon_right, icon_top);
//    }
}
