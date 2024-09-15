package org.jt.tech_trekker.repository;

import java.util.List;

import org.jt.tech_trekker.model.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.jt.tech_trekker.constant.BlogCatagory;

public interface BlogRepository extends JpaRepository<Blog, String> {
    List<Blog> findTop5ByOrderByTitle();

    List<Blog> findByCatagory(BlogCatagory catagory);

    List<Blog> findByCatagory(BlogCatagory catagory, Pageable pageable);

    // @Query(nativeQuery = true ,
    // value = "select * from blog where catagory = ?, limit = ?")// SQL
    // @Query("SELECT FROM Blog B WHERE B.catagory=? LIMIT ?") //JPQL
    // @Query("SELECT FROM Blog B WHERE B.catagory=? LIMIT ?") // When u used this
    // type of query we dont exchenge the parameter .
    // List<Blog> getBlogsByCatagoryWithLimit(BlogCatagory catagory,int limit);//To
    // avoid this problem used @Param

    // @Query("SELECT B FROM Blog B WHERE B.catagory=:ct LIMIT :lm")
    // List<Blog> getBlogsByCatagoryWithLimit(@Param("ct") BlogCatagory catagory,
    // @Param("lm") int limit);

    long countByCatagory(BlogCatagory catagory);

    List<Blog> findByCatagoryAndTitleContaining(BlogCatagory catagory, String title, Pageable pageable);

    long countByCatagoryAndTitleContaining(BlogCatagory catagory, String title);
}
