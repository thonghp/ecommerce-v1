package com.hpt.ecommercev1.utils;

import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.hpt.ecommercev1.utils.Constants.DEFAULT_APP_IMAGE_DIRECTORY;
import static com.hpt.ecommercev1.utils.Constants.DEFAULT_IMAGE_DIRECTORY;

public class FileUploadUtil {

    /**
     * Returns path of the uploaded file.
     *
     * @param request  The request object
     * @param partName The name of the part
     * @return The path of the uploaded file
     * @throws IOException      If the request for the POST could not be handled.
     * @throws ServletException If an input or output error is detected when the servlet handles the POST request.
     */
    private static Part getPartOfImage(HttpServletRequest request, String partName) throws IOException, ServletException {
        return request.getPart(partName); // Retrieves <input type="file" name="partName">
    }

    /**
     * Returns the file name of the uploaded file.
     *
     * @param request  The request object.
     * @param partName The name of the part.
     * @return The file name of the uploaded file.
     * @throws ServletException If the request for the POST could not be handled.
     * @throws IOException      If an input or output error is detected when the servlet handles the POST request.
     */
    public static String extractFileName(HttpServletRequest request, String partName) throws ServletException, IOException {
        Part part = getPartOfImage(request, partName);
        return part.getSubmittedFileName();
    }

    /**
     * Returns the path of server directory where the uploaded file will be saved.
     *
     * @param request             The request object.
     * @param nameDirectoryServer The name of server directory.
     * @return The path of server directory where the uploaded file will be saved.
     */
    private static String getDirectoryServerPath(HttpServletRequest request, String nameDirectoryServer) {
        String serverPath = request.getServletContext().getRealPath("");

        return serverPath + File.separator + DEFAULT_IMAGE_DIRECTORY + File.separator + nameDirectoryServer;
    }

    /**
     * Saves the uploaded file to the server directory.
     *
     * @param request             The request object.
     * @param nameDirectoryServer The name of server directory.
     * @param partName            The name of the part.
     * @param fileName            The file name of the uploaded file.
     * @throws ServletException If the request for the POST could not be handled.
     * @throws IOException      If an input or output error is detected when the servlet handles the POST request.
     */
    public static void saveFileOnServer(HttpServletRequest request, String nameDirectoryServer, String partName, String fileName) throws ServletException, IOException {
        String directoryServerPath = getDirectoryServerPath(request, nameDirectoryServer);

        File fileSaveDir = new File(directoryServerPath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        Part part = getPartOfImage(request, partName);
        part.write(directoryServerPath + File.separator + fileName);
    }

    /**
     * Saves the uploaded file to the application directory.
     *
     * @param request             The request object.
     * @param nameDirectoryServer The name of server directory.
     * @param fileName            The file name of the uploaded file.
     * @throws IOException If an input or output error is detected when the servlet handles the POST request.
     */
    public static void saveFileOnApp(HttpServletRequest request, String nameDirectoryServer, String fileName) throws IOException {
        String fileServerPath = getDirectoryServerPath(request, nameDirectoryServer) + File.separator + fileName;
        String pathDirectoryApp = DEFAULT_APP_IMAGE_DIRECTORY + nameDirectoryServer;

        File fileSaveDir = new File(pathDirectoryApp);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        File originFile = new File(fileServerPath);
        File destFile = new File(pathDirectoryApp + File.separator + originFile.getName());
        FileUtils.copyFile(originFile, destFile);
    }

    /**
     * Delete all files in the specified directory.
     *
     * @param directoryPath The directory path.
     */
    public static void cleanDir(String directoryPath) {
        Path dirPath = Paths.get(directoryPath);

        try {
            Files.list(dirPath).forEach(file -> {
                if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        System.out.println("Could not delete file: " + file);
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Could not list directory: " + dirPath);
        }
    }

    /**
     * Delete the specified directory.
     *
     * @param directoryPath The directory path.
     */
    public static void removeDir(String directoryPath) {
        cleanDir(directoryPath);

        try {
            Files.delete(Paths.get(directoryPath));
        } catch (IOException e) {
            System.out.println("Could not remove directory: " + directoryPath);
        }
    }
}