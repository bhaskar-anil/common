package in.taskoo.common.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public abstract class BaseEntity implements Serializable {
  private static final long serialVersionUID = 4386514102960551083L;

  @Column(name = "sys_delete_flag")
  private Boolean deleteFlag = Boolean.FALSE;

  @CreatedDate
  @Column(name = "sys_create_datetime", nullable = false, updatable = false)
  private LocalDateTime createDateTime;

  @Column(name = "sys_create_staff")
  private String createStaff;

  @Column(name = "sys_create_program")
  private String createProgram;

  @LastModifiedDate
  @Column(name = "sys_update_datetime")
  private LocalDateTime sysUpdateDateTime;

  @Column(name = "sys_update_staff")
  private String sysUpdateStaff;

  @Column(name = "sys_update_program")
  private String sysUpdateProgram;

  @Version
  @Column(name = "sys_update_count")
  private Integer updateCount;
}
