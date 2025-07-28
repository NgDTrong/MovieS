package app.movies.Model;

import java.util.List;

public class ListCategory {
    List<Category> categoryList;

    public ListCategory(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public ListCategory() {
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
