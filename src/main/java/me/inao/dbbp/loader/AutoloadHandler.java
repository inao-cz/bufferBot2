package me.inao.dbbp.loader;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.persistant.StorageUnit;
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

    public void loadTasks(String mainPackage){
        new Thread(new TaskAutoloader(this.unit, mainPackage).load()).start();
    }

    public void loadArguments(String mainPackage){
        new Thread(new ArgumentsHandler(this.unit, mainPackage).load()).start();
    }

    public void fixJavacordMessInListeners(DiscordApi api){
        new ListenerAutoloader(this.unit).fixJavacordMessInListeners(api);
    }
}
