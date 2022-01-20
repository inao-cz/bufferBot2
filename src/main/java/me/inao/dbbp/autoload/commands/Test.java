package me.inao.dbbp.autoload.commands;

import me.inao.dbbp.lentils.TestStatefulLentil;
import me.inao.dbbp.lentils.TestStatelessLentil;
import me.inao.dbbp.processing.annotations.Autoload;
import me.inao.dbbp.processing.annotations.Inject;
import me.inao.dbbp.enums.AutoloadType;
import me.inao.dbbp.interfaces.ICommand;
import org.javacord.api.entity.message.Message;

@Autoload(type = AutoloadType.COMMAND)
public class Test implements ICommand {

    @Inject
    private TestStatelessLentil statelessLentil;

    @Inject
    private TestStatefulLentil statefulLentil;

    @Inject(function = "getMessage")
    private Message message;

    @Override
    public void process() {
        message.getChannel().sendMessage("Stateless: " + statelessLentil.getHelloWorld() + "\n" + "Stateful: " + statefulLentil.getHelloWorld());
    }
}
