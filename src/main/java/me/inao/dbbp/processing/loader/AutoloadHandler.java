package me.inao.dbbp.processing.loader;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.processing.persistant.StorageUnit;
import org.javacord.api.DiscordApiBuilder;

@RequiredArgsConstructor
public class AutoloadHandler {
    private final StorageUnit unit;

    public void loadListeners(DiscordApiBuilder builder, String mainPackage){
        new ListenerAutoloader(builder, this.unit, mainPackage).start();
    }

    public void loadCommands(String mainPackage){
        CommandAutoloader loader = new CommandAutoloader(unit, mainPackage);
        loader.start();
    }
}
