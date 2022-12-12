package com.blog.main.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blog.main.model.User;
@Repository
public interface UserDao extends JpaRepository<User, Long>{
	
	// jpa query laguage
	// OR JPQL
	@Query("Select u from User u where u.emailId=:email")
	public User getUserByUsername(@Param("email") String eamil);

	public User findByEmailId(String name);

	// native query
	@Query(value = "select u from tbl_user u where u.is_online= true", nativeQuery = true)
	public User  getUserisOnline(String email);
	@Query("select u from User u inner join Contact c on c.emailId = u.emailId where c.user.uId=?1 and u.isOnline=true"
			)
	public List<User> getListOfOnlineUser(int userId);
}
