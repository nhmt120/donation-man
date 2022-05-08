package tdtu.spring.models;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

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

	@ColumnDefault("1000000")
	private double targetFund = 1000000;

	@ColumnDefault("0")
	private double currentFund = 0;

	public Project() {
		super();
	}

	public Project(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Project(String name, String description, double targetFund, double currentFund) {
		super();
		this.name = name;
		this.description = description;
		this.targetFund = targetFund;
		this.currentFund = currentFund;
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

	public double getTargetFund() {
		return targetFund;
	}

	public void setTargetFund(double targetFund) {
		this.targetFund = targetFund;
	}

	public double getCurrentFund() {
		return currentFund;
	}

	public void setCurrentFund(double currentFund) {
		this.currentFund = currentFund;
	}

}
