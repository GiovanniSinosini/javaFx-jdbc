package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao(); // create and inject dependency with database
	
	
	public List<Department> findAll(){
		return dao.findAll();
	}
}
