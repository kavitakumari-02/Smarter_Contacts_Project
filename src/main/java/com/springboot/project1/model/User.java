package com.springboot.project1.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name="Id")
private int id;

@Column(name="Name")
@NotBlank(message="Name can't be empty!!")
private String name;

@Column(name="Email")
@NotBlank(message="Email can't be empty!!")
@Email(message="Please enter valid Email")
private String email;

@Column(name="Password")
@NotBlank(message="Password can't be empty!!")

private String password;

@Column(name="About")
private String about;

@Column(name="Role")
private String role;

@Column(name="Is_Enable")
private boolean is_Enable;

@Column(name="Image")
private String image;

@AssertTrue(message = "Please accept terms and conditions")
private boolean agree;
@Transient
private String oldPassword;

@Transient
private String newPassword;

@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
private List<Contact>Contacts=new ArrayList<>();

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getAbout() {
	return about;
}
public void setAbout(String about) {
	this.about = about;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public boolean isIs_Enable() {
	return is_Enable;
}
public void setIs_Enable(boolean is_Enable) {
	this.is_Enable = is_Enable;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}

public boolean isAgree() {
	return agree;
}
public void setAgree(boolean agree) {
	this.agree = agree;
}

public List<Contact> getContacts() {
	return Contacts;
}
public void setContacts(List<Contact> contacts) {
	Contacts = contacts;
}
public void setOldPassword(String oldPassword) {
	this.oldPassword=oldPassword;
}
public String getOldPassword() {
	return oldPassword;
}
public void setNewPassword(String newPassword) {
	this.newPassword=newPassword;
}
public String getNewPassword() {
	return newPassword;
}
/*@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", about=" + about
			+ ", role=" + role + ", is_Enable=" + is_Enable + ", image=" + image + ", agree=" + agree + ", Contacts="
			+ Contacts + "]";
}*/



}
