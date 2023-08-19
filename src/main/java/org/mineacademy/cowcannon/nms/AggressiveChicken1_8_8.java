/*package org.mineacademy.cowcannon.nms;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.mineacademy.cowcannon.CowCannon;

public class AggressiveChicken1_8_8 extends EntityChicken {

	public AggressiveChicken1_8_8(Location loc) {
		super(((CraftWorld) loc.getWorld()).getHandle());

		this.setPosition(loc.getX(), loc.getY(), loc.getZ());

		this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.0, true));
		this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityPlayer.class, 8.0F));
		this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));

		this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityPlayer.class, true));

		this.getBukkitEntity().setMetadata("DeadlyChicken", new org.bukkit.metadata.FixedMetadataValue(CowCannon.getInstance(), true));
		//this.getBukkitEntity().getPersistentDataContainer().set(KEY, PersistentDataType.BOOLEAN, true);

		this.persistent = true;
		((CraftWorld) loc.getWorld()).getHandle().addEntity(this, SpawnReason.CUSTOM);

		EntityZombie e;
	}

	@Override
	protected void initAttributes() {
		super.initAttributes();

		this.getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE);
	}

	//@Override
	//public AttributeMapBase getAttributeMap() {
	//	return new AttributeMapBase(EntityZombie.createAttributes().build());
	//}

	@Override
	protected String z() {
		return "mob.zombie.say";
	}

	@Override
	protected String bo() {
		return "mob.zombie.hurt";
	}

	@Override
	protected String bp() {
		return "mob.zombie.death";
	}
}*/