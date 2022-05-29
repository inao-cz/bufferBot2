package me.inao.dbbp.perms;

import lombok.RequiredArgsConstructor;
import me.inao.dbbp.annotations.Permission;
import me.inao.dbbp.interfaces.ICommand;
import me.inao.dbbp.persistant.StorageUnit;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

@RequiredArgsConstructor
public class PermissionCheck {
    private final StorageUnit storageUnit;

    public boolean hasPermission(Message message, Class<? extends ICommand> command){
        return hasPermission(message.getServer().get(), message.getAuthor().asUser().get(), command.getSimpleName());
    }
    public boolean checkPermission(Message message, Class<? extends ICommand> command){
        return hasPermission(message, command);
    }

    private boolean hasPermission(Server server, User user, String command){
        Permission permission = storageUnit.getCommandOverviewMap().get(command).getDeclaredAnnotation(Permission.class);
        int configPerms = storageUnit.getConfig().getCommandPerms(command);
        if(configPerms == -1 && permission == null){
            return true;
        }
        else if(configPerms > 0){
            return server.hasPermission(user, Permissions.fromBitmask(configPerms).getAllowedPermission().iterator().next());
        }else if(configPerms == 0){
            return true;
        }
        if(permission.PermissionGroup().length() > 0){
            for(Role role : server.getRoles(user)){
                if(role.getName().equalsIgnoreCase(permission.PermissionGroup())){
                    return true;
                }
            }
        }
        return server.hasPermission(user, Permissions.fromBitmask(permission.PermissionMask()).getAllowedPermission().iterator().next());
    }
}
