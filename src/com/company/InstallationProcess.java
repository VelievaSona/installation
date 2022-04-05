package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InstallationProcess {
    private StringBuilder sbLog;
    private String basePath;

    public InstallationProcess(String basePath) {
        sbLog = new StringBuilder();
        this.basePath = basePath;
    }

    public void execute() throws IOException {
        createDir(basePath);
        createDir(basePath + "\\savegames");
        createSrcDir();
        createResDir();
        createTempDir();

        saveLog(basePath + "\\temp\\temp.txt");
    }

    private void createSrcDir() throws IOException {
        createDir(basePath + "\\src");
        createDir(basePath + "\\src\\test");
        createMainDir();
    }

    private void createMainDir() throws IOException {
        createDir(basePath + "\\src\\main");
        createDir(basePath + "\\src\\main\\Main.java");
        createFile(basePath + "\\src\\main\\Utils.java");
    }

    private void createResDir() {
        createDir(basePath + "\\res");
        createDir(basePath + "\\drawables");
        createDir(basePath + "\\vectors");
        createDir(basePath + "\\icons");
    }

    private void createTempDir() throws IOException {
        createDir(basePath + "\\temp");
        createFile(basePath + "\\temp\\temp.txt");
    }

    private void createDir(String dirPath) {
        File dir = new File(dirPath);
        boolean created = dir.mkdir();

        if (created) {
            sbLog.append("Директория " + dirPath + " успешно создана\n");
        } else {
            sbLog.append("Ошибка создания директории " + dirPath + "\n");
        }
    }

    private void createFile(String filePath) throws IOException {
        File file = new File(filePath);
        boolean created = file.createNewFile();

        if (created) {
            sbLog.append("Файл " + filePath + " успешно создан\n");
        } else {
            sbLog.append("Ошибка создания файла " + filePath + "\n");
        }
    }

    private void saveLog(String logPath) throws IOException {
        try (BufferedWriter stream = new BufferedWriter(new FileWriter(logPath))) {
            String log = sbLog.toString();
            stream.write(log);
        }
    }
}
