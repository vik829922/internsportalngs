package in.vikasit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vikasit.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {

}
