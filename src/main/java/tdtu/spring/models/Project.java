package tdtu.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "Project")
public class Project {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "target_fund")
	private int targetFund;

	@Column(name = "current_fund")
	private int currentFund;

	public Project() {
		super();
	}
	
	public Project(String name, String description, int target_fund, int current_fund) {
		super();
		this.name = name;
		this.description = description;
		this.targetFund = target_fund;
		this.currentFund = current_fund;
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
}
