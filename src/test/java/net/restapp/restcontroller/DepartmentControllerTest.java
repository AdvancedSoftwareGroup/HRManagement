package net.restapp.restcontroller;

import net.restapp.mapper.DtoMapper;
import net.restapp.model.Department;
import net.restapp.servise.IService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IService<Department> service;

    @MockBean
    private DtoMapper mapper;


    @Test(expected = IllegalArgumentException.class)
    public void NullPathVariable() throws Exception {
        mockMvc.perform(get("/department/{id}/", null));
    }



}
