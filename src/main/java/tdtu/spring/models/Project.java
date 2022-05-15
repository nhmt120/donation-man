package tdtu.spring.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private int targetFund;

	@ColumnDefault("0")
	private int currentFund = 0;
	
	@ColumnDefault("0")
	private int donationNum = 0;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL)
//	@JsonIgnoreProperties("donation")
	private List<Donation> donationList = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)//EAGER)
	@JoinColumn(name = "account_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) //insertable = false, 
//	@JsonIgnoreProperties("donationList")
	private Account account;

	public void addDonation(Donation donation) {
    this.donationList.add(donation);
	}
	
	public Project() {
		super();
	}

	public Project(String name, String description, int targetFund) {
		super();
		this.name = name;
		this.description = description;
		this.targetFund = targetFund;
	}
	
	public Project(String name, String description, int targetFund, Account account) {
		super();
		this.name = name;
		this.description = description;
		this.targetFund = targetFund;
		this.account = account;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTargetFund() {
		return targetFund;
	}

	public void setTargetFund(int targetFund) {
		this.targetFund = targetFund;
	}

	public int getCurrentFund() {
		return currentFund;
	}

	public void setCurrentFund(int currentFund) {
		this.currentFund = currentFund;
	}

	public int getDonationNum() {
		return donationNum;
	}

	public void setDonationNum(int donationNum) {
		this.donationNum = donationNum;
	}

	public List<Donation> getDonationList() {
		return donationList;
	}

	public void setDonationList(List<Donation> donationList) {
		this.donationList = donationList;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", description=" + description + ", targetFund=" + targetFund
				+ ", currentFund=" + currentFund + "]";
	}
}
