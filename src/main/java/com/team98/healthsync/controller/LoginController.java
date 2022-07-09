package com.team98.healthsync.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String getLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {

            String url = "/login?error=true";

            // Fetch the roles from Authentication object
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            List<String> roles = new ArrayList<String>();
            for (GrantedAuthority a : authorities) {
                roles.add(a.getAuthority());
            }

            // check user role and decide the redirect URL
            if (roles.contains("ROLE_DOCTOR")) {
                url = "/doctor/today";
            }
            else if(roles.contains("ROLE_PATIENT")) {
                url = "/patient/channel";
            }
            else if(roles.contains("ROLE_LABTECHNICIAN")) {
                url = "/labtechnician/labtest";
            }
            else if(roles.contains("ROLE_RECEPTIONIST")) {
                url = "/receptionist/appointment";
            }
            else if(roles.contains("ROLE_PHARMACIST")) {
                url = "/pharmacist/order";
            }
            else if(roles.contains("ROLE_ADMIN")) {
                url = "/admin/dashboard";
            }
            String redirecturl="redirect:" + url;
            return redirecturl;
        }
        else return "thymeleaf/login";
    }
}
