package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;

/**
 * DAO for contestproblem entity.
 */
@Repository
public class ContestProblemDAO extends DAO<ContestProblem, Integer> implements IContestProblemDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<ContestProblem> getReferenceClass() {
    return ContestProblem.class;
  }
}
