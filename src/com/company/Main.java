package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        String basePath = "D:\\Games";
        InstallationProcess installation = new InstallationProcess(basePath);
        installation.execute();

        GameProgress gameProgress = new GameProgress(100, 50, 15, 10);
        GameProgress gameProgress1 = new GameProgress(150, 75, 20, 15);
        GameProgress gameProgress2 = new GameProgress(230, 90, 25, 20);

        List<String> saveGamesFilePaths = new ArrayList<>();

        File saveGameFile = generateSaveGameFile(basePath);
        saveGamesFilePaths.add(saveGameFile.getAbsolutePath());
        saveGame(saveGameFile.getAbsolutePath(), gameProgress);

        saveGameFile = generateSaveGameFile(basePath);
        saveGamesFilePaths.add(saveGameFile.getAbsolutePath());
        saveGame(saveGameFile.getAbsolutePath(), gameProgress1);

        saveGameFile = generateSaveGameFile(basePath);
        saveGamesFilePaths.add(saveGameFile.getAbsolutePath());
        saveGame(saveGameFile.getAbsolutePath(), gameProgress2);

        String[] saveGameFilePathsArr = new String[saveGamesFilePaths.size()];
        zipFiles(basePath + "\\savegames\\zip.zip", saveGamesFilePaths.toArray(saveGameFilePathsArr));
    }

    private static void saveGame(String path, GameProgress progress) throws IOException {
       try (FileOutputStream fos = new FileOutputStream(path)){
           try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
               oos.writeObject(progress);
           }
       }
    }

    private static void zipFiles(String path, String[] saveGameFilePaths) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path))) {
            for (String saveGameFilePath : saveGameFilePaths) {
                File file = new File(saveGameFilePath);

                try (FileInputStream fis = new FileInputStream(saveGameFilePath)) {
                    zos.putNextEntry(new ZipEntry(file.getName()));

                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }

                    zos.closeEntry();
                }

                file.delete();
            }
        }
    }

    private static File generateSaveGameFile(String basePath) throws IOException {
        File saveGameFile = new File(basePath + "\\savegames\\game.txt");

        for (int i = 0; saveGameFile.exists(); i++) {
            saveGameFile = new File(basePath + "\\savegames\\game" + i + ".txt");
        }

        saveGameFile.createNewFile();
        return saveGameFile;
    }
}
