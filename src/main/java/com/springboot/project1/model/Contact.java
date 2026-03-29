package com.springboot.project1.model;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Contact {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
@NotEmpty(message="Name not be Empty")
@Column(name="FirstName")
private String firstName;
@Column(name="LastName")
@NotEmpty(message="LastName must be filled")
private String lastName;
@Column(name="Email")
@Size(min=3,max=50,message="Email must be filled!!")
@NotEmpty(message="Email must be filled")
private String email;
@Size(min=10,message="Use only 1-9 digits")
@NotEmpty(message="Phone Number must be filled")
@Column(name="Phone_Number")
private String phone;
@Column(name="Profession")
@NotBlank(message="must be filled")
private String profession;
@Column(name="Description",length=1000)
@Size(min=5,max=1000,message="must be!!")
private String description;
private String image;

@ManyToOne
@JoinColumn(name="userId")
private User user;


public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getProfession() {
	return profession;
}
public void setProfession(String profession) {
	this.profession = profession;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
/*
@Override
public String toString() {
	return "Contact [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone="
			+ phone + ", profession=" + profession + ", description=" + description + ", user=" + user + ", image="
			+ image + "]";
}*/

}
