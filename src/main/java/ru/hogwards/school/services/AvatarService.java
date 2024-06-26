package ru.hogwards.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwards.school.model.Avatar;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repositories.AvatarRepository;
import ru.hogwards.school.repositories.StudentRepository;


import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional


public class AvatarService {
    @Value("${path.to.avatar.folder}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public void uploadAvatar(Long studentID, MultipartFile avatarFile) throws IOException {

        logger.debug("Trying to upload image!");
        Student student = studentRepository.getById(studentID);

        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentID);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());


        avatarRepository.save(avatar);
        logger.debug("Upload imagine done!");

    }

    private byte[] generationDataForDb (Path filepath) throws IOException{
        logger.debug("Running some understandable method");
        try (
                InputStream is = Files.newInputStream(filepath);
                BufferedInputStream bis = new BufferedInputStream(is,1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight()/(image.getWidth()/100);
            BufferedImage preview = new BufferedImage(100,height,image.getType());

            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image,0,0,100,height,null);
            graphics2D.dispose();

            ImageIO.write(preview,getExtensions(filepath.getFileName().toString()),baos);
            return baos.toByteArray();
        }

    }



    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long studentId) {
        logger.debug("Finding avatar for student by id");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());

    }

    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
