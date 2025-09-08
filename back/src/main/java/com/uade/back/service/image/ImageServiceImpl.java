package com.uade.back.service.image;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.uade.back.dto.image.ImageIdRequest;
import com.uade.back.dto.image.ImageResponse;
import com.uade.back.dto.image.ImageUploadRequest;
import com.uade.back.entity.Image;
import com.uade.back.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public ImageResponse upload(MultipartFile file, ImageUploadRequest meta) {
        try {
            Blob blob = new SerialBlob(file.getBytes());
            Image image = Image.builder()
                    .image(blob)
                    .build();
            Image savedImage = imageRepository.save(image);
            return new ImageResponse(savedImage.getImgId());
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Resource download(ImageIdRequest request) {
        Image image = imageRepository.findById(request.id().intValue())
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + request.id()));
        try {
            byte[] bytes = image.getImage().getBytes(1, (int) image.getImage().length());
            
            return new ByteArrayResource(bytes);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to download image", e);
        }
    }

    @Override
    @Transactional
    public void delete(ImageIdRequest request) {
        if (!imageRepository.existsById(request.id().intValue())) {
            throw new RuntimeException("Image not found with id: " + request.id());
        }
        imageRepository.deleteById(request.id().intValue());
    }
}
