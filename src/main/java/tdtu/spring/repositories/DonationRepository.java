package tdtu.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdtu.spring.models.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer> {

	@Override
	@Query("SELECT d FROM Donation d")
	public List<Donation> findAll();
}