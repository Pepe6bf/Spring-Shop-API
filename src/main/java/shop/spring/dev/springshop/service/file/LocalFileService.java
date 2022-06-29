package shop.spring.dev.springshop.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class LocalFileService {

    @Value("${file.item_img-storage-location}")
    private String itemImgStorageLocation;

    public String uploadFile(String originalFileName,
                             MultipartFile fileData) throws Exception {

        String storedFileName = getStoredFileName(originalFileName);
        String storedFullPath = getImgStoredFullPath(storedFileName);

        // 파일 업로드
        fileData.transferTo(new File(storedFullPath));

        return storedFileName;
    }

    private String getStoredFileName(String originalFileName) {
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        return uuid.toString() + "." + extension;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
            System.out.println("파일 삭제 완료!!!!");
        } else {
            System.out.println("파일 없어욤...");
        }
    }

    public String getImgStoredFullPath(String storedFileName) {
        return itemImgStorageLocation + "/" + storedFileName;
    }
}
