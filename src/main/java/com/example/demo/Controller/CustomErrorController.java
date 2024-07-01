package com.example.demo.Controller;

import com.example.demo.logging.LogMessages;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            logger.info("Status code: " + statusCode);
            if (statusCode == 404) {
                logger.info(LogMessages.PAGE_NOT_FOUND.getMessage());
                return "error"; // Return custom 404 error page
            } else {
                logger.info(LogMessages.GENERAL_ERROR.getMessage());
            }
        } else {
            logger.warn("Error status code is null");
        }
        return "error"; // Return default error page for other errors
    }

    public String getErrorPath() {
        return "/error"; // Specify the path where errors are handled
    }
}
