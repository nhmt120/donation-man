package tdtu.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tdtu.spring.models.Project;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
		
    @Override
    @Query("SELECT p FROM Project p")
    public List<Project> findAll();
    
    @Modifying
    @Query(value = "UPDATE Project p SET p.name = ?2, p.description = ?3, p.target_fund = ?4, p.current_fund = ?5 WHERE p.id = ?1", nativeQuery = true)
    void updateById(int id, String name, String description, double target_fund, double current_fund);   
}