package me.inao.discordbot.lentils;

import me.inao.dbbp.processing.annotations.Inject;
import me.inao.dbbp.processing.annotations.Stateless;
import me.inao.dbbp.processing.persistant.StorageUnit;
import org.apache.logging.log4j.Level;

import java.io.File;

@Stateless
public class TranslationUtil {

    @Inject
    private LoggerLentil logger;

    @Inject
    private StorageUnit storageUnit;

    public TranslationUtil(){
        doesFilesystemExist();
    }

    public String getTranslatedMessage(String lang, String key){
        return null;
    }

    private void doesFilesystemExist(){
        File dir = new File("./translations/");
        if(dir.exists() && dir.isDirectory()) return;
        if(dir.mkdir()){
            logger.log(this.getClass(), "Created new translations directory.", Level.INFO);
            return;
        }
        logger.log(this.getClass(), "Cannot create new translations directory", Level.ERROR);
    }
}
