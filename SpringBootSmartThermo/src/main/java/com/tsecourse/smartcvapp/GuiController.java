package com.tsecourse.smartcvapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GuiController {
	
	@RequestMapping("/")
	public String templateShow(Model model) {
		model.addAttribute("appversion", "0.5");
		return "html/gui";
	}

}
