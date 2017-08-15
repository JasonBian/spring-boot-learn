package spring.boot.multipledb.test;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Resource
    private TestService testService;
   
    @RequestMapping("/test")
    public String test(){
//     for(Demo d:testService.getList()){
//         System.out.println(d);
//     }
       for(Demo d:testService.getListByDs1()){
           System.out.println(d);
       }
       return"ok";
    }
}
