package pl.londek.lobbyutils.utils;

public class LogUtils {
    public static String fixColor(String raw) {
        return raw.replaceAll("&","§");
    }
}
