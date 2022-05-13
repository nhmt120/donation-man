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
	@Query("SELECT d FROM Donation d")
	public List<Donation> findAll();
	
    @Query(value = "SELECT SUM(d.amount) FROM Donation d WHERE d.project_id = ?1", nativeQuery = true)
    public int getTotalAmountByProjectId(int id);
    
    @Query(value = "SELECT COUNT(d.amount) FROM Donation d WHERE d.project_id = ?1", nativeQuery = true)
    public int countByProjectId(int id);
}