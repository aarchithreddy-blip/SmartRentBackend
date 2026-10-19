package com.smartrental.repository;
import com.smartrental.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PropertyRepository extends JpaRepository<Property,Long>{
 List<Property> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
 List<Property> findAllByOrderByCreatedAtDesc();
}
