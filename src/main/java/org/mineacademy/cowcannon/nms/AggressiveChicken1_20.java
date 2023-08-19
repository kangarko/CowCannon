/*package org.mineacademy.cowcannon.nms;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.persistence.PersistentDataType;
import org.mineacademy.cowcannon.CowCannon;

import javax.annotation.Nullable;

public class AggressiveChicken1_20 extends Chicken {

	public static final NamespacedKey KEY = new NamespacedKey(CowCannon.getInstance(), "DeadlyChicken");

	public AggressiveChicken1_20(Location loc) {
		super(EntityType.CHICKEN, ((CraftWorld) loc.getWorld()).getHandle());

		this.setPosRaw(loc.getX(), loc.getY(), loc.getZ());

		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

		this.getBukkitEntity().getPersistentDataContainer().set(KEY, PersistentDataType.BOOLEAN, true);

		this.persist = true;
		((CraftWorld) loc.getWorld()).getHandle().addFreshEntity(this, SpawnReason.CUSTOM);
	}

	@Override
	public AttributeMap getAttributes() {
		return new AttributeMap(Zombie.createAttributes().build());
	}

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return SoundEvents.WITHER_AMBIENT;
	}

	@Override
	protected @Nullable SoundEvent getHurtSound(DamageSource damagesource) {
		return SoundEvents.WITHER_SKELETON_HURT;
	}

	@Override
	protected @Nullable SoundEvent getDeathSound() {
		return SoundEvents.WITHER_SKELETON_DEATH;
	}
}*/