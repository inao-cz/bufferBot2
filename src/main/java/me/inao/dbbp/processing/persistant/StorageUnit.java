package me.inao.dbbp.processing.persistant;

import lombok.Getter;
import lombok.Setter;
import me.inao.dbbp.Config;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.dbbp.processing.lentil.StatefulLentilContainer;
import me.inao.dbbp.processing.perms.PermissionCheck;
import org.javacord.api.DiscordApi;

import java.util.HashMap;

@Getter
@Setter
public class StorageUnit {
    private Config config;
    private HashMap<String, Class<? extends ICommand>> commandOverviewMap;
    private PermissionCheck permissionCheck = new PermissionCheck(this);
    private DiscordApi api;
    private HashMap<String, StatefulLentilContainer> statefulLentilsSoup = new HashMap<>();
}
