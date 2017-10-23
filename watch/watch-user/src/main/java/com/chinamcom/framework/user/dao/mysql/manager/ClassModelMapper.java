package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.ClassModel;

public interface ClassModelMapper {

	public List<ClassModel> getClassModelList(Map<String,Object> map);
	
	public boolean batchInsert(List<ClassModel> list);
	
	public boolean updateclassmodel(ClassModel classModel);
	
	public boolean insertClassModel(ClassModel classModel);
	
	public boolean classmodelopenorclose(Map<String,Object> map);
	
	public boolean updatepower(Map<String,Object> map);
	
	public ClassModel queryClassMode(Map<String,Object> map);
	
}
