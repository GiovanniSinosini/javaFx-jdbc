package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	
	private SellerDao dao = DaoFactory.createSellerDao(); // create and inject dependency with database
	
	public List<Seller> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate (Seller obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Seller obj) {   // remove department from database
		dao.deleteById(obj.getId());
		
	}
}
