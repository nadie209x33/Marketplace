package com.uade.back.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.back.dto.image.ImageIdRequest;
import com.uade.back.dto.image.ImageResponse;
import com.uade.back.dto.image.ImageUploadRequest;
import com.uade.back.service.image.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService service;
  private final ObjectMapper objectMapper;

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> upload(
      @RequestPart("file") MultipartFile file,
      @RequestPart("meta") String metaJson) {
        
    ImageUploadRequest meta;
    try {
      meta = objectMapper.readValue(metaJson, ImageUploadRequest.class);
    } catch (JsonProcessingException e) {
      return ResponseEntity.badRequest().body("Invalid JSON format for 'meta' part.");
    }
    ImageResponse imageResponse = service.upload(file, meta);
    return ResponseEntity.ok(imageResponse);
  }

  @GetMapping("/{id}")
    public ResponseEntity<Resource> getImageById(@PathVariable Integer id) {
        ImageIdRequest request = new ImageIdRequest(id);
        Resource resource = service.download(request);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

  @PostMapping("/download")
  public ResponseEntity<Resource> download(@RequestBody ImageIdRequest request) {
    Resource res = service.download(request);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + res.getFilename())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(res);
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestBody ImageIdRequest request) {
    service.delete(request);
    return ResponseEntity.noContent().build();
  }
}