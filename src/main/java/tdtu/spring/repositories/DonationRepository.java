package tdtu.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdtu.spring.models.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer> {

	@Override
	@Query("SELECT d FROM Donation d ORDER BY d.id DESC")
	public List<Donation> findAll();
	
	@Query(value = "SELECT SUM(d.amount) FROM Donation d WHERE d.project_id = ?1", nativeQuery = true)
	public int getTotalAmountByProjectId(int id);

	@Query(value = "SELECT COUNT(d.amount) FROM Donation d WHERE d.project_id = ?1", nativeQuery = true)
	public int countByProjectId(int id);
	
	@Query(value = "SELECT d.project_id, p.name, SUM(d.amount) as amount FROM donation d, project p WHERE d.account_id = ?1 and d.project_id=p.id group by d.project_id order by d.project_id desc", nativeQuery = true)
	public List<Object[]> getProjectAmountByAccountId(int id);
}