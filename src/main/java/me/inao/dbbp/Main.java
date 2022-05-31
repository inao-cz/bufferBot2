package me.inao.dbbp;

import lombok.Getter;
import me.inao.dbbp.lentil.LentilHandler;
import me.inao.dbbp.loader.AutoloadHandler;
import me.inao.dbbp.persistant.StorageUnit;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.user.UserStatus;

@Getter
public class Main {

    private final StorageUnit storageUnit = new StorageUnit();

    public static void main(String[] args) {
        new Main().start();
    }

    public void start(){
        new Config().loadConfig(this);
        DiscordApiBuilder builder = new DiscordApiBuilder().setWaitForServersOnStartup(false).setToken(storageUnit.getConfig().getApikey());

        new LentilHandler(storageUnit).loadStatefulLentils("me.inao.discordbot.lentils");
        new AutoloadHandler(storageUnit).loadCommands("me.inao.discordbot.autoload.commands");
        new AutoloadHandler(storageUnit).loadListeners(builder, "me.inao.discordbot.autoload.listeners");
        new AutoloadHandler(storageUnit).loadTasks("me.inao.discordbot.autoload.tasks");
        new AutoloadHandler(storageUnit).loadArguments("me.inao.discordbot.autoload.arguments");

        DiscordApi api = builder.login().join();

        api.setAutomaticMessageCacheCleanupEnabled(true);

        storageUnit.setApi(api);
        new AutoloadHandler(storageUnit).fixJavacordMessInListeners(api);

        api.updateStatus(UserStatus.fromString(storageUnit.getConfig().getStatus().length() > 1 ? storageUnit.getConfig().getStatus() : "online" ));
    }
}
