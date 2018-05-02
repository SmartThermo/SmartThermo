package com.tsecourse.smartcvapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GuiTemplateController {
	
	@RequestMapping("/")
	public String templateShow(Model model) {
		model.addAttribute("appversion", "0.4");
		model.addAttribute("server_url", "http://localhost:8080/"); // to do: use in template / js
		return "html/gui";
	}

}
