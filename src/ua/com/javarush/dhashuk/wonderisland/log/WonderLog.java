package ua.com.javarush.dhashuk.wonderisland.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WonderLog {
    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String DIRECTORY = "logs";
    private static final String LOGFILE  = "logfile.log";

    private static WonderLog INSTANCE;

    private DateTimeFormatter isoLocalDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private Path realLogPath;

    private WonderLog(){
        createRealLogPath();
    }

    public static WonderLog getInstance(){
        if(INSTANCE == null){
            INSTANCE = new WonderLog();
        }
        return INSTANCE;
    }

    public void info(String message){
        writeLog("INFO: " + message);
    }

    public void debug(String message){
        writeLog("DEBUG: " + message);
    }

    public void error(String message){
        writeLog("ERROR: " + message.toUpperCase());
    }

    private void createRealLogPath(){
        Path outputDirectory = Path.of(DIRECTORY);
        Path outputFilePath = Path.of(DIRECTORY+SEPARATOR+LOGFILE);

        try {
            if(!Files.exists(outputDirectory)){
                Files.createDirectory(outputDirectory);
            }
            if(!Files.exists(outputFilePath)){
                Files.createFile(outputFilePath);
            }
            realLogPath = outputFilePath;
        }catch (IOException ex) {
            throw new LoggerInitializationException("File or directory cannot be created " + outputFilePath,ex);
        }
    }

    private void writeLog(String text){
        synchronized (INSTANCE){
            try {
                String result_string = "[ " + LocalDateTime.now().format(isoLocalDateTime)
                        +" ] " + text + "\n";
                Files.writeString(realLogPath, result_string , StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new LoggerInitializationException("Something happened - Logger does not work: "
                        + e.getMessage(), e);
            }
        }
    }
}
