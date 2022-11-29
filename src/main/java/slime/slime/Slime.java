package slime.slime;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.literal;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import static net.minecraft.server.command.CommandManager.*;
import net.minecraft.text.Text;

import java.util.Random;

public class Slime implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("slime").executes(context -> {
                ServerCommandSource source = context.getSource();
                ServerPlayerEntity player = source.getPlayer();
                int xPosition = player.getChunkPos().x;
                int zPosition = player.getChunkPos().z;
                long seed = source.getWorld().getSeed();

                Random rnd = new Random(
                        seed +
                                (int) (xPosition * xPosition * 0x4c1906) +
                                (int) (xPosition * 0x5ac0db) +
                                (int) (zPosition * zPosition) * 0x4307a7L +
                                (int) (zPosition * 0x5f24f) ^ 0x3ad8025fL
                );

                boolean isSlimeChunk = (rnd.nextInt(10) == 0);
                if (isSlimeChunk) {
                    source.sendFeedback(Text.literal("Chunk " + xPosition + ", " + zPosition + " is a slime chunk."), false);
                } else {
                    source.sendFeedback(Text.literal("Chunk " + xPosition + ", " + zPosition + " is NOT a slime chunk."), false);
                }
                return 1;
            }));
        });
    }
}
