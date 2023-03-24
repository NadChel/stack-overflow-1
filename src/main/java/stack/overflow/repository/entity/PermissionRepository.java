package stack.overflow.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import stack.overflow.model.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
