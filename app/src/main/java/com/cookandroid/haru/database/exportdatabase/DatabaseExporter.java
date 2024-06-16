package com.cookandroid.haru.database.exportdatabase;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class DatabaseExporter {

    public static void exportDatabase(Context context, String fileName) throws IOException {
        File dbFile = context.getDatabasePath(fileName+".db");
        File exportDir = new File(context.getExternalFilesDir(null), "backup");

        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File exportFile = new File(exportDir, "BACKUP_" + dbFile.getName());

        if (!exportFile.exists()) {
            exportFile.createNewFile();
        }

        copyFile(dbFile, exportFile);
    }

    private static void copyFile(File sourceFile, File destFile) throws IOException {
        InputStream inputStream = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            inputStream = Files.newInputStream(sourceFile.toPath());
        }
        OutputStream outputStream = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            outputStream = Files.newOutputStream(destFile.toPath());
        }

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
}