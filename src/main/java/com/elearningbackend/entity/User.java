package com.elearningbackend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", catalog = "e_learning")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String passwordDigest;
	private String activationDigest;
	private int activated;
	private Timestamp activatedAt;
	private String rememberDigest;
	private String resetDigest;
	private Timestamp resetSentAt;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private String displayName;
	private String email;
	private String phone;
	private String address;
	private String avatar;
	private String role;
	private Set<Lession> lessions = new HashSet<Lession>(0);

	public User() {
	}

	public User(String username, String passwordDigest) {
		this.username = username;
		this.passwordDigest = passwordDigest;
	}

	public User(String username, String passwordDigest, Timestamp createdAt, Timestamp updatedAt, String email) {
		this.username = username;
		this.passwordDigest = passwordDigest;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.email = email;
	}

	public User(String username, String passwordDigest, String activationDigest, int activated, Timestamp activatedAt,
			String rememberDigest, String resetDigest, Timestamp resetSentAt, Timestamp createdAt, Timestamp updatedAt,
			String displayName, String email, String phone, String address, String avatar, String role,
			Set<Lession> lessions) {
		this.username = username;
		this.passwordDigest = passwordDigest;
		this.activationDigest = activationDigest;
		this.activated = activated;
		this.activatedAt = activatedAt;
		this.rememberDigest = rememberDigest;
		this.resetDigest = resetDigest;
		this.resetSentAt = resetSentAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.displayName = displayName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.avatar = avatar;
		this.role = role;
		this.lessions = lessions;
	}

	@Id
	@Column(name = "username", unique = true, nullable = false, length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password_digest", nullable = false, length = 200)
	public String getPasswordDigest() {
		return this.passwordDigest;
	}

	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}

	@Column(name = "activation_digest", length = 200)
	public String getActivationDigest() {
		return this.activationDigest;
	}

	public void setActivationDigest(String activationDigest) {
		this.activationDigest = activationDigest;
	}

	@Column(name = "activated")
	public int getActivated() {
		return this.activated;
	}

	public void setActivated(int activated) {
		this.activated = activated;
	}

	@Column(name = "activated_at")
	public Timestamp getActivatedAt() {
		return this.activatedAt;
	}

	public void setActivatedAt(Timestamp activatedAt) {
		this.activatedAt = activatedAt;
	}

	@Column(name = "remember_digest", length = 200)
	public String getRememberDigest() {
		return this.rememberDigest;
	}

	public void setRememberDigest(String rememberDigest) {
		this.rememberDigest = rememberDigest;
	}

	@Column(name = "reset_digest", length = 200)
	public String getResetDigest() {
		return this.resetDigest;
	}

	public void setResetDigest(String resetDigest) {
		this.resetDigest = resetDigest;
	}

	@Column(name = "reset_sent_at")
	public Timestamp getResetSentAt() {
		return this.resetSentAt;
	}

	public void setResetSentAt(Timestamp resetSentAt) {
		this.resetSentAt = resetSentAt;
	}

	@Column(name = "created_at", nullable = false)
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "updated_at", nullable = false)
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "display_name", length = 100)
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Column(name = "email", nullable = false, length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "avatar")
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Column(name = "role", length = 50)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public Set<Lession> getLessions() {
		return this.lessions;
	}

	public void setLessions(Set<Lession> lessions) {
		this.lessions = lessions;
	}
}
