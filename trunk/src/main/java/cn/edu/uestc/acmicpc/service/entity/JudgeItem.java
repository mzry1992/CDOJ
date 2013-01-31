/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.service.entity;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ICompileinfoDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.Projections;

/**
 * Judge item for single problem.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class JudgeItem {
    public Status status;
    public CompileInfo compileInfo;

    private int parseLanguage(String extension) {
        if ("cc".equals(extension))
            return 0;
        else if ("c".equals(extension))
            return 1;
        else if ("java".equals(extension))
            return 2;
        else
            return 3;
    }

    public JudgeItem(Status status) {
        this.status = status;
        compileInfo = null;
    }

    public String getSourceName() {
        if ("java".equals(status.getLanguageByLanguageId().getExtension()))
            return "Main.java";
        else
            return Integer.toString(status.getStatusId()) + status.getLanguageByLanguageId().getExtension();

    }

    public void update(IUserDAO userDAO, IProblemDAO problemDAO, IStatusDAO statusDAO,
                       ICompileinfoDAO compileinfoDAO) {
        if (compileInfo != null) {
            if (compileInfo.getContent().length() > 65535)
                compileInfo.setContent(compileInfo.getContent().substring(0, 65534));
            try {
                compileinfoDAO.add(compileInfo);
                status.setCompileInfoByCompileInfoId(compileInfo);
            } catch (AppException ignored) {
            }
        }
        try {
            statusDAO.update(status);
        } catch (AppException ignored) {
        }
        if (status.getResult() == Global.OnlineJudgeReturnType.OJ_AC.ordinal()) {
            try {
                User user = status.getUserByUserId();
                StatusCondition statusCondition = new StatusCondition();
                statusCondition.userId = user.getUserId();
                Condition condition = statusCondition.getCondition();
                condition.addProjection(Projections.countDistinct("problemId"));
                Long count = statusDAO.customCount(condition);
                user.setSolved((int) count.longValue());
                userDAO.update(user);
                statusCondition = new StatusCondition();
                Problem problem = status.getProblemByProblemId();
                statusCondition.problemId = problem.getProblemId();
                condition = statusCondition.getCondition();
                condition.addProjection(Projections.countDistinct("userId"));
                count = statusDAO.customCount(condition);
                problem.setSolved((int) count.longValue());
                problemDAO.update(problem);
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
    }
}