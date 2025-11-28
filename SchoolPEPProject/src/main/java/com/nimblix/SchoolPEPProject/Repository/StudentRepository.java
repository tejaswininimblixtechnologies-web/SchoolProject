package com.nimblix.SchoolPEPProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimblix.SchoolPEPProject.Model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

<<<<<<< Updated upstream
	@Query(value = "select * from student where email=?1",nativeQuery =true)
=======
	@Query(value = "select * from student where email =?1",nativeQuery = true)
>>>>>>> Stashed changes
	Student findByEmail(String email);
}
