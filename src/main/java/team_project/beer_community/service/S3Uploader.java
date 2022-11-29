package team_project.beer_community.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team_project.beer_community.domain.User;
import team_project.beer_community.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileUploadResponse upload(Long userId, MultipartFile multipartFile, String dirName) throws IOException {

        System.out.println("S3Uploader.1upload() / before convert()");
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
        System.out.println("S3Uploader.1upload() / after convert()");
        return upload(userId, uploadFile, dirName);
    }


    private FileUploadResponse upload(Long userId, File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        System.out.println("2upload() / fileName = " + fileName); // -> profile/고경환_사진확대.PNG
        String uploadImageUrl = putS3(uploadFile, fileName);
        System.out.println("2upload() / uploadImageUrl = " + uploadImageUrl);
        removeNewFile(uploadFile);

//사용자의 프로필을 등록하는 것이기때문에, User 도메인에 setImageUrl을 해주는 코드.
//이 부분은 그냥 업로드만 필요하다면 필요없는 부분이다.
        User user = userRepository.findById(userId).get();
        user.setImageUrl(uploadImageUrl);

//FileUploadResponse DTO로 반환해준다.
        System.out.println("2upload() / user.getImageUrl() = " + user.getImageUrl());
        return new FileUploadResponse(fileName, uploadImageUrl);
        //return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
                CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        System.out.println("----S3Uploader.convert----");
        System.out.println("1. S3Uploader.convert");
        if(convertFile.createNewFile()) {
            System.out.println("2. S3Uploader.convert");
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                System.out.println("3. S3Uploader.convert");
                fos.write(file.getBytes());
                System.out.println("4. S3Uploader.convert");
            }
            System.out.println("5. S3Uploader.convert");
            return Optional.of(convertFile);
        }
        System.out.println("6. S3Uploader.convert");
        return Optional.empty();
    }


}