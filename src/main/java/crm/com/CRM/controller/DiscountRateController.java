package crm.com.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

import crm.com.CRM.dto.DiscountRateDTO;
import crm.com.CRM.service.DiscountRateService;

@Controller
@RequestMapping("/discount-rates")
public class DiscountRateController {
    private final DiscountRateService discountRateService;

    @Autowired
    public DiscountRateController(DiscountRateService discountRateService) {
        this.discountRateService = discountRateService;
    }

    @GetMapping
    public String showDiscountRates(Model model) {
        model.addAttribute("discountRates", discountRateService.getAllDiscountRates());
        return "discount-rates/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("discountRate", new DiscountRateDTO());
        return "discount-rates/form";
    }

    @PostMapping
    public String createDiscountRate(@Valid @ModelAttribute DiscountRateDTO discountRate) {
        discountRateService.createDiscountRate(discountRate);
        return "redirect:/discount-rates";
    }

    @PostMapping("/toggle/{id}")
    public String toggleDiscountRate(@PathVariable Long id) {
        discountRateService.toogle(id, new DiscountRateDTO());
        return "redirect:/discount-rates";
    }
}