package fff.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class FFFPosition {
	public World world;
	public int x;
	public int y;
	public int z;

	public FFFPosition(World world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public FFFPosition(FFFPosition pos, ForgeDirection dir) {
		this.world = pos.world;
		this.x = pos.x + dir.offsetX;
		this.y = pos.y + dir.offsetY;
		this.z = pos.z + dir.offsetZ;
	}

	public boolean equals(FFFPosition pos) {
		return (pos.x == this.x) && (pos.y == this.y) && (pos.z == this.z);
	}

	public int get_block_id() {
		return world.getBlockId(x, y, z);
	}

	public int get_block_meta_data() {
		return world.getBlockMetadata(x, y, z);
	}
	
	public Block get_block_reg_instance() {
		return Block.blocksList[get_block_id()];
	}

	// ����ı亯��
	// --------------------------
	
	// ���÷��飬������ notifyBlocksOfNeighborChange
	public boolean set_block(int block_id) {
		return world.setBlock(x, y, z, block_id);
	}

	// ɾ�����飬������ notifyBlocksOfNeighborChange
	public boolean delete_block() {
		return set_block(0);
	}
	
	public boolean delete_block_with_notifyBlocksOfNeighborChange() {
		boolean re = delete_block();
		world.notifyBlocksOfNeighborChange(x, y, z, 0);
		return re;
	}

	// ʹ�õ�ǰλ�õķ��鰴Ĭ�ϵ���������
	// �ᴥ�� notifyBlocksOfNeighborChange
	public void drop_self() {
		int meta = get_block_meta_data();
		get_block_reg_instance().dropBlockAsItem(world, x, y, z, meta, 0);
		delete_block_with_notifyBlocksOfNeighborChange();
	}
	
	public void drop_self_at(int drop_x, int drop_y, int drop_z) {
		int meta = get_block_meta_data();
		get_block_reg_instance().dropBlockAsItem(world, drop_x, drop_y, drop_z, meta, 0);
		delete_block_with_notifyBlocksOfNeighborChange();
	}
	
	// �౶����
	public void drop_self_multiple_at(int drop_x, int drop_y, int drop_z, int multiple) {
		int meta = get_block_meta_data();
		for (int i = 0; i < multiple; i++) {
			get_block_reg_instance().dropBlockAsItem(world, drop_x, drop_y,
					drop_z, meta, 0);
		}
		delete_block_with_notifyBlocksOfNeighborChange();
	}
	
	// ����Ϊ
	public void drop_self_at_as(int drop_x, int drop_y, int drop_z, Block block) {
		block.dropBlockAsItem(world, drop_x, drop_y, drop_z, 0, 0);
		delete_block_with_notifyBlocksOfNeighborChange();
	}
	
	// �����ж�����
	public boolean is_of_kind(Block[] block_kinds) {
		int block_id = get_block_id();
		for (Block block : block_kinds) {
			if (block_id == block.blockID) return true;
		}
		return false;
	}

	
	// λ�û�ȡ����
	// ---------------------------------------------
	
	// ��ȡ��ǰ������Χ�����꣬�͵�ǰ����һ�𷵻أ���27��
	public List<FFFPosition> get_positions_around() {
		List<FFFPosition> re = new ArrayList<FFFPosition>();
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
					FFFPosition pos = new FFFPosition(world, x + dx, y + dy, z
							+ dz);
					re.add(pos);
				}
			}
		}
		return re;
	}

	// ��ȡ��ǰ����ˮƽλ�õ����꣬�͵�ǰ����һ�𷵻أ���9��
	public List<FFFPosition> get_horizontal_positions_around() {
		List<FFFPosition> re = new ArrayList<FFFPosition>();
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				FFFPosition pos = new FFFPosition(world, x + dx, y, z + dz);
				re.add(pos);
			}
		}
		return re;
	}
}
