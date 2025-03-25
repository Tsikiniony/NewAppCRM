package crm.com.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import crm.com.CRM.service.ProjectService;

@Controller
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @GetMapping("/projects")
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "projects";
    }
    
    @GetMapping("/project/{externalId}")
    public String projectDetails(@PathVariable String externalId, Model model) {
        model.addAttribute("project", projectService.getProjectByExternalId(externalId));
        return "project-details";
    }
}