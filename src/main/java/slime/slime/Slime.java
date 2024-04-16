package slime.slime;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.ChunkRandom;

import static net.minecraft.server.command.CommandManager.literal;

public class Slime implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("slime").executes(context -> {
            ServerCommandSource source = context.getSource();
            ServerPlayerEntity player = source.getPlayer();
            int xPosition = player.getChunkPos().x;
            int zPosition = player.getChunkPos().z;
            long seed = source.getWorld().getSeed();

            boolean isSlimeChunk = ChunkRandom.getSlimeRandom(xPosition, zPosition, seed, 0x3ad8025fL).nextInt(10) == 0;
            if (isSlimeChunk) {
                source.sendFeedback(() -> Text.literal("Chunk " + xPosition + ", " + zPosition + " is a slime chunk."), false);
            } else {
                source.sendFeedback(() -> Text.literal("Chunk " + xPosition + ", " + zPosition + " is NOT a slime chunk."), false);
            }
            return 1;
        })));
    }
}
