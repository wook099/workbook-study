package sw_workbook.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw_workbook.spring.domain.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory,Long> {
}
