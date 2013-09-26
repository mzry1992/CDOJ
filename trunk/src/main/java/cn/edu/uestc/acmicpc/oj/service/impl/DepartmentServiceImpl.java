package cn.edu.uestc.acmicpc.oj.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.DepartmentDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.oj.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.impl.AbstractService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 */
@Service
@Primary
public class DepartmentServiceImpl extends AbstractService implements DepartmentService {

  private final IDepartmentDAO departmentDAO;
  private List<DepartmentDTO> departmentDTOList;

  @Autowired
  public DepartmentServiceImpl(IDepartmentDAO departmentDAO) {
    this.departmentDAO = departmentDAO;
  }

  private DepartmentDTO getDepartmentDTO(Department department) {
    return DepartmentDTO.builder()
        .setDepartmentId(department.getDepartmentId())
        .setName(department.getName())
        .build();
  }

  @PostConstruct
  public void init() throws AppException {
    List<Department> departmentList = (List<Department>) departmentDAO.findAll();
    departmentDTOList = new LinkedList<>();
    for (Department department: departmentList)
      departmentDTOList.add(getDepartmentDTO(department));
  }

  @Override
  public String getDepartmentName(Integer departmentId) {
    for (DepartmentDTO department: departmentDTOList)
      if (department.getDepartmentId().equals(departmentId))
        return department.getName();
    return null;
  }

  @Override
  public List<DepartmentDTO> getDepartmentList() {
    return departmentDTOList;
  }

  @Override
  public IDepartmentDAO getDAO() {
    return departmentDAO;
  }
}
