package com.chinamcom.framework.upload.service;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.upload.model.UploadFile;

public interface UploadFileService {

	public int insert(UploadFile uploadFile);

	public int update(UploadFile uploadFile);
	
	public List<UploadFile> getUploadFile(Map<String, Object> map);
}
