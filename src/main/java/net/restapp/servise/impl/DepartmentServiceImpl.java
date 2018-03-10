package net.restapp.servise.impl;

import net.restapp.model.Department;
import net.restapp.repository.RepoDepartment;
import net.restapp.servise.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    RepoDepartment repoDepartment;

    @Override
    public void save(Department department) {
        repoDepartment.save(department);
    }

    @Override
    public void delete(Long id) {
        repoDepartment.delete(id);
    }

    @Override
    public List<Department> getAll() {
        return repoDepartment.findAll();
    }

    @Override
    public Department getById(Long id) {
        return repoDepartment.findOne(id);
    }



}
