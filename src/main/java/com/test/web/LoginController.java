/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.web;

import org.springframework.stereotype.Controller;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Controller
public class LoginController
{

//    @RequestMapping(value = "/main", method = RequestMethod.GET)
//    public String printWelcome(ModelMap model, Principal principal)
//    {
//
//        String name = principal.getName();
//        model.addAttribute("username", name);
//        return "main_page";
//
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login(ModelMap model)
//    {
//        return "login_page";
//    }
//
//    @RequestMapping(value = "/loginError", method = RequestMethod.GET)
//    public String loginerror(ModelMap model)
//    {
//        model.addAttribute("error", "true");
//        return "login_page";
//    }
//
//    @Secured(
//            {
//                "ROLE_REGULAR_USER", "ROLE_ADMIN"
//            })
//    @RequestMapping(value = "/common", method = RequestMethod.GET)
//    public String common(ModelMap model)
//    {
//
//        return "common_page";
//
//    }
//
//    @Secured("ROLE_ADMIN")
//    @RequestMapping(value = "/admin", method = RequestMethod.GET)
//    public String admin(ModelMap model)
//    {
//
//        return "admin_page";
//
//    }
//
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logout(ModelMap model)
//    {
//
//        return login(model);
//
//    }
}
