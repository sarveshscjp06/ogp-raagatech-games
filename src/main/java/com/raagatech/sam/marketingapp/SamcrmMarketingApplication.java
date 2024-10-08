/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raagatech.sam.marketingapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sarve
 */
@RestController
@RequestMapping("/resources/marketing")
public class SamcrmMarketingApplication {
    @RequestMapping
    public String home() {
        return "<h1>Spring Boot Hello World!</h1><br/> This service is about SAMCRM Marketing Application "+
        "<br/>Marketing encompasses a broader set of activities to create awareness, generate leads, and build customer relationships "+
        "<br/>1. Email marketing 2. Content marketing 3. Search engine optimization 4. Social media marketing "+
"5. Blogging 6. Influencer marketing 7. Word of mouth 8. Advertising 9. Event marketing 10. Marketing communications "+
"11. Conduct market research 12. Define your budget 13. Direct marketing 14. Identify goals 15. Marketing budget "+ 
"16. Marketing objectives 17. Online 18. Public Relations 18. Online marketing 19. Conversational marketing "+
"20. Cause marketing 21. Affiliate marketing 22. Inbound marketing";
    }
}
