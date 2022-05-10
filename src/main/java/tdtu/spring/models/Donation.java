package tdtu.spring.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Donation")
public class Donation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)//EAGER)
	@JoinColumn(name = "project_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) //insertable = false, 
	@JsonIgnoreProperties("donationList")
	private Project project;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)//EAGER)
	@JoinColumn(name = "account_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) //insertable = false, 
//	@JsonIgnoreProperties("donationList")
	private Account account;

	@Column(nullable = false)
	private int amount;

	public Donation() {
		super();
	}

	public Donation(int amount) {
		super();
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Donation [id=" + id + ", project_id=" + project.getId() + ", account_id=" + account.getId() + ", amount=" + amount + "]";
	}

}
