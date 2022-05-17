package tdtu.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tdtu.spring.models.Account;
import tdtu.spring.models.Project;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	Account findByUsername(String username);

	@Override
	@Query("SELECT a FROM Account a ORDER BY a.id DESC")
	public List<Account> findAll();

	@Modifying
	@Query(value = "UPDATE Account a SET a.name = ?2, a.username = ?3, a.role = ?4, a.balance = ?5 WHERE a.id = ?1", nativeQuery = true)
	void updateById(int id, String name, String username, String role, int balance);

	@Modifying
	@Transactional
	@Query("update Account a set a.balance = ?2 where a.id = ?1")
	void updateBalanceById(int id, int newBalance);
	
	
}