package slime.slime;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.levelgen.WorldgenRandom;

import static net.minecraft.commands.Commands.literal;

public class Slime implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("slime").executes(context -> {
            CommandSourceStack source = context.getSource();
            ServerPlayer player = source.getPlayer();
            assert player != null;
            int xPosition = player.chunkPosition().x();
            int zPosition = player.chunkPosition().z();
            long seed = source.getLevel().getSeed();

            boolean isSlimeChunk = WorldgenRandom.seedSlimeChunk(xPosition, zPosition, seed, 0x3ad8025fL).nextInt(10) == 0;
            if (isSlimeChunk) {
                player.sendSystemMessage(Component.literal("Chunk " + xPosition + ", " + zPosition + " is a slime chunk."), false);
            } else {
                player.sendSystemMessage(Component.literal("Chunk " + xPosition + ", " + zPosition + " is NOT a slime chunk."), false);
            }
            return 1;
        })));
    }
}
