package org.mineacademy.cowcannon;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ButterflyTask implements Runnable {

	private static final ButterflyTask instance = new ButterflyTask();

	private final Set<UUID> viewingWings = new HashSet<>();

	private ButterflyTask() {
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers())
			if (hasPlayer(player.getUniqueId()))
				generateButterflyWingEffect(player);
	}

	private void generateButterflyWingEffect(Player player) {
		Location location = player.getLocation();

		location.add(location.getDirection().normalize().multiply(-0.3)); // move behind the player
		location.add(0, 0.85, 0); // push down to chest
		location.setPitch(0F); // stop vertical rotation, only make particles rotate to sides, not up and down

		for (double degree = 0; degree < 360; degree += 2) {
			final double radians = Math.toRadians(degree);

			final double wingSize = 0.35;
			final double circlesAmount = 4;

			final double circle = wingSize * Math.pow(Math.E, Math.cos(radians));
			final double radius = circle - Math.cos(circlesAmount * radians);

			final double x = Math.sin(radians) * radius;
			final double z = Math.cos(radians) * radius;

			final Vector particleLocation = new Vector(x, 0, z);

			rotateAroundAxisX(particleLocation, -90);
			rotateAroundAxisY(particleLocation, location.getYaw());

			player.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(particleLocation), 0, new DustOptions(Color.fromRGB(212, 146, 53), 0.6f));
		}
	}

	private Vector rotateAroundAxisX(Vector vector, double angle) {
		angle = Math.toRadians(angle);

		final double cos = Math.cos(angle);
		final double sin = Math.sin(angle);
		final double y = vector.getY() * cos - vector.getZ() * sin;
		final double z = vector.getY() * sin + vector.getZ() * cos;

		return vector.setY(y).setZ(z);
	}

	private Vector rotateAroundAxisY(Vector vector, double angle) {
		angle = -angle;
		angle = Math.toRadians(angle);

		final double cos = Math.cos(angle);
		final double sin = Math.sin(angle);
		final double x = vector.getX() * cos + vector.getZ() * sin;
		final double z = vector.getX() * -sin + vector.getZ() * cos;

		return vector.setX(x).setZ(z);
	}

	public void addPlayer(UUID uuid) {
		viewingWings.add(uuid);
	}

	public void removePlayer(UUID uuid) {
		viewingWings.remove(uuid);
	}

	public boolean hasPlayer(UUID uuid) {
		return viewingWings.contains(uuid);
	}

	public static ButterflyTask getInstance() {
		return instance;
	}
}
