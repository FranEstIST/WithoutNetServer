package tecnico.withoutnet.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tecnico.withoutnet.server.service.MessageService;

@Controller
public class WebAppController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("messages", messageService.getAllMessages());
        return "WithoutNetPortal";
    }

    @GetMapping("/home/delete-all")
    public String deleteAll(Model model) {
        messageService.deleteAllMessages();
        model.addAttribute("messages", messageService.getAllMessages());
        return "WithoutNetPortal";
    }
}
