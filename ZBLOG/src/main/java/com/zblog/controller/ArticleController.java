package com.zblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zblog.dmo.Article;
import com.zblog.dto.Page;
import com.zblog.service.ArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {
    
    @Autowired
    private ArticleService articleService;
    
    /**
     * 
     * 获取所以的文章列表，按照时间降序排列
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/index")
    public ModelAndView getAllArticles(Page page) {
        
        ModelAndView response = new ModelAndView("/index");
        List<Article> articles = null;
        
        if( page == null ) {
            page = new Page();
            page.setCurrentPage(1);
            page.setTotalRows(10);
        }
        
        try {        
            articles = articleService.getArticles(null, page);
            if (articles != null) {                
                //设置page并重新分页
                page.setTotalRows(articles.size());
                page.repaginate();      
                response.addObject("articles", articles);              
            }
        } catch (Exception e) {
           LOGGER.error("ArticleController." +
           		"getAllArticles();", e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 
     * 获取所以的文章列表，按照时间降序排列
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/search")
    public ModelAndView getArticlesByCate(Article article, Page page) {
        
        ModelAndView response = new ModelAndView("/index");
        List<Article> articles = null;
        
        if (page == null) {   
            page = new Page();
            page.setCurrentPage(0);
            page.setPageSize(10);
        }
        
        try {  
            articles = articleService.getArticles(article, page);
            if (articles != null) {                
                //设置page并重新分页
                page.setTotalRows(articles.size());
                page.repaginate();      
            }
        } catch (Exception e) {
            LOGGER.error("ArticleController.getAllArticles();", e.getMessage());
        } 
        
        response.addObject("articles", articles);                      
        return response;
    }
    
    /**
     * 
     * 添加文章
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/add")
    @ResponseBody
    public String addArticle(Article article) {
        
        try {  
            //参数正常
            if (!article.getTitle().isEmpty() && !article.getContent().isEmpty()
                && article.getCategory() != null) {
                //如果添加ok,返回成功
                if (articleService.addArticle(article)) { return "SUCCESS"; }   
            //参数错误
            } else { return "ERROR"; }

        } catch (Exception e) {
            LOGGER.error("ArticleController.addArticle();", e.getMessage());
        }
        
        return "FAIL";
    }
    
    /**
     * 
     * 更新文章
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/update")
    @ResponseBody
    public String updateArticles(Article article) {
        
        try {  
            //参数正常
            if (article.getId() != null) {
                //如果更新成功,返回SUCCESS
                if (articleService.updateArticle(article)) { return "SUCCESS"; }   
            //参数错误,返回ERROR
            } else { return "ERROR"; }
            
        } catch (Exception e) {
                LOGGER.error("ArticleController.addArticle();", e.getMessage());
        }
        //返回FAIL
        return "FAIL";
    }
    
}