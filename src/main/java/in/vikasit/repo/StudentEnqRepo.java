package in.vikasit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vikasit.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer>{

}
