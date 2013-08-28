package cgNoCreeper;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class NoCreeperCreeper extends EntityCreeper {
	private int lastActiveTime;
	private int timeSinceIgnited;
	private int fuseTime = 30;
	private int explosionRadius = 3;

	public NoCreeperCreeper(World par1World) {
		super(par1World);
	}

	public boolean updateCreeper(int par1) {
		boolean flg = false;
		if (par1 == 1) {
			flg = false;
			explosionRadius = 4;
			fuseTime = 30;
		}
		if (par1 == 2) {
			flg = true;
			explosionRadius = 3;
			fuseTime = 30;
		}
		if (par1 == 3) {
			flg = true;
			explosionRadius = 5;
			fuseTime = 20;
		}
		return flg;
	}

	public void onLivingUpdate() {
		if (this.worldObj.isDaytime() && !this.worldObj.isRemote) {
			float f = this.getBrightness(1.0F);

			if (f > 0.3F
					&& this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F
					&& this.worldObj.canBlockSeeTheSky(
							MathHelper.floor_double(this.posX),
							MathHelper.floor_double(this.posY),
							MathHelper.floor_double(this.posZ))) {
				this.setFire(100); // BURN!
			}
		}
		super.onLivingUpdate();
	}

	public void onUpdate() {
		if (this.isEntityAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;
			int i = this.getCreeperState();

			if (i > 0 && this.timeSinceIgnited == 0) {
				this.playSound("random.fuse", 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;

			if (this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;

				if (!this.worldObj.isRemote) {
					boolean flag = updateCreeper(this.worldObj.difficultySetting);
					if (this.getPowered()) {
						this.worldObj.createExplosion(this, this.posX,
								this.posY, this.posZ,
								(float) (explosionRadius * 2), flag);
					} else {
						this.worldObj.createExplosion(this, this.posX,
								this.posY, this.posZ, (float) explosionRadius,
								flag);
					}

					this.setDead();
				}
			}
		}
		super.onUpdate();
	}
}
