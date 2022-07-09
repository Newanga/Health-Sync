package com.team98.healthsync.controller.admin;

import com.team98.healthsync.models.LabTestCategory;
import com.team98.healthsync.repository.LabTestCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LabTestCategoryController {

    @Autowired
    private LabTestCategoryRepository labTestCategoryRepository;

    @GetMapping("/admin/labtestcategories")
    @ResponseBody
    public Iterable<LabTestCategory> getAllCategories(){
        Iterable<LabTestCategory> categories=labTestCategoryRepository.findAll();
        return categories;
    }

    @GetMapping("/admin/labtestcategories/new")
    public ModelAndView newPatient(){
        ModelAndView mv=new ModelAndView();
        mv.addObject("category",new LabTestCategory());
        mv.setViewName("thymeleaf/admin/labtestcategory/new_category");
        return mv;
    }

    @PostMapping("/admin/labtestcategories")
    public ModelAndView createNewChannel(@Valid @ModelAttribute("category") LabTestCategory category, BindingResult result  ){
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("category",category);
            mv.setViewName("thymeleaf/admin/labtestcategory/new_category");
            return mv;
        }
        try {
            labTestCategoryRepository.save(category);
        } catch (Exception e) {
            return new ModelAndView("thymeleaf/admin/labtestcategory/categories", "error", e.getMessage());
        }
        return new ModelAndView("redirect:/admin/labtestcategory","success","true");

    }
}
