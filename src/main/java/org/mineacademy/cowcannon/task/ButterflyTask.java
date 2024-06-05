package org.mineacademy.cowcannon.task;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class ButterflyTask implements Runnable {

	private static final ButterflyTask instance = new ButterflyTask();

	private final Set<UUID> viewingWings = new HashSet<>();

	private ButterflyTask() {
	}

	@Override
	public void run() {
		for (final Player player : Bukkit.getOnlinePlayers())
			if (hasPlayer(player.getUniqueId()))
				generateButterflyWingEffect(player);
	}

	private void generateButterflyWingEffect(Player player) {
		final Location location = player.getLocation();

		location.add(location.getDirection().normalize().multiply(-0.3)); // move behind the player
		location.add(0, 0.85, 0); // push down to chest
		location.setPitch(0F); // stop vertical rotation, only make particles rotate to sides, not up and down

		final double wingSize = 0.35;
		final double circlesAmount = 4;

		for (double degree = 0; degree < 360; degree += 2 /* particle density */) {
			final double radians = Math.toRadians(degree);

			final double circle = wingSize * Math.pow(Math.E, Math.cos(radians));
			final double radius = circle - Math.cos(circlesAmount * radians);

			final double x = Math.sin(radians) * radius;
			final double z = Math.cos(radians) * radius;

			final Vector particleLocation = new Vector(x, 0, z);

			rotateAroundAxisX(particleLocation, -90);
			rotateAroundAxisY(particleLocation, location.getYaw());

			try {
				final Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(212, 146, 53), 0.6F);

				Particle particle;

				try {
					particle = Particle.DUST;

				} catch (final Throwable t) { // Spigot 1.20.5+ changed names
					particle = Particle.valueOf("REDSTONE");
				}

				player.getWorld().spawnParticle(particle, location.clone().add(particleLocation), 0, dust);

			} catch (final Throwable t) {
				// Unsupported
			}
		}
	}

	private void rotateAroundAxisX(Vector vector, double angle) {
		angle = Math.toRadians(angle);

		final double cos = Math.cos(angle);
		final double sin = Math.sin(angle);
		final double y = vector.getY() * cos - vector.getZ() * sin;
		final double z = vector.getY() * sin + vector.getZ() * cos;

		vector.setY(y).setZ(z);
	}

	private void rotateAroundAxisY(Vector vector, double angle) {
		angle = -angle;
		angle = Math.toRadians(angle);

		final double cos = Math.cos(angle);
		final double sin = Math.sin(angle);
		final double x = vector.getX() * cos + vector.getZ() * sin;
		final double z = vector.getX() * -sin + vector.getZ() * cos;

		vector.setX(x).setZ(z);
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
