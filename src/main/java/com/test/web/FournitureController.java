/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.web;

import com.test.persistence.model.Categorie;
import com.test.persistence.model.Fourniture;
import com.test.persistence.service.ICategorieService;
import com.test.persistence.service.IFournitureService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@Controller
@Secured(
        {
            "ROLE_USER", "ROLE_ADMIN"
        })
@RequestMapping("/fourniture")
public class FournitureController
{

    @Autowired
    private IFournitureService iFournitureService;

    @Autowired
    private ICategorieService iCategorieService;

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String showAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Fourniture fourniture = iFournitureService.findOne(id);
        model.addAttribute("fourniture", fourniture);
        return "/fourniture/show";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        final Fourniture fourniture = new Fourniture();
        fourniture.setQuantite(0);
        model.addAttribute("fourniture", fourniture);
        return "/fourniture/new";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Fourniture fourniture = iFournitureService.findOne(id);
        model.addAttribute("fourniture", fourniture);
        return "/fourniture/edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteAction(@PathVariable("id") final Long id, final RedirectAttributes redirectAttributes)
    {
        final Fourniture fournitureToDisable = iFournitureService.findOne(id);
        iFournitureService.delete(fournitureToDisable);
        return "redirect:/fourniture/";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {

        final Long categorieID = (webRequest.getParameter("querycategorie") != null && !webRequest.getParameter("querycategorie").equals(""))
                ? Long.valueOf(webRequest.getParameter("querycategorie"))
                : -1;
        final String designation = webRequest.getParameter("querydesignation") != null ? webRequest.getParameter("querydesignation") : "";
        final String reference = webRequest.getParameter("queryreference") != null ? webRequest.getParameter("queryreference") : "";
        final Integer nombrePage = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 5;

        final Page<Fourniture> resultPage = iFournitureService.findPaginated(categorieID, designation, reference, nombrePage, size);

        final Fourniture fourniture = new Fourniture();
        fourniture.setReference(reference);
        fourniture.setDesignation(designation);
        Categorie ct = new Categorie();
        ct.setId(categorieID);
        fourniture.setCategorie(ct);
        model.addAttribute("fourniture", fourniture);
        model.addAttribute("page", nombrePage);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("fournitures", resultPage.getContent());
        return "fourniture/index";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final Fourniture fourniture, final ModelMap model,
            final BindingResult result, final RedirectAttributes redirectAttributes)
    {
        System.out.println("dans le controller");
        if (result.hasErrors())
        {
            System.out.println("dans le controller avec erreur");
            model.addAttribute("error", "error");
            model.addAttribute("fourniture", fourniture);
            return "fourniture/new";
        }
        else
        {
            System.out.println("dans le controller sans erreur");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            iFournitureService.create(fourniture);
            return "redirect:/fourniture/" + fourniture.getId() + "/show";
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(@Valid final Fourniture fourniture, final ModelMap model,
            @PathVariable("id") final Long id,
            final BindingResult result, final RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors())
        {
            model.addAttribute("error", "error");
            return "fourniture/edit";
        }
        else
        {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            iFournitureService.update(fourniture);
            return "redirect:/fourniture/" + fourniture.getId() + "/show";
        }
    }

    @ModelAttribute("categories")
    public Map<Long, String> getCategorie()
    {
        Map<Long, String> results = new HashMap<>();
        final List<Categorie> categories = iCategorieService.findAll();
        for (Categorie category : categories)
        {
            results.put(category.getId(), category.getIntitule());
        }
        return results;
    }

}
