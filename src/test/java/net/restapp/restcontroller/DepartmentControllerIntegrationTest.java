package net.restapp.restcontroller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class DepartmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

//todo: think how work this test
//    @Test
//    public void NullPathVariable() throws Exception {
//        mockMvc.perform(get("/department/{id}/", 1)
//                .with(httpBasic("svitlana.anulich@gmail.com",
//                        "22222222")))
//                //.andExpect(authenticated())
//                .andExpect(status().isInternalServerError());
//    }


}
