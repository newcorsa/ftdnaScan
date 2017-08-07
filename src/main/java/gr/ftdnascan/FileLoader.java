package gr.ftdnascan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    public static List<Kit> loadKitsFromResources() {
        return loadKitsFromFolder(getResourcesFolder());
    }

    public static String getResourcesFolder() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // ClassLoader classLoader = getClass().getClassLoader();
        String folder = classLoader.getResource("html-source").getFile(); // src\main\resources\src
        return folder;
    }

    public static List<Kit> loadKitsFromFolder(String folder) {

        System.out.println("Scanning: " + folder + "...");

        List<Kit> kits = new ArrayList<Kit>();

        File workDir = new File(folder);
        File[] curFiles = workDir.listFiles();

        long startTime = System.currentTimeMillis();

        if (curFiles != null) {
            for (int i = 0; i < curFiles.length; i++) {
                if (curFiles[i].isFile()) {
                    System.out.println("Source: " + curFiles[i].getPath());

                    String filename = curFiles[i].getPath();

                    FtdnaPage ftdnaPage = new FtdnaPage(filename);
                    List<Kit> pageKits = ftdnaPage.Parse();

                    kits.addAll(pageKits);

                    System.out.println("Loaded " + pageKits.size() + " kits.");
                }
            }
        }

        System.out.println("Loaded " + kits.size() + " kits in total, time: " + (System.currentTimeMillis() - startTime));

        return kits;
    }
}
