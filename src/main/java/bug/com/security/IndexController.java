package bug.com.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @GetMapping("/test")
    public String test() {

        return "index";
    }
    @GetMapping("/contact")
    public String contact() {

        return "contact";
    }
}