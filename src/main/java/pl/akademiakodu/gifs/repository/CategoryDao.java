package pl.akademiakodu.gifs.repository;

import pl.akademiakodu.gifs.model.Category;
import java.util.List;

//CRUD
public interface CategoryDao {
    void createCategory(Category category);
    List<Category> findAllCategoriess();
    void updateCategory(Long id);
    void deleteCategory(Long id);
}
