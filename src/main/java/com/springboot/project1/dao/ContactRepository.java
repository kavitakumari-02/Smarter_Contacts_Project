package com.springboot.project1.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.project1.model.Contact;
import com.springboot.project1.model.User;

public interface ContactRepository extends JpaRepository<Contact, Integer>{
	public Page<Contact> findByUserId(int UserId, Pageable  pageable);
  @Modifying
  @Transactional
  @Query("delete from Contact c where c.id= :id and c.user.id= :userId")
	public void deleteByUserContact(int id,int userId);
}
