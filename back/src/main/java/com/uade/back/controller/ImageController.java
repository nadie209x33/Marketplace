package com.uade.back.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.uade.back.dto.image.*;
import com.uade.back.service.image.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService service;

  // Subir imagen vía multipart + DTO de metadatos
  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ImageResponse> upload(
      @RequestPart("file") MultipartFile file,
      @RequestPart("meta") ImageUploadRequest meta) {
    return ResponseEntity.ok(service.upload(file, meta));
  }

  // Descargar imagen vía DTO (sin path variables)
  @PostMapping("/download")
  public ResponseEntity<Resource> download(@RequestBody ImageIdRequest request) {
    Resource res = service.download(request);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + res.getFilename())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(res);
  }

  // (Opcional) Eliminar imagen vía DTO
  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestBody ImageIdRequest request) {
    service.delete(request);
    return ResponseEntity.noContent().build();
  }
}
