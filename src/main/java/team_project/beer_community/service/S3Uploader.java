package team_project.beer_community.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team_project.beer_community.domain.User;
import team_project.beer_community.repository.UserRepository;



@Service
@Slf4j
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public FileUploadResponse uploadFiles(Long userId, MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("Error: MultipartFile -> File로 전환이 실패했습니다."));
        return upload(userId, uploadFile, dirName);
    }


    @Transactional
    public FileUploadResponse upload(Long userId, File uploadFile, String filePath) {
        String fileName = filePath + "/" + userId + uploadFile.getName(); // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // S3로 업로드
        log.info("uploadImageUrl = " + uploadImageUrl);
        removeNewFile(uploadFile);

        //사용자의 프로필을 등록하는 것이기때문에, User 도메인에 setImageUrl을 해주는 코드.
        //이 부분은 그냥 업로드만 필요하다면 필요없는 부분이다.
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        user.setImageUrl(uploadImageUrl); // dirtyChecking으로 변경사항 DB반영

        //FileUploadResponse DTO로 반환해준다.
        return new FileUploadResponse(fileName, uploadImageUrl);
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
                CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile =  new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }


}