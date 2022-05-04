package tdtu.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdtu.spring.models.Project;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
		
    @Override
    @Query("SELECT l FROM Project l")
    public List<Project> findAll();
    
//    @Modifying
//    @Query("UPDATE Project l SET l.name = ?1, l.description = ?2, l.target_fund = ?3 WHERE l.current_fund = ?4")
//    void updateById(String name, String description, int target_fund, int current_fund);   
}