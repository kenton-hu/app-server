package cn.wildfirechat.app.wfchat.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_user_status")
public class TUserStatus {
    @Id
    @Column(length = 11)
    private int id;

    @Column(name = "_uid")
    private String uid;

    @Column(name = "_status")
    private String status;

    @Column(name = "_dt")
    private long dt;
}
