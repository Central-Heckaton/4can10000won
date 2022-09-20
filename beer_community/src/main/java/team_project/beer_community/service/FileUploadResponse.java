package team_project.beer_community.service;

import lombok.Data;

@Data
public class FileUploadResponse {

    String fileName;
    String url;

    public FileUploadResponse(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }
}
