package ru.geekbrains.spring.ishop.service;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageSaverService {
    //TODO Вынести в property файл?
    private static final String UPLOADED_FOLDER = "./images/";

    public String saveFile(MultipartFile file, String subDirName) {
        if (file.isEmpty()) {
            return "";
        }
        String fileName = Paths.get(subDirName, file.getOriginalFilename()).toString();
        //TODO заменить try-catch на собственное исключение FileOperationException
        try {
            Path path = Paths.get(UPLOADED_FOLDER, fileName);
            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    //FIXME Файл не удаляется физически при удалении Product
    //TODO заменить try-catch на собственное исключение FileOperationException
    public void deleteFile(String pathToFile) {
        try {
            Files.deleteIfExists(Paths.get(UPLOADED_FOLDER, pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MultipartFile getFile(String pathToFile) {
        String pathname = Paths.get(UPLOADED_FOLDER, pathToFile).toString();
        //TODO заменить try-catch на собственное исключение FileOperationException
        try {
            return new MockMultipartFile(pathToFile,
                    new FileInputStream(new File(pathname)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

