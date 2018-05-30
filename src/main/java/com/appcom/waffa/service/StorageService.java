package com.appcom.waffa.service;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;


public interface StorageService {

	void init();

	void store(MultipartFile file);

	// Stream<Path> loadAll();
	//
	 Path load(String filename);
	//
	// Resource loadAsResource(String filename);

	void deleteAll();

}
