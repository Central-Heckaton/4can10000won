package team_project.beer_community.api;

import org.apache.coyote.Response;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@Controller
public class ExceptionApiController implements ErrorController {

    @GetMapping({"/", "error"})
    public String index(){
        return "index.html";
    }

    @RequestMapping(value = "/error")
    public String handleNoHandleFoundException(HttpServletResponse response, HttpServletRequest request) {
        int status = response.getStatus();
        HttpHeaders headers = new HttpHeaders();
        System.out.println("status = " + status); // 오류상태
        System.out.println("request.getRequestURI() = " + request.getRequestURI()); // 요청주소
        if (Objects.equals(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            Map<String, Object> body = Map.of("error", "Not Found","timestamp", System.currentTimeMillis());
            System.out.println("handleNoHandleFoundException/equals/body = " + body);
            return "/error";
        }
        return "/error";
    }
}
