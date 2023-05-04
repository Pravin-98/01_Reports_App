package in.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entiry.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer>{

}
