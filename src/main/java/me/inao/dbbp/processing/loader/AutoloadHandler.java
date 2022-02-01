package me.inao.dbbp.processing.loader;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.processing.persistant.StorageUnit;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

@RequiredArgsConstructor
public class AutoloadHandler {
    private final StorageUnit unit;

    public void loadListeners(DiscordApiBuilder builder, String mainPackage){
        new ListenerAutoloader(this.unit).load(builder, mainPackage).run();
    }

    public void loadCommands(String mainPackage){
        new Thread(new CommandAutoloader(unit, mainPackage).load()).start();
    }

    public void fixJavacordMessInListeners(DiscordApi api){
        new ListenerAutoloader(this.unit).fixJavacordMessInListeners(api);
    }
}
