package hello.service;

import hello.dao.BlogDao;
import hello.entity.Blog;
import hello.entity.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BlogServiceTest {
    BlogService blogService;

    @Mock
    BlogDao blogDao;

//    @InjectMocks
//    BlogService blogService;

    @Test
    public void getBlogsFromDb() {
//       getBlogsFromDb List<Blog> blogs = Arrays.asList(mock(Blog.class), mock(Blog.class));
//        when(blogDao.getBlogs(1, 2, null)).thenReturn(blogs);
//        when(blogDao.count(null)).thenReturn(3);

//        BlogListResult result = blogService.getBlogs(1, 2, null);
//
//        verify(blogDao).count(null);
//        verify(blogDao).getBlogs(1, 2, null);
//
//        Assertions.assertEquals(1, result.getPage());
//        Assertions.assertEquals(3, result.getTotal());
//        Assertions.assertEquals(2, result.getTotalPage());
//        Assertions.assertEquals("ok", result.getStatus());
    }

    @Test
    public void returnFailureWhenExceptionThrown() {
        when(blogDao.getBlogs(anyInt(), anyInt(), any())).thenThrow(new RuntimeException());

        Result result = blogService.getBlogs(1, 10, null);

        Assertions.assertEquals("fail", result.getStatus());
        Assertions.assertEquals("系统异常", result.getMsg());
    }
}
