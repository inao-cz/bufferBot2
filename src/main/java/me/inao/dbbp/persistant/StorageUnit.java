package me.inao.dbbp.persistant;

import lombok.Getter;
import lombok.Setter;
import me.inao.dbbp.Config;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.dbbp.lentil.StatefulLentilContainer;
import me.inao.dbbp.perms.PermissionCheck;
import org.javacord.api.DiscordApi;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
@Setter
public class StorageUnit {
    private Config config;
    private HashMap<String, Class<? extends ICommand>> commandOverviewMap;
    private PermissionCheck permissionCheck = new PermissionCheck(this);
    private DiscordApi api;
    private HashMap<String, StatefulLentilContainer> statefulLentilsSoup = new HashMap<>();
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() / 2);
}
