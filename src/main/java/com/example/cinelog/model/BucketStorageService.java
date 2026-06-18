package com.example.cinelog.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BucketStorageService {

    @Value("${storage.bucket.path:./bucket}")
    private String bucketPath;

    @Value("${storage.bucket.public-url-prefix:/bucket}")
    private String publicUrlPrefix;

    public StorageUploadResult uploadImagem(MultipartFile file) throws IOException {
        Path destinoBucket = Paths.get(bucketPath);
        if (!Files.exists(destinoBucket)) {
            Files.createDirectories(destinoBucket);
        }

        String nomeOriginal = StringUtils.cleanPath(Objects.requireNonNullElse(file.getOriginalFilename(), "imagem"));
        String nomeSeguro = Paths.get(nomeOriginal).getFileName().toString().replace(" ", "_");
        String chaveArquivo = UUID.randomUUID() + "_" + nomeSeguro;

        Path destino = destinoBucket.resolve(chaveArquivo);
        Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        return new StorageUploadResult(chaveArquivo, publicUrlPrefix + "/" + chaveArquivo);
    }

    public Resource carregar(String chaveArquivo) {
        Path caminho = Paths.get(bucketPath).resolve(chaveSegura(chaveArquivo));
        return new FileSystemResource(caminho);
    }

    public String contentType(String chaveArquivo) throws IOException {
        Path caminho = Paths.get(bucketPath).resolve(chaveSegura(chaveArquivo));
        String tipo = Files.probeContentType(caminho);
        return tipo == null ? "application/octet-stream" : tipo;
    }

    public void deletarImagem(String chaveArquivo) throws IOException {
        if (chaveArquivo == null || chaveArquivo.isBlank()) {
            return;
        }
        Path caminho = Paths.get(bucketPath).resolve(chaveSegura(chaveArquivo));
        Files.deleteIfExists(caminho);
    }

    private String chaveSegura(String chaveArquivo) {
        return Paths.get(chaveArquivo).getFileName().toString();
    }
}
