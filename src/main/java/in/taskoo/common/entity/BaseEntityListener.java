package in.taskoo.common.entity;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.lang3.StringUtils;

public class BaseEntityListener {
  private static final int DB_COLUMN_LIMIT = 32;

  // @Autowired
  // private Clock clock;

  /**
   * It runs before saving data into DB.
   *
   * @param entity
   */
  @PrePersist
  public void prePersist(BaseEntity entity) {
    String userName = this.getUserName();
    String programName = this.getProgramName();

    entity.setDeleteFlag(Boolean.FALSE);
    entity.setCreateDateTime(LocalDateTime.now());
    entity.setCreateProgram(programName);
    entity.setCreateStaff(userName);
    entity.setSysUpdateDateTime(LocalDateTime.now());
    entity.setSysUpdateProgram(programName);
    entity.setSysUpdateStaff(userName);
    entity.setUpdateCount(0);
  }

  /**
   * It runs before updating data into DB.
   *
   * @param entity
   */
  @PreUpdate
  public void preUpdate(BaseEntity entity) {
    String userName = this.getUserName();
    String programName = this.getProgramName();

    entity.setSysUpdateDateTime(LocalDateTime.now());
    entity.setSysUpdateProgram(programName);
    entity.setSysUpdateStaff(userName);
    entity.setUpdateCount(entity.getUpdateCount() + 1);
  }

  private String getUserName() {
    return StringUtils.substring("default", 0, DB_COLUMN_LIMIT);
  }

  private String getProgramName() {
    return StringUtils.substring("default", 0, DB_COLUMN_LIMIT);
  }
}
