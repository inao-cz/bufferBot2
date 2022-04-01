package me.inao.discordbot.autoload.listeners;

import me.inao.dbbp.Config;
import me.inao.dbbp.annotations.Autoload;
import me.inao.dbbp.annotations.Inject;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.discordbot.lentils.CaptchaLentil;
import me.inao.discordbot.lentils.LoggerLentil;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

@Autoload(type = AutoloadType.LISTENER)
public class OnUserJoinListener implements ServerMemberJoinListener {
    @Inject
    private LoggerLentil logger;

    @Inject
    private Config config;

    @Inject
    private CaptchaLentil captcha;

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent serverMemberJoinEvent) {

    }

    private ServerTextChannel createChannel(User user, Server server) {
        return server.createTextChannelBuilder()
                .setName("captcha-" + user.getIdAsString())
                .setTopic("Captcha channel for user")
                .setCategory(server.getChannelCategoriesByName("captcha").get(0))
                .addPermissionOverwrite(user, new PermissionsBuilder().setDenied(PermissionType.SEND_MESSAGES).build())
                .addPermissionOverwrite(user, new PermissionsBuilder().setAllowed(PermissionType.READ_MESSAGES, PermissionType.ADD_REACTIONS).build())
                .addPermissionOverwrite(server.getRolesByName("Captcha").get(0), new PermissionsBuilder().setDenied(PermissionType.READ_MESSAGES).build())
                .addPermissionOverwrite(server.getRolesByName("@everyone").get(0), new PermissionsBuilder().setDenied(PermissionType.READ_MESSAGES).build())
                .create()
                .join();
    }
}
