package com.mj.webmarket.aws;

//import com.amazonaws.services.s3.AmazonS3Client;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.product.ProductImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j

public class S3Uploader {

//    private final AmazonS3Client amazonS3Client;

    public String bucket;

    public String originalFileName = "basic";

    public ArrayList<ProductImage> uploadList(List<MultipartFile> multipartFiles, String dir, Product product) throws IOException {
        ArrayList<ProductImage> result = new ArrayList<>();
        for(MultipartFile file : multipartFiles){
            String uploadURL = upload(file, dir);
            result.add(ProductImage.builder().product(product).originalFileName(originalFileName)
                    .serverFileName(uploadURL).filePath(dir+"/").build());
        }
        return result;
    }

    /**
     * return server file name
     * @param multipartFile
     * @param dirName
     * @return
     * @throws IOException
     */
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException(
                        "error: MultipartFile -> File convert fail"
                ));
//        originalFileName = UUID.randomUUID() + uploadFile.getName(); // for aws
        originalFileName = uploadFile.getName(); // for local
        String fileName = dirName + "/" + originalFileName;
        return fileName;
    }


    /**
     * multipartfile -> file
     * @param file
     * @return
     * @throws IOException
     */
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File("/Users/a60156077/Documents/gitProject/Numble_Ecommerce/webmarket/src/main/resources/static/images/" + file.getOriginalFilename());
        if (convertFile.createNewFile()){
            try(FileOutputStream fs = new FileOutputStream(convertFile)){
                fs.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private void deleteLocalFile(File file){
        if(file.delete()){
            log.info("Delete Success");
            return;
        }
        log.info("Delete Failed");
    }


}
