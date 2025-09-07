package com.uade.back.service.image;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import com.uade.back.dto.image.*;

public interface ImageService {
  ImageResponse upload(MultipartFile file, ImageUploadRequest meta);
  Resource download(ImageIdRequest request);
  void delete(ImageIdRequest request);
}
