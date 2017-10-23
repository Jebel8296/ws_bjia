package com.chinamcom.framework.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.ClassModel;
import com.chinamcom.framework.user.dao.mysql.manager.ClassModelMapper;
import com.chinamcom.framework.user.service.ClassModelService;

@Service
public class ClassModelServiceImpl implements ClassModelService{

	@Autowired
	private ClassModelMapper classModelMapper;
	
	@Override
	public List<ClassModel> getClassModelList(Map<String, Object> map) {
		
		List<ClassModel> list = classModelMapper.getClassModelList(map);
		return list;
	}

	@Override
	public boolean batchInsert(List<ClassModel> list) {
		// TODO Auto-generated method stub
		boolean flag = classModelMapper.batchInsert(list);
		return flag;
	}

	@Override
	public boolean updateclassmodel(ClassModel classModel) {
		boolean flag = classModelMapper.updateclassmodel(classModel);
		return flag;
	}

	@Override
	public boolean classmodelopenorclose(Map<String, Object> map) {
		// TODO Auto-generated method stub
		boolean flag = classModelMapper.classmodelopenorclose(map);
		return flag;
	}

	@Override
	public boolean updatepower(Map<String, Object> map) {
		// TODO Auto-generated method stub
		boolean flag = classModelMapper.updatepower(map);
		return flag;
	}

	@Override
	public boolean insertClassModel(ClassModel classModel) {
		// TODO Auto-generated method stub
		boolean flag = classModelMapper.insertClassModel(classModel);
		return flag;
	}

	@Override
	public ClassModel queryClassMode(Map<String, Object> map) {
		// TODO Auto-generated method stub
		ClassModel cm = classModelMapper.queryClassMode(map);
		return cm;
	}

}
