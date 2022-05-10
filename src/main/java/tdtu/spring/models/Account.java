package tdtu.spring.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(name = "role", columnDefinition = "varchar(255) default 'user'")
	private String role = "user";

	@ColumnDefault("0")
	private int balance = 0;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL)
//	@JsonIgnoreProperties("donation")
	private List<Donation> donationList = new ArrayList<>();

	public void addDonation(Donation donation) {
    this.donationList.add(donation);
	}
	
	public Account() {
		super();
	}

	public Account(String name, String username, String password, String role) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Account(String name, String username, String password) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public List<Donation> getDonationList() {
		return donationList;
	}

	public void setDonationList(List<Donation> donationList) {
		this.donationList = donationList;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", role="
				+ role + ", balance=" + balance + ", donationList=" + donationList + "]";
	}

}
