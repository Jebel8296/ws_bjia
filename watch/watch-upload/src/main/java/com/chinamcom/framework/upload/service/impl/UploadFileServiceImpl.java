package com.chinamcom.framework.upload.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.upload.dao.UploadFileMapper;
import com.chinamcom.framework.upload.model.UploadFile;
import com.chinamcom.framework.upload.service.UploadFileService;

@Service
public class UploadFileServiceImpl implements UploadFileService {

	@Autowired
	private UploadFileMapper uploadFileMapper;

	@Override
	public int insert(UploadFile uploadFile) {
		return uploadFileMapper.insert(uploadFile);
	}

	@Override
	public int update(UploadFile uploadFile) {
		return uploadFileMapper.update(uploadFile);
	}

	@Override
	public List<UploadFile> getUploadFile(Map<String, Object> map) {
		return uploadFileMapper.getUploadFile(map);
	}

}
