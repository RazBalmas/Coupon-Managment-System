package app.core.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;
import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

	@Value("${file.upload.dir}")
	private String downloadDir;
	private Path fileStoragePath;
	
	@PostConstruct
	public void init() {
		this.fileStoragePath = Paths.get(downloadDir, downloadDir).toAbsolutePath().normalize();
		try {
			Files.createDirectories(fileStoragePath);
		} catch (IOException e) {
			throw new RuntimeException("Could'nt createee the pictures upload directory", e);
		}
	}
	
	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if(fileName.contains("..")) {
			throw new RuntimeException("File has invalid path : " +fileName);
			
		}
		Path targetLocation =  this.fileStoragePath.resolve(fileName);
		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (IOException e) {
		
			throw new RuntimeException("Storing file : " + fileName + " has failed", e);
		}
	}
}
